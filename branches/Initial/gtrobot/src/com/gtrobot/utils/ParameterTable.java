package com.gtrobot.utils;

import java.util.Hashtable;
import java.util.Map;

import com.gtrobot.command.common.AvailableCommand;
import com.gtrobot.command.common.AwayCommand;
import com.gtrobot.command.common.BroadcastMessageCommand;
import com.gtrobot.command.common.EchoCommand;
import com.gtrobot.command.common.HelpCommand;
import com.gtrobot.command.common.InvalidCommand;
import com.gtrobot.command.common.LangCommand;
import com.gtrobot.command.common.PrivateMessageCommand;
import com.gtrobot.command.common.SearchUserCommand;
import com.gtrobot.command.common.StatusCommand;
import com.gtrobot.command.word.AddWordEntryCommand;
import com.gtrobot.command.word.InteractiveSampleCommand;
import com.gtrobot.processor.common.AvailableProcessor;
import com.gtrobot.processor.common.AwayProcessor;
import com.gtrobot.processor.common.BroadcastMessageProcessor;
import com.gtrobot.processor.common.EchoProcessor;
import com.gtrobot.processor.common.ErrorProcessor;
import com.gtrobot.processor.common.HelpProcessor;
import com.gtrobot.processor.common.InvalidCommandProcessor;
import com.gtrobot.processor.common.LangProcessor;
import com.gtrobot.processor.common.PrivateMessageProcessor;
import com.gtrobot.processor.common.SearchUserProcessor;
import com.gtrobot.processor.common.StatusProcessor;
import com.gtrobot.processor.word.AddWordEntryProcessor;
import com.gtrobot.processor.word.InteractiveProcessorSample;

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
		commandMappings.put("aw", AddWordEntryCommand.class);
		commandMappings.put("addword", AddWordEntryCommand.class);
		commandMappings.put("ips", InteractiveSampleCommand.class);
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
		commandProcessorsMappings.put(AddWordEntryCommand.class.getName(),
				new AddWordEntryProcessor());
		commandProcessorsMappings.put(InteractiveSampleCommand.class.getName(),
				new InteractiveProcessorSample());
		// TODO
	}

	public static Map getCommadMappings() {
		return commandMappings;
	}

	public static Map getCommadProcessorsMappings() {
		return commandProcessorsMappings;
	}
}
