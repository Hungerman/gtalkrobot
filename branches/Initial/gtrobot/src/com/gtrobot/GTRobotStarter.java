package com.gtrobot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.engine.GTRobotContext;
import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.engine.GoogleTalkConnection;

/**
 * Main enterance of GTRobot. <br>
 * Initialize configuration, cache, database connection, XMPPConnection, and
 * roster listener, packet listener.
 * 
 * 
 * @author sunyuxin
 * 
 */
public class GTRobotStarter {
	protected static final transient Log log = LogFactory
			.getLog(GTRobotStarter.class);

	public static void main(String[] args) {
		GTRobotStarter gtrobot = new GTRobotStarter();
		boolean success = gtrobot.startup();
		if (!success) {
			log.error("GTRobot startup failed!");
			return;
		}
	}

	private boolean startup() {
		try {
			// Load and check the system paramter
			GTRobotContext.getContext();

			// Setup the XMPP connection
			// org.jivesoftware.smack.XMPPConnection.DEBUG_ENABLED = true;
			GoogleTalkConnection googleTalkConnection = GTRobotContextHelper
					.getGoogleTalkConnection();
			googleTalkConnection.init();

			// Setup the worker thread pook and dispatcher
			GTRobotContextHelper.getWorkerDispatcher();

			return true;
		} catch (Exception e) {
			log.error("System error!", e);
			return false;
		}
	}
}
