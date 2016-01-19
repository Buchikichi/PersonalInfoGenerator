package to.kit.personal.making;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import to.kit.personal.dto.KanjiName;
import to.kit.personal.util.NameUtils;

/**
 * 漢字・カナの組み合わせを生成.
 * @author Hidetaka Sasai
 */
public final class KanjiKanaMaker extends SimpleChooser<KanjiName> {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(KanjiKanaMaker.class);
	private static final UnaryOperator<String> HALF_CONVERTER = s -> NameUtils.toHalfKana(s);
	private static final UnaryOperator<String> KATAKANA_CONVERTER = s -> NameUtils.toKatakana(s);
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
	public KanjiName next() {
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

	@Override
	protected List<KanjiName> load(String... resources) {
		List<KanjiName> list = new ArrayList<>();

		for (String line : loadAll(resources[0])) {
			String[] csv = line.split(",");
			String kanji = NameUtils.shuffle(csv[1]);
			String kana = NameUtils.shuffle(csv[0]);

			list.add(new KanjiName(kanji, kana));
		}
		return list;
	}

	/**
	 * インスタンス生成.
	 * @param resourceName 読み込むリソース名
	 * @param converter 「かな」のコンバーター
	 */
	public KanjiKanaMaker(final String resourceName, final String converter) {
		super(resourceName);
		if (StringUtils.isNotBlank(converter)) {
			String conv = converter.toLowerCase();

			if (conv.startsWith("half")) {
				this.kanaConverter = HALF_CONVERTER;
			} else if (conv.startsWith("katakana")) {
				this.kanaConverter = KATAKANA_CONVERTER;
			} else {
				this.kanaConverter = null;
			}
		} else {
			this.kanaConverter = null;
		}
	}
	/**
	 * インスタンス生成.
	 * @param resourceName 読み込むリソース名
	 */
	public KanjiKanaMaker(final String resourceName) {
		this(resourceName, null);
	}
}
