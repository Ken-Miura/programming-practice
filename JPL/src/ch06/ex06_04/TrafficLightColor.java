package ch06.ex06_04;

public enum TrafficLightColor {
	RED(Color.RED),
	YELLOW(Color.YELLOW),
	GREEN(Color.GREEN);
	
	private final Color color;
	
	private TrafficLightColor(Color color){
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
