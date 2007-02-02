package test;

import java.util.Hashtable;

import test.command.AbstractCommand;
import test.command.BroadcastMessageCommand;
import test.command.HelpCommand;
import test.command.InvalidCommand;
import test.processer.BroadcastMessageProcessor;
import test.processer.ErrorProcessor;
import test.processer.HelpProcessor;
import test.processer.InvalidCommandProcessor;
import test.processer.Processor;

public class CommadProcessor {
	private static final ErrorProcessor errorProcessor = new ErrorProcessor();
	private static final Hashtable commandProcessors = new Hashtable();
	static {
		commandProcessors.put(BroadcastMessageCommand.class,
				new BroadcastMessageProcessor());
		commandProcessors.put(InvalidCommand.class,
				new InvalidCommandProcessor());
		commandProcessors.put(HelpCommand.class, new HelpProcessor());
		// TODO
	}

	public static void process(AbstractCommand command) {
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
