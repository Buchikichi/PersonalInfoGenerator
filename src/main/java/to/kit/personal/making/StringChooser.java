package to.kit.personal.making;

import java.util.Arrays;
import java.util.List;

/**
 * 候補文字列から選択.
 * @author Hidetaka Sasai
 */
public class StringChooser extends SimpleChooser<String> {
	@Override
	protected List<String> load(String... candidates) {
		return Arrays.asList(candidates);
	}

	/**
	 * インスタンス生成.
	 * @param candidates 候補
	 */
	public StringChooser(String... candidates) {
		super(candidates);
	}
}
