package com.gtrobot.processor;

import org.jivesoftware.smack.XMPPException;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.exception.CommandMatchedException;

public interface Processor {
	public void process(BaseCommand command) throws CommandMatchedException,
			XMPPException;
}
