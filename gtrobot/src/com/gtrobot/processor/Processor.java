package com.gtrobot.processor;

import com.gtrobot.command.BaseCommand;

/**
 * 具体业务的处理接口。
 * 
 * @author Joey
 * 
 */
public interface Processor {
    public void process(BaseCommand command);
}
