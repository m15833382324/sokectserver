package com.company.socket.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.company.socket.util.DataUtil;
import com.company.socket.util.ProUtil;
import com.company.socket.util.SendAlarm;

public class RecieveThread implements Runnable{
	Logger log = Logger.getLogger(RecieveThread.class);

	private Socket client;
	private Socket sendSocket;
	private ReentrantLock reentrantLock;

	public RecieveThread(Socket client, Socket sendSocket,ReentrantLock reentrantLock) {
		super();
		this.client = client;
		this.sendSocket = sendSocket;
		this.reentrantLock=reentrantLock;
	}

	@Override
	public void run() {
		InputStream in = null;
		OutputStream out = null;
		byte[] byte1= {0x01,(byte) 0x80,0x01,(byte) 0x80};
		boolean flag = true;
        while(flag){
        	try {
				out = client.getOutputStream();
				out.write(byte1);
				out.flush();
			} catch (IOException e2) {
				log.info("数据发送失败!");
			}
			
        	byte[] bytes  = new byte[10];
        	try {
        		in = client.getInputStream();
				int i = in.read(bytes);
			} catch (IOException e) {
				flag = false;
				e.printStackTrace();
				continue;
			}
        	byte[] speedBytes = new byte[4];
        	System.arraycopy(bytes, 4, speedBytes, 0, 4);
        	int  i = DataUtil.bytesToInt(speedBytes, 0);
        	//然后int转float
        	double dou = i/10;
        	int windLev= DataUtil.fomratWindLevel(dou);
        	
        	log.info("现在的风速为:"+dou+"m/s,风力等级为:"+windLev+"级");
        	
        	if(windLev>=Integer.valueOf(ProUtil.readPro("alarmlevel"))){
        			//如果风力大于alarmlevel/则报警;
        			OutputStream os;
					try {
						os = sendSocket.getOutputStream();
						while(reentrantLock.isLocked()){
							Thread.sleep(10);
						}
						SendAlarm.send(os,reentrantLock);
					} catch (Exception e) {
						e.printStackTrace();
					}
        	}
        	try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	if(!client.isConnected()){
        		flag = false;
        	}
        }
        try {
        	client.close();
        	log.info("server.close()方法执行,ip为:"+client.getLocalAddress()+"的连接断开!");
		} catch (IOException e) {
			log.info("server.close()异常!");
			e.printStackTrace();
		}
		
	}
}
