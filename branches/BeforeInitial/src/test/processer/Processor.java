package test.processer;

import org.jivesoftware.smack.XMPPException;

import test.command.AbstractCommand;
import test.exception.CommandMatchedException;

public interface Processor {
	public void process(AbstractCommand command)
			throws CommandMatchedException, XMPPException;
}
