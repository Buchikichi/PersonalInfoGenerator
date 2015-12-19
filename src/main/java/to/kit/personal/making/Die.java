package to.kit.personal.making;

/**
 * さいころ.
 * @author Hidetaka Sasai
 */
public final class Die implements InfoMaker<Integer> {
	private Integer chose;

	/**
	 * さいころを振る.
	 * @param max 最大値
	 * @return 値
	 */
	public Integer next(int max) {
		int value = (int) (Math.random() * max);

		this.chose = Integer.valueOf(value);
		return this.chose;
	}

	@Override
	public Integer next() {
		return next(Integer.MAX_VALUE);
	}

	@Override
	public Integer current() {
		return this.chose;
	}

	/**
	 * インスタンス生成.
	 */
	public Die() {
	}
}
