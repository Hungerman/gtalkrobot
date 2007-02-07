package com.gtrobot;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.AvailableCommand;
import com.gtrobot.command.AwayCommand;
import com.gtrobot.command.BroadcastMessageCommand;
import com.gtrobot.command.EchoCommand;
import com.gtrobot.command.HelpCommand;
import com.gtrobot.command.InvalidCommand;
import com.gtrobot.command.LangCommand;
import com.gtrobot.command.SearchUserCommand;
import com.gtrobot.processor.AvailableProcessor;
import com.gtrobot.processor.AwayProcessor;
import com.gtrobot.processor.BroadcastMessageProcessor;
import com.gtrobot.processor.EchoProcessor;
import com.gtrobot.processor.ErrorProcessor;
import com.gtrobot.processor.HelpProcessor;
import com.gtrobot.processor.InvalidCommandProcessor;
import com.gtrobot.processor.LangProcessor;
import com.gtrobot.processor.Processor;
import com.gtrobot.processor.SearchUserProcessor;
import com.gtrobot.utils.MessageBundle;

public class CommadProcessor {
	protected static final transient Log log = LogFactory
			.getLog(CommadProcessor.class);

	private static final ErrorProcessor errorProcessor = new ErrorProcessor();

	private static final Hashtable commandProcessors = new Hashtable();
	static {
		commandProcessors.put(BroadcastMessageCommand.class,
				new BroadcastMessageProcessor());
		commandProcessors.put(InvalidCommand.class,
				new InvalidCommandProcessor());
		commandProcessors.put(HelpCommand.class, new HelpProcessor());
		commandProcessors.put(AwayCommand.class, new AwayProcessor());
		commandProcessors.put(AvailableCommand.class, new AvailableProcessor());
		commandProcessors.put(EchoCommand.class, new EchoProcessor());
		commandProcessors.put(LangCommand.class, new LangProcessor());
		commandProcessors.put(SearchUserCommand.class,
				new SearchUserProcessor());
		// TODO
	}

	public static void process(AbstractCommand command) {
		if (command == null)
			return;
		Class commandClass = command.getClass();
		Processor processor = (Processor) commandProcessors.get(commandClass);
		if (processor == null) {
			processor = errorProcessor;
			String sysError = MessageBundle.getInstance().getMessage(
					"system.error.processor.null");
			log.warn(sysError + "with command: " + command.getClass().getName()
					+ " origin message: " + command.getOriginMessage());

			command.setErrorMessage(sysError);
		}

		try {
			processor.process(command);
		} catch (Exception e) {
			log.error("Processing error!", e);
		}
	}
}
