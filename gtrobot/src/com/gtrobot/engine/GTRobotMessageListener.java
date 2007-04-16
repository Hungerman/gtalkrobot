package com.gtrobot.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

public class GTRobotMessageListener implements PacketListener {
	protected static final transient Log log = LogFactory
			.getLog(GTRobotMessageListener.class);

	public void processPacket(Packet packet) {
		try {
			Message message = (Message) packet;
			// Parse the message to command object
			GTRobotDispatcher dispatcher = GTRobotContextHelper
					.getGTRobotDispatcher();
			dispatcher.parseAndDispatch(message);
		} catch (Exception e) {
			log.error("Exception when processing message.", e);
		}
	}
}
