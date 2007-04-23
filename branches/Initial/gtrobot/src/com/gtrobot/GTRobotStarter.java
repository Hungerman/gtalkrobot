package com.gtrobot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.engine.GTRobotContextHelper;
import com.gtrobot.engine.GoogleTalkConnection;

/**
 * 程序的入口 <br>
 * 初始化Spring的Context，初始化Connection，初始化ThreadWorkerDispatcher
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
			// 初始化Spring的Context
			GTRobotContextHelper.initApplicationContext();

			// 初始化Connection
			// org.jivesoftware.smack.XMPPConnection.DEBUG_ENABLED = true;
			GoogleTalkConnection googleTalkConnection = GTRobotContextHelper
					.getGoogleTalkConnection();
			googleTalkConnection.init();

			// 初始化ThreadWorkerDispatcher
			GTRobotContextHelper.getWorkerDispatcher();

			return true;
		} catch (Exception e) {
			log.error("System error!", e);
			return false;
		}
	}
}
