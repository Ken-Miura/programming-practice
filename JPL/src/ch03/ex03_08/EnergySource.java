package ch03.ex03_08;

/**
 * 動力源を表す抽象クラス
 * 
 * @author Ken
 *
 */
/* ex03_06.EnergySourceを修正 */
public abstract class EnergySource implements Cloneable {

	/**
	 * 動力源が空かどうかを調べるメソッド
	 * 
	 * @return 動力源が空の場合、trueを返す。そうでない場合、falseを返す。
	 */
	public abstract boolean empty();

	/**
	 * 動力源の残量を確認する
	 * 
	 * @return 動力源の残量
	 */
	public abstract int observeRemainingEnergy();

	/**
	 * 動力源からエネルギーを取得するメソッド
	 * 
	 * @param energy
	 *            取得したいエネルギー量
	 * @return 実際に取得できたエネルギー量
	 */
	public abstract int retrieve(int energy);

	/**
	 * 動力源にエネルギーを補充するメソッド
	 * 
	 * @param energy
	 *            補充するエネルギー量
	 */
	public abstract void fill(int energy);

	/* ex03_08で追加 */
	@Override
	protected EnergySource clone() {
		EnergySource energySource = null;
		try {
			energySource = (EnergySource) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
		return energySource;
	}
}
