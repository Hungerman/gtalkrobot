package com.gtrobot;

import java.util.Hashtable;

import com.gtrobot.command.AbstractCommand;
import com.gtrobot.command.AvailableCommand;
import com.gtrobot.command.AwayCommand;
import com.gtrobot.command.BroadcastMessageCommand;
import com.gtrobot.command.EchoCommand;
import com.gtrobot.command.HelpCommand;
import com.gtrobot.command.InvalidCommand;
import com.gtrobot.processor.AvailableProcessor;
import com.gtrobot.processor.AwayProcessor;
import com.gtrobot.processor.BroadcastMessageProcessor;
import com.gtrobot.processor.EchoProcessor;
import com.gtrobot.processor.ErrorProcessor;
import com.gtrobot.processor.HelpProcessor;
import com.gtrobot.processor.InvalidCommandProcessor;
import com.gtrobot.processor.Processor;


public class CommadProcessor {
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
		// TODO
	}

	public static void process(AbstractCommand command) {
		if(command == null)
			return;
		Class commandClass = command.getClass();
		Processor processor = (Processor) commandProcessors.get(commandClass);
		if(processor == null)
		{
			processor = errorProcessor; 
			command.setErrorMessage("System error while Processor is NULL!");
		}
		
		try {
			processor.process(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
