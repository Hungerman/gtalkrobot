package com.gtrobot.utils;

import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.processor.Processor;

public class CommandProcessorMapping {
	protected static final transient Log log = LogFactory
			.getLog(CommandProcessorMapping.class);

	public static final String BROADCAST_COMMAND = "broadcast";

	public static final String INVALID_COMMAND = "invalid";

	public static final String COMMANDS_RESOURCE = "commands";

	public static final String PREFIX_KEY = "gtrobot.command";

	private static CommandProcessorMapping instance = new CommandProcessorMapping();

	private Map command2TypeMappings = new Hashtable();

	private Map commandType2CommandMappings = new Hashtable();

	private CommandProcessorMapping() {
		initCommands();
	}

	public static CommandProcessorMapping getInstance() {
		return instance;
	}

	public BaseCommand getBroadcastMessageCommandInstance() {
		return getCommandInstance(BROADCAST_COMMAND);
	}

	public BaseCommand getInvalidCommandInstance() {
		return getCommandInstance(INVALID_COMMAND);
	}

	public BaseCommand getCommandInstance(String command) {
		String commandType = (String) command2TypeMappings.get(command);
		if (commandType == null)
			return null;
		CommandMapping cm = (CommandMapping) commandType2CommandMappings
				.get(commandType);
		if (cm == null)
			return null;

		BaseCommand baseCommand = null;
		try {
			baseCommand = (BaseCommand) cm.getComamndClass().newInstance();
			baseCommand.setCommandType(commandType);
		} catch (Exception e) {
			log.error("Exception in creating command!", e);
		}
		return baseCommand;
	}

	public Processor getProcessor(String commandType) {
		CommandMapping cm = (CommandMapping) commandType2CommandMappings
				.get(commandType);
		if (cm == null)
			return null;
		return cm.getProcessor();
	}

	private void initCommands() {
		ResourceBundle resourceBundle = ResourceBundle
				.getBundle(COMMANDS_RESOURCE);

		String[] commands = parseStringArray(resourceBundle
				.getString(PREFIX_KEY));

		for (int i = 0; i < commands.length; i++) {
			String command = (String) commands[i];
			initCommand(resourceBundle, command);
		}
	}

	private void initCommand(ResourceBundle resourceBundle, String command) {
		String key = PREFIX_KEY + "." + command;

		String[] commandWords = parseStringArray(resourceBundle.getString(key));

		for (int i = 0; i < commandWords.length; i++) {
			String commandWord = commandWords[i];
			if (command2TypeMappings.containsKey(commandWord)) {
				log.error("Same command twice! " + commandWord);
			}
			command2TypeMappings.put(commandWord, command);
		}
		String commandClass = resourceBundle.getString(key + ".class");

		String processorClass = resourceBundle.getString(key
				+ ".processor.class");
		Processor processor = null;
		Class commandClazz = null;
		try {
			commandClazz = Class.forName(commandClass);
			Class processorClazz = Class.forName(processorClass);
			processor = (Processor) processorClazz.newInstance();
		} catch (Exception e) {
			log.error("Parsing command parameter error!", e);
		}

		CommandMapping cm = new CommandMapping();
		cm.setComamndClass(commandClazz);
		cm.setProcessor(processor);
		commandType2CommandMappings.put(command, cm);
	}

	private String[] parseStringArray(String value) {
		if (value == null)
			return null;
		String[] list = value.split(",");
		for (int i = 0; i < list.length; i++) {
			list[i] = list[i].trim();
		}
		return list;
	}
}

class CommandMapping {
	private Class comamndClass;

	private Processor processor;

	public Class getComamndClass() {
		return comamndClass;
	}

	public Processor getProcessor() {
		return processor;
	}

	public void setComamndClass(Class comamndClass) {
		this.comamndClass = comamndClass;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}
}
