package shuangjia.shuangjia.component;

import shuangjia.shuangjia.util.CloseUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class SendClient implements Runnable{

	private OutputStream out; 
	private boolean flag=true;
	
	 	
	
	public  SendClient() {
		// TODO Auto-generated constructor stub
	}
	public SendClient(Socket socket) {
		try {
			out=socket.getOutputStream();			
			//out.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
			CloseUtil.closeAll(out);
		}
	}

	private  void send() {
		 try {
		   byte[] a=new byte[]{0X01,0X00,0X00,0X00,0X00,0X06,0X01,0X03,0X00,0X00,0X00,(byte) 0XD4};
			out.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
			CloseUtil.closeAll(out);
		}
	}
	public void run() {
		// TODO Auto-generated method stub
		while(flag) {
			this.send();
			try {
				Thread.currentThread().sleep(10000);
				System.out.println("----------------------------------------------循环请求---------------------------------------------");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
				CloseUtil.closeAll(out);
			}
		}
	}
	public static void main(String[] args) {
	
		
	}
}
