package to.kit.personal.making;

/**
 * 数値を生成.
 * @author Hidetaka Sasai
 */
public final class NumberMaker implements InfoMaker<Integer> {
	private int min;
	private int range;
	private Integer chose;

	@Override
	public Integer next(String... conditions) {
		int value = this.min + (int) (Math.random() * this.range);

		this.chose = Integer.valueOf(value);
		return this.chose;
	}

	@Override
	public Integer current() {
		return this.chose;
	}

	/**
	 * インスタンス生成.
	 * @param min 最小値
	 * @param max 最大値
	 */
	public NumberMaker(int min, int max) {
		this.min = min;
		this.range = max - min;
	}
}
