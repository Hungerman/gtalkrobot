package com.gtrobot.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.ConnectionListener;

/**
 * 监听Connection的状态，进行日志。由于smack可以自动实现Reconnection，所以这部分只有log的内容。
 * 
 * @author Joey
 * 
 */
public class GTRobotConnectionListener implements ConnectionListener {
    protected static final transient Log log = LogFactory
            .getLog(GTRobotConnectionListener.class);

    public void connectionClosed() {
        GTRobotConnectionListener.log.warn("Connection closed!");
    }

    public void connectionClosedOnError(final Exception e) {
        GTRobotConnectionListener.log
                .error("Connection closed with error: ", e);
    }

    public void reconnectingIn(final int seconds) {
        GTRobotConnectionListener.log
                .warn("Connection closed, reconnecting...");
    }

    public void reconnectionFailed(final Exception e) {
        GTRobotConnectionListener.log.error("Reconnection failed: ", e);
    }

    public void reconnectionSuccessful() {
        GTRobotConnectionListener.log.info("Reconnection successfully!");
    }

}
