package com.company.socket.thread;

import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

public class HeartBeatPacket implements Runnable{
	
	  Logger log =Logger.getLogger(HeartBeatPacket.class);
	  
	  private Socket socket;
	  private ReentrantLock reentrantLock;
	  
	  

	public HeartBeatPacket(Socket socket, ReentrantLock lock) {
		super();
		this.socket = socket;
		this.reentrantLock = lock;
	}


	@Override
	public void run() {
          try {
        	  	OutputStream os =socket.getOutputStream();
                
        	  	while (true) {
                          Thread.sleep(3000);
                          //如果当前未解锁，则说明刚放发完数据，那么就再睡一觉，并且直接跳过本次循环
                          if(reentrantLock.isLocked()){
                                  log.info("-------------------------------isLocked------------------------------");
                                  Thread.sleep(3000);
                                  continue;
                          }
                          reentrantLock.lock();
                          socket.sendUrgentData(1);
                          reentrantLock.unlock();
                  }
          } catch (Exception e) {
                  if(reentrantLock.isLocked()){
                	  reentrantLock.unlock();
                  }
                  e.printStackTrace();
          }

  }

}
