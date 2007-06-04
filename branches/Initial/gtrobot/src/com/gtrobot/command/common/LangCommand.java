package com.gtrobot.command.common;

import java.util.List;
import java.util.Locale;

import com.gtrobot.command.ErrorType;
import com.gtrobot.command.ProcessableCommand;
import com.gtrobot.utils.CommonUtils;

/**
 * 设置Locale的命令对象。
 * 
 * @author Joey
 * 
 */
public class LangCommand extends ProcessableCommand {
    private String operation;

    private Locale locale;

    @Override
    public void parseArgv(final List argv) {
        if (argv.size() != 2) {
            this.setError(ErrorType.wrongParameter);
            return;
        }
        this.operation = ((String) argv.get(1)).trim().toLowerCase();
        this.locale = CommonUtils.parseLocale(this.operation);
        if (this.locale == null) {
            this.setError(ErrorType.wrongParameter);
        }

        super.parseArgv(argv);
    }

    public Locale getOperationLocale() {
        return new Locale(this.operation);
    }

    public String getOperation() {
        return this.operation;
    }
}
