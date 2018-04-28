package com.company.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.company.socket.thread.RecieveThread;
import com.company.socket.util.ProUtil;



public class MainSocket {
	public static void main(String[] args) {
		
		Logger log = Logger.getLogger(MainSocket.class);
		log.info("程序启动 ");
		
		ReentrantLock reentrantLock = new ReentrantLock();
		
		ServerSocket server = null;
		Socket sendSocket= null;
		
		try {
			server = new ServerSocket(51000);
			sendSocket=new Socket(ProUtil.readPro("server_ip"),55555);
			//暂时不考虑心跳包的问题;
			sendSocket.setSoTimeout(30000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Socket client = null;
		boolean f = true;
		
		while(f){
			try {
				
				client = server.accept();
				log.info("ip为"+client.getInetAddress()+"的设备发起连接");
				ThreadPoolExecutor threadPoolExecutor = 
						new ThreadPoolExecutor(1, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),new ThreadPoolExecutor.DiscardOldestPolicy());
				threadPoolExecutor.execute(new RecieveThread(client,sendSocket, reentrantLock));
			} catch (IOException e) { 
				log.info("server.accept问题");
				e.printStackTrace();
				continue;
			}
		}
		try {
			server.close();
		} catch (IOException e) {
			log.info("server.close()方法执行");
			e.printStackTrace();
		}
	
	}
}
