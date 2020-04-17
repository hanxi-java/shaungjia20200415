package shuangjia.shuangjia.component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


/**
 * @author 张典
 * @Description 线程初始化加载启动类
 *
 */

public class InitThread implements ApplicationListener<ContextRefreshedEvent>{


	private static final Executor exec = Executors.newCachedThreadPool();

	public static void InitThread(){
			
	}
	public static void main(String[] args) {
		InitThread();
	}
	@SneakyThrows
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		 /*Socket socket = new Socket(); //客户端
		 try {
			socket.setTcpNoDelay(true);
			 socket.setReuseAddress(true);
			 socket.setSoTimeout(30000);
			 socket.setSoLinger(true, 5);
			 socket.setSendBufferSize(1024);
			 socket.setReceiveBufferSize(1024);
			 socket.setKeepAlive(true);
			 socket.connect(new InetSocketAddress("10.192.12.254", Integer.parseInt("502")), 30000);

			 //exec.execute(new SendClient(socket));
			 //exec.execute(new Receive(socket));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		//无参启动
		exec.execute(new JMReceiveThread());
		/*exec.execute(new ReceiveQuality());
		exec.execute(new ReceiveQuality1());
		exec.execute(new ReceiveQuality2());*/
		
	}
}
