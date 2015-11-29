package to.kit.personal.util;

import java.lang.Character.UnicodeBlock;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class NameUtils {
	private static final String HALF_KANA = "ｧ ｱ ｨ ｲ ｩ ｳ ｪ ｴ ｫ ｵ ｶ ｶﾞｷ ｷﾞｸ ｸﾞｹ ｹﾞｺ ｺﾞｻ ｻﾞｼ ｼﾞｽ ｽﾞｾ ｾﾞｿ ｿﾞﾀ ﾀﾞﾁ ﾁﾞｯ ﾂ ﾂﾞﾃ ﾃﾞﾄ ﾄﾞﾅ ﾆ ﾇ ﾈ ﾉ ﾊ ﾊﾞﾊﾟﾋ ﾋﾞﾋﾟﾌ ﾌﾞﾌﾟﾍ ﾍﾞﾍﾟﾎ ﾎﾞﾎﾟﾏ ﾐ ﾑ ﾒ ﾓ ｬ ﾔ ｭ ﾕ ｮ ﾖ ﾗ ﾘ ﾙ ﾚ ﾛ * ﾜ ｲ ｴ ｦ ﾝ ｳﾞ";
	private static final String SUUJI = "〇一二三四五六七八九";

	private static UnaryOperator<Character> toFull = ch -> {
		char c = ch.charValue();

		if ('!' <= c && c <= '~') {
			return Character.valueOf((char) ('！' + (c - '!')));
		}
		return ch;
	};

	private NameUtils() {
		// nop
	}

	public static String toFull(final String str) {
		StringBuilder buff = new StringBuilder();

		for (final char c : str.toCharArray()) {
			Character ch = Character.valueOf(c);

			ch = toFull.apply(ch);
			buff.append(ch);
		}
		return buff.toString();
	}


	/**
	 * 漢数字に変換.<br/>
	 * 1桁のみ対応.
	 * @param str 文字列
	 * @return 変換後の文字列
	 */
	public static String toKansuuji(final String str) {
		StringBuilder buff = new StringBuilder();

		for (final char c : str.toCharArray()) {
			if ('0' <= c && c <= '9') {
				int ix = c - '0';
				buff.append(SUUJI.charAt(ix));
				continue;
			}
			buff.append(c);
		}
		return buff.toString();
	}

	/**
	 * ひらがなに変換.<br/>
	 * 半角カナおよび全角カナをひらがなに変換
	 * @param str 文字列
	 * @return 変換後の文字列
	 */
	public static String toHiragana(final String str) {
		StringBuilder buff = new StringBuilder();
		char[] chars = str.toCharArray();
		String punctuation = StringUtils.SPACE;

		ArrayUtils.reverse(chars);
		for (final char c : chars) {
			UnicodeBlock block = UnicodeBlock.of(c);

			if (UnicodeBlock.KATAKANA.equals(block)) {
				int diff = 'ァ' - c;
				char hira = (char) ('ぁ' + diff);

				buff.append(hira);
			} else if (c == 'ｰ') {
				buff.append('ー');
			} else if (c == 'ﾞ' || c == 'ﾟ') {
				punctuation = String.valueOf(c);
			} else if ('ｦ' <= c && c <= 'ﾝ') {
				int ix = HALF_KANA.indexOf(c + punctuation) / 2;
				char hira = (char) ('ぁ' + ix);

				buff.append(hira);
				punctuation = StringUtils.SPACE;
			} else {
				buff.append(c);
			}
		}
		return buff.reverse().toString();
	}

	public static String toHalfKana(final String str) {
		StringBuilder buff = new StringBuilder();

		for (final char c : str.toCharArray()) {
			UnicodeBlock block = UnicodeBlock.of(c);

			if (UnicodeBlock.KATAKANA.equals(block)) {
				int ix = (c - 'ァ') * 2;

				if (0 <= ix && ix < HALF_KANA.length()) {
					String kana = HALF_KANA.substring(ix, ix + 2);

					buff.append(kana.trim());
				} else if (c == 'ー') {
					buff.append('ｰ');
				} else {
					buff.append("***");
				}
			} else if (UnicodeBlock.HIRAGANA.equals(block)) {
				int ix = (c - 'ぁ') * 2;

				if (0 <= ix && ix < HALF_KANA.length()) {
					String kana = HALF_KANA.substring(ix, ix + 2);

					buff.append(kana.trim());
				} else if (c == 'ー') {
					buff.append('ｰ');
				} else {
					buff.append("*" + c + "*");
				}
			} else {
				buff.append(c);
			}
		}
		return buff.toString();
	}
}
