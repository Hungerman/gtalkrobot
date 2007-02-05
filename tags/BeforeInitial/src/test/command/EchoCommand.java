package test.command;

import java.util.List;

public class EchoCommand extends ProcessableCommand {
	private static final String ON = "on";
	private static final String OFF = "off";
	private String operation;

	public EchoCommand(String user, List argv) {
		super(user, argv);
	}

	protected void parseArgv(List argv) {
		if(argv.size() != 2)
		{
			setErrorMessage("Missing operation mode!");
		}
		operation = ((String)argv.get(1)).trim().toLowerCase();
		if(!(ON.endsWith(operation) || OFF.equals(operation)))
		{
			setErrorMessage("Invalid operation mode!");
		}
		
		super.parseArgv(argv);
	}

	public boolean isOperationON() {
		return ON.equals(operation);
	}
}
