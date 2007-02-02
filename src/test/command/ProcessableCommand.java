package test.command;

public class ProcessableCommand extends AbstractCommand {

	public ProcessableCommand(String user, String[] argv) {
		super(user);

		parseArgv(argv);
	}

	protected void parseArgv(String[] argv) {
		return;
	}
}
