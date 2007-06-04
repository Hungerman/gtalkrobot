package com.gtrobot.command.common;

import java.util.List;

import com.gtrobot.command.ErrorType;
import com.gtrobot.command.ProcessableCommand;

/**
 * 发送秘密消息的命令。
 * 
 * @author Joey
 * 
 */
public class PrivateMessageCommand extends ProcessableCommand {
    private String targetJid;

    @Override
    public void parseArgv(final List argv) {
        if (argv.size() < 3) {
            this.setError(ErrorType.wrongParameter);
            return;
        }
        this.targetJid = ((String) argv.get(1)).trim().toLowerCase();

        super.parseArgv(argv);
    }

    public String getMessageContent() {
        final int pos = this.getOriginMessage().indexOf(this.targetJid);

        return this.getOriginMessage().substring(
                pos + this.targetJid.length() + 1).trim();
    }

    public String getTargetJid() {
        String jid = this.targetJid;
        if (jid.indexOf('@') == -1) {
            jid = jid + "@gmail.com";
        }
        return jid;
    }
}
