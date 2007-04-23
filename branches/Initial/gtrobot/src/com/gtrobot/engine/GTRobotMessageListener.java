package com.gtrobot.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * 侦听所有的Message，然后调用Dispatcher进行处理。
 * 
 * @author Joey
 * 
 */
public class GTRobotMessageListener implements PacketListener {
	protected static final transient Log log = LogFactory
			.getLog(GTRobotMessageListener.class);

	private GTRobotDispatcher dispatcher = GTRobotContextHelper
			.getGTRobotDispatcher();

	public void processPacket(Packet packet) {
		try {
			Message message = (Message) packet;

			// 调用GTRobotDispatcher进行处理
			dispatcher.parseAndDispatch(message);
		} catch (Exception e) {
			log.error("Exception when processing message.", e);
		}
	}
}
