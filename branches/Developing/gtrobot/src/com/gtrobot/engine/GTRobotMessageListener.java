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

    private final GTRobotDispatcher dispatcher = GTRobotContextHelper
            .getGTRobotDispatcher();

    public void processPacket(final Packet packet) {
        try {
            final Message message = (Message) packet;

            // 调用GTRobotDispatcher进行处理
            this.dispatcher.parseAndDispatch(message);
        } catch (final Exception e) {
            GTRobotMessageListener.log.error(
                    "Exception when processing message.", e);
        }
    }
}
