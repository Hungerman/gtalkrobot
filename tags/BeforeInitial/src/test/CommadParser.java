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
*** �����б�:
/status "/status [message]" ���û�鿴�����˳����״̬
/lang "/lang [language]" ��������Ϊ "language" ������Ϊȱʡ
/whois "/whois [nick]" �鿴ĳ�˵�״̬
/quit "/quit" ��Զ�˳����������
/away "/away [message]" ����ĳ�˵� "away"(��Ҫ������Ϣ����) �� "chat"(����Ϣ����) ��־
/mode "/mode option" ���û�ȥ��ĳ�˵ı�־����: "+s" �����˵�ϵͳ��Ϣ��"-s" �������½���ϵͳ��Ϣ
/leave "/leave" ͬ /quit
/me "/me <emote> [<msg>]" �������һ������
/version "/version" ��ʾ��������˳���İ汾
/exit "/exit" ͬ /quit
/names "/names" �г��������е�ȫ����
/chat "/chat" ɾ��ĳ�˵� "away" ��־����ͬ "/away" ����
/msg "/msg nick message" ��ĳ�˷����Ļ�
/listemotes "/listemotes" �г����б��鴮
/nochat "/nochat [message]" ����ĳ�˵� "away" ��־����ͬ "/away message" ����
/listlangs "/listlangs" �г�����֧�ֵ�����
/help "/help" ��ʾ��������Ϣ
*** �鿴 http://coders.meta.net.nz/~perry/jabber/confbot.php ���˽�����ϸ�ڡ�
�鿴 http://www.donews.net/limodou �˽���ں������ϸ�ڡ�
�鿴 http://wiki.woodpecker.org.cn/moin/GoogleTalkBot �˽�������顣
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
