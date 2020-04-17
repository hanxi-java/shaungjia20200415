package shuangjia.shuangjia.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import shuangjia.shuangjia.entities.AlarmDefine;
import shuangjia.shuangjia.entities.Data;
import shuangjia.shuangjia.entities.DeviceProperty;
import shuangjia.shuangjia.service.AlarmServer;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *@Author 张典
 *@Description  原水水质监测设备信息接收线程3
 *
 */
@Slf4j
public class JMQualityReceiveThread_3 implements Runnable{

	private InputStream in;
	private OutputStream out;
	private boolean flag=true;
	@Autowired
	private DevicePropertyService devicePropertyService;
	@Autowired
	private DataService dataService;
	private  boolean run=false;
	private AlarmServer alarmServer;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date time= new java.sql.Date(new Date().getTime());
	//无参构造
	public JMQualityReceiveThread_3(){

	}


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
			this.alarmServer=ApplicationUtil.getBean(AlarmServer.class);
			//发送数据
			out = socket.getOutputStream();
			//接收数据
			in=socket.getInputStream();
			getMassage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("ReceiveQuality2通信遇到异常,堆栈信息如下",e);
			//flag=false;
			CloseUtil.closeAll(in);
		}
	}

    int num=0;
	//解析数据
	private void getMassage() {
		// TODO Auto-generated method stub
		 num=num+1;
		 byte[] c=null;
		 Map map = new HashMap<String,Object>();
		byte[] a=new byte[]{0X01,0X00,0X00,0X00,0X00,0X06,0X01,0X04,0X1B,0X58,0X00,0X08};
		try{
		  out.write(a);
		 if(in==null) {
			 log.error("TCP响应数据为空");
		 	flag=false;
		 }else {
			c= new byte[25];
			in.read(c);
		}
		 List<DeviceProperty> list=new ArrayList<DeviceProperty>();
			/*获取报警方案*/
			List<AlarmDefine> alarmDefines=alarmServer.getAllAlarmDefine();
		 for(int i=0;i<2;i++) {
			int j=i*4+9;
			 float q= BitConverter.toFloat(new byte[] {c[j+1],c[j],c[j+3],c[j+2]}, 0);
			 DecimalFormat decimalFormat = new DecimalFormat("0.0000");
			 String p = decimalFormat.format(q);
			 /*对比有报警方案的ID才进入报警自动处理*/
			 for(AlarmDefine alarmDefine:alarmDefines){
				 if(alarmDefine.getDevicePropertyId()==i+1){
					 alarmServer.autoAlarm(alarmDefine,df.format(new Date()),p);
				 }else {
				 }
			 }
			 DeviceProperty deviceProperty=new DeviceProperty();
			 Data data =new Data();
			 deviceProperty.setId(i+107);
			 deviceProperty.setMonitorValue(p);
			 deviceProperty.setMonitorDate(df.format(new Date()));
			 list.add(deviceProperty);
		 }
			 log.info("浓度浊度数据入库");
			 int result=devicePropertyService.update(list);
			 if(num==5){
				int result1=devicePropertyService.insertData(list);
				num=0;
			 }
			 log.info("浓度浊度数据入库完成");
			 list.clear();
		 } catch (Exception e) {
			 flag=false;
			 CloseUtil.closeAll(in);
			 }
	}
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			try {
				startTCP();
				Thread.currentThread().sleep(10000);
				log.info("-------------------------------循环请求--------------------------------");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				log.error("TCP接收线程出错",e);
				CloseUtil.closeAll(in);
			}
		}
	}

}
