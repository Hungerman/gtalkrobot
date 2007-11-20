package com.gtrobot.command.common;

import java.util.List;

import com.gtrobot.command.ErrorType;
import com.gtrobot.command.ProcessableCommand;

/**
 * 查询在线用户的命令。
 * 
 * @author Joey
 * 
 */
public class SearchUserCommand extends ProcessableCommand {
    private String condition;

    @Override
    public void parseArgv(final List argv) {
        if (argv.size() > 2) {
            this.setError(ErrorType.wrongParameter);
            return;
        }
        if (argv.size() == 2) {
            this.condition = ((String) argv.get(1)).trim();
        }

        super.parseArgv(argv);
    }

    public String getCondition() {
        return this.condition;
    }
}
