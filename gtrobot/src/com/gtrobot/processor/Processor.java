package com.gtrobot.processor;

import com.gtrobot.command.BaseCommand;

public interface Processor {
	public void process(BaseCommand command);
}
