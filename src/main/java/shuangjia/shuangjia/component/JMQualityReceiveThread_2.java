package shuangjia.shuangjia.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import shuangjia.shuangjia.entities.Data;
import shuangjia.shuangjia.entities.DeviceProperty;
import shuangjia.shuangjia.service.DataService;
import shuangjia.shuangjia.service.DevicePropertyService;
import shuangjia.shuangjia.util.ApplicationUtil;
import shuangjia.shuangjia.util.BitConverter;
import shuangjia.shuangjia.util.CloseUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

/**
*@Author 张典
*@Description  原水水质监测设备信息接收线程2
 *
*/
@Slf4j
public class JMQualityReceiveThread_2 implements Runnable{
    private InputStream in;
    private OutputStream out;
    private boolean flag=true;
    private  boolean run=false;
    @Autowired
    private DevicePropertyService devicePropertyService;
    @Autowired
    private DataService dataService;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date time= new java.sql.Date(new Date().getTime());
    public JMQualityReceiveThread_2(){
    }

    /*开启TCP线程*/
    //开启TCP线程
    private void  startTCP(){
        Socket socket = new Socket(); //客户端
        try {
            socket.setTcpNoDelay(true);
            socket.setReuseAddress(true);
            socket.setSoTimeout(30000);
            socket.setSoLinger(true, 5);
            socket.setSendBufferSize(1024);
            socket.setReceiveBufferSize(1024);
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress("192.168.0.232", Integer.parseInt("502")), 30000);
            this.devicePropertyService= ApplicationUtil.getBean(DevicePropertyService.class);
            this.dataService=ApplicationUtil.getBean(DataService.class);
            //发送数据
            out = socket.getOutputStream();
            //接收数据
            in=socket.getInputStream();
            getMassage();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("ReceiveQuality1通信遇到异常,堆栈信息如下",e);
            //flag=false;
            CloseUtil.closeAll(in);
        }
    }
    /*解析数据*/
    private void getMassage() throws IOException {
        /*16进制解析为2进制*/
        byte[] c=null;
        Map map = new HashMap<String,Object>();
        byte[] a=new byte[]{0X01,0X00,0X00,0X00,0X00,0X06,0X01,0X01,0X00,0X00,0X00,0X08};
        try {
        out.write(a);
            if (in == null) {
                log.error("TCP响应数据为空");
                flag = false;
            } else {
                c = new byte[10];
                in.read(c);
            }
            List<DeviceProperty> list=new ArrayList<DeviceProperty>();
            StringBuffer cover= BitConverter.conver2HexStr(new byte[] {c[9]});
            DeviceProperty deviceProperty=new DeviceProperty();
            Data data =new Data();
            deviceProperty.setId(115);
            deviceProperty.setMonitorValue(cover.substring(3,4));
            deviceProperty.setMonitorDate(df.format(new Date()));
            list.add(deviceProperty);
            log.info("排污阀状态入库");
            int result=devicePropertyService.update(list);
            log.info("排污阀状态入库完成");
            list.clear();
        }catch (Exception e) {
                flag=false;
                CloseUtil.closeAll(in);
        }
    }

    public void run() {
        // TODO Auto-generated method stub
        while (flag) {
            try {
                startTCP();
                Thread.currentThread().sleep(5000);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error("TCP接收线程出错",e);
                CloseUtil.closeAll(in);
            }
        }
    }
}
