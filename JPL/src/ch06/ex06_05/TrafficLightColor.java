package ch06.ex06_05;

/* getColorはenum定数毎に異なる振る舞いをするメソッドなので定数固有のメソッドを使うことを推奨する。 */
public enum TrafficLightColor {
	RED {
		@Override
		public Color getColor() {
			return Color.RED;
		}
	},
	YELLOW {
		@Override
		public Color getColor() {
			return Color.YELLOW;
		}
	},
	GREEN {
		@Override
		public Color getColor() {
			return Color.GREEN;
		}
	};

	public abstract Color getColor();
}
