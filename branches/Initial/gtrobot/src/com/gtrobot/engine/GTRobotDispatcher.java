package com.gtrobot.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;

import com.gtrobot.command.BaseCommand;
import com.gtrobot.command.ErrorType;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.model.common.AccountType;
import com.gtrobot.model.common.UserEntry;
import com.gtrobot.processor.InteractiveProcessor;
import com.gtrobot.processor.Processor;
import com.gtrobot.thread.WorkerDispatcher;
import com.gtrobot.utils.CommonUtils;
import com.gtrobot.utils.UserSessionUtil;

/**
 * 从GTRobotMessageListener处接收得到Message，然后进行分析，得到（生成）Comamnd对象，然后调用把Command对象传递给WorkerDispatcher进行业务处理。
 * <p>
 * 
 * defaultCommand定义了命令解析失败的时候，缺省的Command对象。<br>
 * commandMappings定义了所有Comamnd Name对应的Command对象的对应关系。
 * <p>
 * 
 * "/"和":"开头的命令，为全局类型的命令。因为在这里进行解析处理，所以优先级最高。<br>
 * 
 * @author Joey
 * 
 */
public class GTRobotDispatcher {
    protected static final transient Log log = LogFactory
            .getLog(GTRobotDispatcher.class);

    public static final String COMMAND_PREFIX_1 = "/";

    public static final String COMMAND_PREFIX_2 = ":";

    private String defaultCommand = null;

    private final Map<String, String> commandMappings = new HashMap<String, String>();

    public void setDefaultCommand(final String defaultCommand) {
        this.defaultCommand = defaultCommand;
    }

    @SuppressWarnings("unchecked")
    public void setCommandMappings(final Properties mappings) {
        this.commandMappings
                .putAll((Map<? extends String, ? extends String>) mappings);
    }

    public void parseAndDispatch(final Message message) {
        final BaseCommand commnad = this.parse(message);
        if (commnad == null) {
            return;
        }

        final WorkerDispatcher workerDispatcher = GTRobotContextHelper
                .getWorkerDispatcher();
        workerDispatcher.triggerEvent(commnad);
    }

    /**
     * Parse the message and return a command object
     * 
     * @param message
     * @return
     */
    private BaseCommand parse(final Message message) {
        // if (log.isDebugEnabled()) {
        // log.debug("Message from: " + message.getFrom());
        // log.debug(" to: " + message.getTo());
        // log.debug(" threadId: " + message.getThread());
        // log.debug(" packetId: " + message.getPacketID());
        // log.debug(" type: " + message.getType().toString());
        // Iterator<String> propertyNames = message.getPropertyNames()
        // .iterator();
        // while (propertyNames.hasNext()) {
        // String name = propertyNames.next();
        // log.debug(" property: " + name + " : "
        // + message.getProperty(name));
        // }
        // log.debug(" class: " + message.getClass().getName());
        // log.debug(" error: " + message.getError());
        // log.debug(" subject: " + message.getSubject());
        // log.debug(" body: " + message.getBody());
        // }

        final String from = message.getFrom();
        final String body = message.getBody();
        GTRobotDispatcher.log.info("Message from <" + from + ">: " + body);
        final BaseCommand command = this.parse(from, body);
        return command;
    }

    /**
     * Parse the command string and contruct comamnd object
     * 
     * @param jid
     * @param commandStr
     * @return
     */
    private BaseCommand parse(final String from, final String body) {
        if (body == null) {
            return null;
        }
        // Trim the message body to parse the command prefix
        final String jid = StringUtils.parseBareAddress(from);
        final UserEntry userEntry = GTRobotContextHelper.getUserEntryService()
                .getUserEntry(jid);
        final String commandStr = body.trim();
        BaseCommand command = null;

        if (!userEntry.getAccountType().equals(AccountType.blocked)) {
            command = this.parseCommand(jid, commandStr);
        } else { // AccountType.blocked
            command = this.getDefaultCommand();
            command.setError(ErrorType.accountLocked);
        }

        command.setFrom(from);
        command.setOriginMessage(body.trim());
        command.setUserEntry(userEntry);

        return command;
    }

    private BaseCommand parseCommand(final String jid, final String commandStr) {
        boolean isImmediateCommand = false;
        List<String> commands = null;
        BaseCommand command = null;

        if (commandStr.startsWith(GTRobotDispatcher.COMMAND_PREFIX_1)
                || commandStr.startsWith(GTRobotDispatcher.COMMAND_PREFIX_2)) {
            isImmediateCommand = true;
        }

        // Parse the command and parameters
        commands = CommonUtils.parseCommand(commandStr, isImmediateCommand);
        if ((commands == null) || (commands.size() < 1)) {
            return this.getDefaultCommand();
        }

        final BaseCommand previousCommand = this.getPreviousCommand(jid);
        if (!isImmediateCommand) {
            command = previousCommand;
        } else {
            final String commandType = (commands.get(0)).toLowerCase();
            // Repeat the previous command
            try {
                // Construct a new command object according to the command
                // info
                command = this.getCommandByType(commandType);
                if (command == null) {
                    // if (commandType.equals(COMMAND_PREFIX_1)
                    // || commandType.equals(COMMAND_PREFIX_2)) {
                    // command = previousCommand;
                    // }
                } else {
                    final Processor processor = command.getProcessor();
                    // When you are in an interactive operations, if you
                    // want to
                    // enter a new
                    // interactive operation, please exit the current
                    // operation
                    // first.
                    if ((processor instanceof InteractiveProcessor)
                            && (previousCommand != null)) {
                        if (!command.getCommandType().equals(
                                previousCommand.getCommandType())) {
                            command.setError(ErrorType.reEnterInteractive);
                        } else {
                            command.setError(ErrorType.reEnterSameInteractive);
                        }
                    }
                }
            } catch (final Exception e) {
                GTRobotDispatcher.log.error("Exception in parsing command!", e);
            }
        }

        if (command == null) {
            command = this.getDefaultCommand();
        }

        command.setArgv(commands);
        command.setProcessed(false);
        if (command instanceof ProcessableCommand) {
            ((ProcessableCommand) command).parseArgv(commands);
        }
        return command;
    }

    private BaseCommand getPreviousCommand(final String jid) {
        final BaseCommand previousCommand = UserSessionUtil
                .retrievePreviousCommand(jid);
        if (previousCommand != null) {
            previousCommand.setError(null);
        }
        return previousCommand;
    }

    private BaseCommand getCommandByType(final String commandType) {
        if (commandType == null) {
            return null;
        }
        final String commandBeanName = this.commandMappings.get(commandType);
        if (commandBeanName == null) {
            return null;
        }
        final BaseCommand command = this.getCommandByBeanName(commandBeanName);
        return command;
    }

    private BaseCommand getCommandByBeanName(final String commandBeanName) {
        return (BaseCommand) GTRobotContextHelper.getBean(commandBeanName);
    }

    private BaseCommand getDefaultCommand() {
        return this.getCommandByBeanName(this.defaultCommand);
    }
}
