package com.gtrobot.utils;

import java.util.Hashtable;
import java.util.Map;

import com.gtrobot.command.AvailableCommand;
import com.gtrobot.command.AwayCommand;
import com.gtrobot.command.BroadcastMessageCommand;
import com.gtrobot.command.EchoCommand;
import com.gtrobot.command.HelpCommand;
import com.gtrobot.command.InvalidCommand;
import com.gtrobot.command.LangCommand;
import com.gtrobot.command.PrivateMessageCommand;
import com.gtrobot.command.SearchUserCommand;
import com.gtrobot.command.StatusCommand;
import com.gtrobot.processor.AvailableProcessor;
import com.gtrobot.processor.AwayProcessor;
import com.gtrobot.processor.BroadcastMessageProcessor;
import com.gtrobot.processor.EchoProcessor;
import com.gtrobot.processor.ErrorProcessor;
import com.gtrobot.processor.HelpProcessor;
import com.gtrobot.processor.InvalidCommandProcessor;
import com.gtrobot.processor.LangProcessor;
import com.gtrobot.processor.PrivateMessageProcessor;
import com.gtrobot.processor.SearchUserProcessor;
import com.gtrobot.processor.StatusProcessor;

public class ParameterTable {
	private static final Hashtable commandMappings = new Hashtable();
	static {
		commandMappings.put("help", HelpCommand.class);
		commandMappings.put("nochat", AwayCommand.class);
		commandMappings.put("away", AwayCommand.class);
		commandMappings.put("chat", AvailableCommand.class);
		commandMappings.put("available", AvailableCommand.class);
		commandMappings.put("echo", EchoCommand.class);
		commandMappings.put("lang", LangCommand.class);
		commandMappings.put("sc", SearchUserCommand.class);
		commandMappings.put("searchuser", SearchUserCommand.class);
		commandMappings.put("st", StatusCommand.class);
		commandMappings.put("status", StatusCommand.class);
		commandMappings.put("pm", PrivateMessageCommand.class);
		commandMappings.put("privatemessage", PrivateMessageCommand.class);
		// TODO
	}

	private static final Hashtable commandProcessorsMappings = new Hashtable();
	static {
		commandProcessorsMappings.put("errorProcessor", new ErrorProcessor());
		commandProcessorsMappings.put(BroadcastMessageCommand.class.getName(),
				new BroadcastMessageProcessor());
		commandProcessorsMappings.put(InvalidCommand.class.getName(),
				new InvalidCommandProcessor());
		commandProcessorsMappings.put(HelpCommand.class.getName(),
				new HelpProcessor());
		commandProcessorsMappings.put(AwayCommand.class.getName(),
				new AwayProcessor());
		commandProcessorsMappings.put(AvailableCommand.class.getName(),
				new AvailableProcessor());
		commandProcessorsMappings.put(EchoCommand.class.getName(),
				new EchoProcessor());
		commandProcessorsMappings.put(LangCommand.class.getName(),
				new LangProcessor());
		commandProcessorsMappings.put(SearchUserCommand.class.getName(),
				new SearchUserProcessor());
		commandProcessorsMappings.put(StatusCommand.class.getName(),
				new StatusProcessor());
		commandProcessorsMappings.put(PrivateMessageCommand.class.getName(),
				new PrivateMessageProcessor());
		// TODO
	}

	public static Map getCommadMappings() {
		return commandMappings;
	}

	public static Map getCommadProcessorsMappings() {
		return commandProcessorsMappings;
	}
}
