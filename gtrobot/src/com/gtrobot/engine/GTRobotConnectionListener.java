package com.gtrobot.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.ConnectionListener;

public class GTRobotConnectionListener implements ConnectionListener {
	protected static final transient Log log = LogFactory
			.getLog(GTRobotConnectionListener.class);

	public void connectionClosed() {
		log.warn("Connection closed!");
	}

	public void connectionClosedOnError(Exception e) {
		log.error("Connection closed with error: ", e);
	}

	public void reconnectingIn(int seconds) {
		log.warn("Connection closed, reconnecting...");
	}

	public void reconnectionFailed(Exception e) {
		log.error("Reconnection failed: ", e);
	}

	public void reconnectionSuccessful() {
		log.info("Reconnection successfully!");
	}

}
