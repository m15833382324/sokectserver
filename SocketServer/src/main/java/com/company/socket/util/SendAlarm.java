package com.company.socket.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

public class SendAlarm {

	static Logger log = Logger.getLogger(SendAlarm.class);

	/**
	 * 发送数据
	 * 
	 * @param data
	 *            发送的数据
	 * @return
	 */
	public static boolean send(OutputStream os, ReentrantLock reentrantLock) {

		try {
			reentrantLock.lock();
			// 构造要发送的内容;
			byte[] data = fomratSendMsg();
			os.write(data);
			os.flush();
			log.info("消息发送成功!");
			reentrantLock.unlock();
		} catch (IOException e) {
			log.info("发送数据时,发生io异常!");
			e.printStackTrace();
			return false;
		} finally {
			if (reentrantLock.isLocked()) {
				reentrantLock.unlock();
			}
		}

		return true;
	}

	static byte[] fomratSendMsg() throws UnsupportedEncodingException {
		// 创建byte数组
		// [报警ID#报警时间#防区ID#现场设备类型#报警类型]
		String msgBody = "[" + ProUtil.readPro("zone_id") + "."
				+ DataUtil.formatTimetoMS();
		msgBody += "#" + DataUtil.formatTimetoSecond() + "#"
				+ ProUtil.readPro("zone_id") + "#2#22]";
		byte[] msgBodyArray = msgBody.getBytes("UTF-8");
		int msgBodyLength = msgBodyArray.length;
		int msgLength = 32 + msgBodyLength;
		byte[] msgArray = new byte[msgLength];
		// 包头
		for (int i = 0; i < 4; i++) {
			msgArray[i] = (byte) 0xFF;
		}
		// 业务数据类型
		msgArray[4] = 0x01;
		// 数据版本
		msgArray[5] = 0x01;
		// 设备ID
		String deviceId = ProUtil.readParam("dev_id");
		byte[] deviceIdArray = deviceId.getBytes("ASCII");
		for (int i = 0; i < deviceIdArray.length; i++) {
			msgArray[(msgLength - 26 - msgBodyLength) + i] = deviceIdArray[i];
		}
		// 业务数据长度
		byte[] msgBodyLengthArray = new byte[4];
		msgBodyLengthArray = DataUtil.intToBytesHL(msgBodyLength);
		for (int i = 0; i < msgBodyLengthArray.length; i++) {
			msgArray[(msgLength - 9 - msgBodyLength) + i] = msgBodyLengthArray[i];
		}
		// 业务数据内容

		for (int i = 0; i < msgBodyArray.length; i++) {
			msgArray[(msgLength - 5 - msgBodyLength) + i] = msgBodyArray[i];
		}
		// 异或校验码
		msgArray[(msgLength - 5)] = 0x00;
		// 包尾
		for (int i = (msgLength - 4); i < msgArray.length; i++) {
			msgArray[i] = (byte) 0xDD;
		}
		return msgArray;
	}
}
