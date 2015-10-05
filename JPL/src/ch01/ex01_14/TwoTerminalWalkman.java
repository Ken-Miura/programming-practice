package ch01.ex01_14;

public class TwoTerminalWalkman extends Walkman {

	private final Terminal secondTerminal;

	public TwoTerminalWalkman() {
		super();
		this.secondTerminal = new Terminal();
	}

	public Terminal getSecondTerminal() {
		return secondTerminal;
	}

	public void listenToMusicWithOther() {
		// use firstTerminal and secondTerminal to listen to
		// music with other
	}
}
