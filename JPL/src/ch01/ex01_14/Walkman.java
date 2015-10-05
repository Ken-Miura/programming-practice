package ch01.ex01_14;

public class Walkman {

	private static int nextSerialNumber = 0;
	private final int serialNumber;
	private final Terminal firstTerminal;
	
	public Walkman() {
		this.serialNumber = nextSerialNumber;
		this.firstTerminal = new Terminal();
		nextSerialNumber++;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public Terminal getFirstTerminal() {
		return firstTerminal;
	}
	
	public void listenToMusic() {
		// use firstTerminal to listen to music
	}
}
