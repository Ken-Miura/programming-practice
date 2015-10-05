package ch03.ex03_03;

public class Y extends X {

	// staticをつけてクラス初期化時に値を設定して、クラスXのフィールド初期化時とコンストラクタ実行時にデフォルト値を使うことを避ける
	protected static int yMask = 0xff00;

	public Y() {
		fullMask |= yMask;
	}

	public static void main(String... args) {
		new Y();
	}
}
