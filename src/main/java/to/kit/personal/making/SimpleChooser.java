package to.kit.personal.making;

import java.util.List;

/**
 * 選択.
 * @param <T> 選択するデータ型
 * @author Hidetaka Sasai
 */
public abstract class SimpleChooser<T> implements InfoMaker<T> {
	private final List<T> candidateList;
	private final int max;
	private T chose;

	protected abstract List<T> load(String... resources);

	protected List<T> getCandidateList() {
		return this.candidateList;
	}

	protected int getMax() {
		return this.max;
	}

	protected void setChose(T val) {
		this.chose = val;
	}

	protected int rand(int hi) {
		int rand = (int) (Math.random() * hi);

		return rand;
	}

	@Override
	public T next() {
		int ix = rand(this.max);

		this.chose = this.candidateList.get(ix);
		return this.chose;
	}

	@Override
	public T current() {
		return this.chose;
	}

	/**
	 * インスタンス生成.
	 * @param resources リソース
	 */
	public SimpleChooser(String... resources) {
		this.candidateList = load(resources);
		this.max = this.candidateList.size();
	}
}
