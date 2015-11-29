package to.kit.personal.dto;

/**
 * 漢字名とかなのセット.
 * @author Hidetaka Sasai
 */
public final class KanjiName {
	private String kanji;
	private String kana;

	public String getKanji() {
		return this.kanji;
	}
	public void setKanji(String kanji) {
		this.kanji = kanji;
	}
	public String getKana() {
		return this.kana;
	}
	public void setKana(String kana) {
		this.kana = kana;
	}

	@Override
	public String toString() {
		return this.kana + '\t' + this.kanji;
	}

	/**
	 * インスタンス生成.
	 * @param kanji 漢字
	 * @param kana かな
	 */
	public KanjiName(final String kanji, final String kana) {
		this.kanji = kanji;
		this.kana = kana;
	}

	/**
	 * インスタンス生成.
	 */
	public KanjiName() {
		// nop
	}
}
