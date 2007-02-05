package test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.packet.Message;

import test.command.AbstractCommand;
import test.command.AvailableCommand;
import test.command.AwayCommand;
import test.command.BroadcastMessageCommand;
import test.command.EchoCommand;
import test.command.HelpCommand;
import test.command.InvalidCommand;

/**
 * groups.mygtalk.cn: 
*** 命令列表:
/status "/status [message]" 设置或查看机器人程序的状态
/lang "/lang [language]" 设置语言为 "language" 或重新为缺省
/whois "/whois [nick]" 查看某人的状态
/quit "/quit" 永远退出这个聊天室
/away "/away [message]" 设置某人的 "away"(需要输入消息参数) 或 "chat"(无消息参数) 标志
/mode "/mode option" 设置或去除某人的标志。如: "+s" 将过滤掉系统消息，"-s" 可以重新接收系统消息
/leave "/leave" 同 /quit
/me "/me <emote> [<msg>]" 表现你的一个表情
/version "/version" 显示这个机器人程序的版本
/exit "/exit" 同 /quit
/names "/names" 列出聊天室中的全有人
/chat "/chat" 删除某人的 "away" 标志，如同 "/away" 命令
/msg "/msg nick message" 向某人发悄悄话
/listemotes "/listemotes" 列出所有表情串
/nochat "/nochat [message]" 设置某人的 "away" 标志，如同 "/away message" 命令
/listlangs "/listlangs" 列出所有支持的语言
/help "/help" 显示本帮助信息
*** 查看 http://coders.meta.net.nz/~perry/jabber/confbot.php 以了解更多的细节。
查看 http://www.donews.net/limodou 了解关于汉化版的细节。
查看 http://wiki.woodpecker.org.cn/moin/GoogleTalkBot 了解更多体验。
 * @author Joey
 *
 */
public class CommadParser {
	private static final String COMMAND_PREFIX_1 = "/";

	private static final String COMMAND_PREFIX_2 = ":";

	private static final Hashtable commandTable = new Hashtable();
	static {
		commandTable.put("help", HelpCommand.class);
		commandTable.put("nochat", AwayCommand.class);
		commandTable.put("away", AwayCommand.class);
		commandTable.put("chat", AvailableCommand.class);
		commandTable.put("available", AvailableCommand.class);
		commandTable.put("echo", EchoCommand.class);
		// TODO
	}

	public static AbstractCommand parser(Message message) {
		System.out.println("Message       from: " + message.getFrom());
		System.out.println("                to: " + message.getTo());
		System.out.println("          threadId: " + message.getThread());
		System.out.println("          packetId: " + message.getPacketID());
		System.out.println("              type: "
				+ message.getType().toString());
		Iterator propertyNames = message.getPropertyNames();
		while (propertyNames.hasNext()) {
			String name = (String) propertyNames.next();
			System.out.println("          property: " + name + " : "
					+ message.getProperty(name));
		}
		System.out.println("             class: "
				+ message.getClass().getName());
		System.out.println("             error: " + message.getError());
		System.out.println("           subject: " + message.getSubject());
		System.out.println("              body: " + message.getBody());

		String from = message.getFrom();
		String body = message.getBody();
		AbstractCommand command = parse(from, body);
		if(command != null)
		{
			command.setOriginMessage(body);
		}
		return command;
	}

	private static AbstractCommand parse(String user, String body) {
		if (body == null) {
			System.out.println("Warn: message's body is NULL!");
			return null;
		}
		body = body.trim();
		if (!(body.startsWith(COMMAND_PREFIX_1) || body
				.startsWith(COMMAND_PREFIX_2))) {
			return new BroadcastMessageCommand(user, body);
		}

		List commands = parseCommand(body);
		if (commands == null || commands.size() < 1) {
			return new InvalidCommand(user, null);
		}
		String commandName = ((String) commands.get(0)).toLowerCase();
		Class commandClass = (Class) commandTable.get(commandName);
		if (commandClass == null) {
			return new InvalidCommand(user, null);
		}
		try {
			Constructor constructor = commandClass.getConstructor(new Class[] {
					String.class, List.class });
			return (AbstractCommand) constructor.newInstance(new Object[] {
					user, commands });
		} catch (Exception e) {
			e.printStackTrace();
			return new InvalidCommand(user, null);
		}
	}

	private static List parseCommand(String body) {
		List results = new ArrayList();
		StringBuffer tempStr = new StringBuffer();
		for (int i = 1; i < body.length(); i++) {
			char cc = body.charAt(i);

			if (cc == ' ' || cc == '\t' || cc == '\n' || cc == '\r') {
				if (tempStr.length() == 0) {
					continue;
				} else {
					results.add(tempStr.toString());
					tempStr = new StringBuffer();
				}
			} else {
				tempStr.append(cc);
			}
		}
		if (tempStr.length() != 0) {
			results.add(tempStr.toString());
		}
		return results;
	}
}
