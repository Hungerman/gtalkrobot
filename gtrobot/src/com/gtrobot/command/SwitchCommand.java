package com.gtrobot.command;

import java.util.List;

/**
 * 对于简单的on和off参数类型的命令。
 * 
 * @author Joey
 * 
 */
public class SwitchCommand extends ProcessableCommand {
    private static final String ON = "on";

    private static final String OFF = "off";

    private String operation;

    @Override
    public void parseArgv(final List argv) {
        if (argv.size() != 2) {
            this.setError(ErrorType.wrongParameter);
            return;
        }
        this.operation = ((String) argv.get(1)).trim().toLowerCase();
        if (!(SwitchCommand.ON.endsWith(this.operation) || SwitchCommand.OFF
                .equals(this.operation))) {
            this.setError(ErrorType.wrongParameter);
        }

        super.parseArgv(argv);
    }

    public boolean isOperationON() {
        return SwitchCommand.ON.equals(this.operation);
    }
}
