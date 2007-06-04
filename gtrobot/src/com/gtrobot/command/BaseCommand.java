package com.gtrobot.command;

import java.util.List;

import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.Processor;

/**
 * Command对象的基类。
 * Command对象应该配置在Spring的gtrobot-command-context.xml中，并且要使用非Singleton的方式进行配置。
 * 
 * @author Joey
 * 
 */
public class BaseCommand {
    /**
     * Comamnd对应的Processor实例。通过Spring配置。
     */
    private Processor processor;

    /**
     * Command的类型，唯一确定一个Command的类型。
     */
    private String commandType;

    /**
     * 根据当时访问关联的用户对象。
     */
    private UserEntry userEntry;

    /**
     * 原始的消息信息。
     */
    private String originMessage;

    /**
     * 消息的原始From信息。
     */
    private String from;

    /**
     * 错误类型。
     */
    private ErrorType error = null;;

    /**
     * 根据分隔符解析后的命令和参数列表。
     */
    private List<String> argv;

    /**
     * 根据第一个分隔符解析后的命令和参数列表。
     */
    List<String> interactiveCommands;

    /**
     * 主要使用在交互式处理中，表示这个Command是否已经被处理过了。
     */
    boolean isProcessed = false;

    public Processor getProcessor() {
        return this.processor;
    }

    public void setProcessor(final Processor processor) {
        this.processor = processor;
    }

    public String getCommandType() {
        return this.commandType;
    }

    public void setCommandType(final String commandType) {
        this.commandType = commandType;
    }

    public ErrorType getError() {
        return this.error;
    }

    public void setError(final ErrorType errorType) {
        this.error = errorType;
    }

    public String getOriginMessage() {
        return this.originMessage;
    }

    public void setOriginMessage(final String orginMessage) {
        this.originMessage = orginMessage;
    }

    public List<String> getArgv() {
        return this.argv;
    }

    public void setArgv(final List<String> argvs) {
        this.argv = argvs;
    }

    public UserEntry getUserEntry() {
        return this.userEntry;
    }

    public void setUserEntry(final UserEntry userEntry) {
        this.userEntry = userEntry;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public List<String> getInteractiveCommands() {
        return this.interactiveCommands;
    }

    public void setInteractiveCommands(final List<String> interactiveCommands) {
        this.interactiveCommands = interactiveCommands;
    }

    public boolean isProcessed() {
        return this.isProcessed;
    }

    public void setProcessed(final boolean isProcessed) {
        this.isProcessed = isProcessed;
    }
}
