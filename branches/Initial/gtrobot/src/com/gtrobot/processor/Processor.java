package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.exception.CommandMatchedException;


public interface Processor {
	public void process(AbstractCommand command)
			throws CommandMatchedException, XMPPException;
}
