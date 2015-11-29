package to.kit.personal.making;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import to.kit.personal.dto.KanjiName;

/**
 * 漢字・カナの組み合わせを生成.
 * @author Hidetaka Sasai
 */
public final class KanjiKanaMaker extends SimpleChooser<KanjiName> {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(KanjiKanaMaker.class);
	private final UnaryOperator<String> kanaConverter;

	private List<String> loadAll(String resourceName) {
		List<String> list;

		try (InputStream in = ListChooser.class.getResourceAsStream(resourceName)) {
			list = IOUtils.readLines(in, Charset.defaultCharset());
		} catch (IOException e) {
			LOG.error(e.getMessage());
			list = new ArrayList<>();
		}
		return list;
	}

	@Override
	protected List<KanjiName> load(String... resources) {
		List<KanjiName> list = new ArrayList<>();

		for (String line : loadAll(resources[0])) {
			String[] csv = line.split(",");

			list.add(new KanjiName(csv[1], csv[0]));
		}
		return list;
	}

	@Override
	public KanjiName next(String... conditions) {
		int rank = (int) (Math.random() * getMax());
		int ix = (int) (Math.random() * rank);
		KanjiName result = getCandidateList().get(ix);
		String kana = result.getKana();

		if (this.kanaConverter != null) {
			kana = this.kanaConverter.apply(kana);
		}
		result.setKana(kana);
		setChose(result);
		return result;
	}

	/**
	 * インスタンス生成.
	 * @param resourceName 読み込むリソース名
	 * @param converter 「かな」のコンバーター
	 */
	public KanjiKanaMaker(String resourceName, UnaryOperator<String> converter) {
		super(resourceName);
		this.kanaConverter = converter;
	}
	/**
	 * インスタンス生成.
	 * @param resourceName 読み込むリソース名
	 */
	public KanjiKanaMaker(String resourceName) {
		this(resourceName, null);
	}
}
