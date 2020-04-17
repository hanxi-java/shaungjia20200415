package shuangjia.shuangjia.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ShuangJiaTCP {

    private ShuangJiaTCP(){

    }

    public static Map<String,String> sendTCPRequest(String ip, String port){
        Map<String, String> respMap = new HashMap<String, String>();
        OutputStream out = null;      //写
        InputStream in = null;        //读
        //String localPort = null;      //本地绑定的端口(java socket, client, /127.0.0.1:50804 => /127.0.0.1:9901)
        String respData = null;       //响应报文
        String respDataHex = null;    //远程主机响应的原始字节的十六进制表示
        Socket socket = new Socket(); //客户机

        try {
            socket.setTcpNoDelay(true);
            socket.setReuseAddress(true);
            socket.setSoTimeout(30000);
            socket.setSoLinger(true, 5);
            socket.setSendBufferSize(1024);
            socket.setReceiveBufferSize(1024);
            socket.setKeepAlive(true);
            socket.connect(new InetSocketAddress(ip, Integer.parseInt(port)), 30000);
            //localPort = String.valueOf(socket.getLocalPort());
            //System.out.println(localPort);
            /**			 * 发送TCP请求			 */
            out = socket.getOutputStream();
            //String a =
            byte[] a=new byte[]{0X01,0X00,0X00,0X00,0X00,0X06,0X01,0X03,0X00,0X00,0X00,0X02};
            System.out.println("发送报文："+a.toString());
            out.write(a);
            /**			 * 接收TCP响应			 */
            in = socket.getInputStream();
            byte[] c=new byte[256];
            if(in==null) {
                System.out.println("为空");
            }else {
                in.read(c);
            }
            /*
             * ByteArrayOutputStream bytesOut = new ByteArrayOutputStream(); byte[] buffer =
             * new byte[1024]; int len = -1; while((len=in.read(buffer)) != -1){
             * bytesOut.write(buffer, 0, len); }
             */
            /**			 * 解码TCP响应的完整报文			 */
            // respData = bytesOut.toString();
            System.out.println("响应报文："+c);
            //respDataHex = formatToHexStringWithASCII(bytesOut.toByteArray());
        } catch (Exception e) {
            System.out.println("与[" + ip + ":" + port + "]通信遇到异常,堆栈信息如下");
            e.printStackTrace();
        } finally {
            if (null!=socket && socket.isConnected() && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("关闭客户机Socket时发生异常,堆栈信息如下");
                    e.printStackTrace();
                }
            }
        }
        // respMap.put("localPort", localPort);
        //respMap.put("reqData", reqData);
        respMap.put("respData", respData);
        //respMap.put("respDataHex", respDataHex);
        return respMap;

    }



    public static void main(String[] args) {

        /*
         * testTCPserver ts=new testTCPserver(); ts.server();
         */
        //String reqData="01000000000601030002";
        String ip="10.192.12.254";
        String port="502";
        Map<String, String> respMap = sendTCPRequest(ip, port);

        //testTCPserver ts=new testTCPserver(); ts.server();


    }
}
