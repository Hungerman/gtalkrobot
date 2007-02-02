package test.command;

public class InvalidCommand extends ProcessableCommand {
	private String[] argv;

	public InvalidCommand(String user, String[] argv) {
		super(user, argv);
	}

	protected void parseArgv(String[] argv) {
		this.argv = argv;
		
		super.parseArgv(argv);
	}

	public String[] getArgv() {
		return argv;
	}
}
