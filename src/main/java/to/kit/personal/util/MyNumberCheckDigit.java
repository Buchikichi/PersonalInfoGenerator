package to.kit.personal.util;

import java.io.Serializable;

import org.apache.commons.validator.routines.checkdigit.CheckDigit;

/**
 * マイナンバーチェックディジット.
 * @author Hidetaka Sasai
 */
public final class MyNumberCheckDigit implements CheckDigit, Serializable {
    /** 唯一のインスタンス. */
    public static final CheckDigit MY_NUMBER_CHECK_DIGIT = new MyNumberCheckDigit();

    @Override
	public String calculate(final String code) {
		int val = 0;
		int ix = 1;

		for (char c : code.toCharArray()) {
			int p = c - '0';
			int q = 7 - ix % 6;

			val += p * q;
			ix++;
		}
		return String.valueOf((11 - val % 11) % 11 % 10);
	}

	@Override
	public boolean isValid(final String code) {
		int len = code.length() - 1;

		if (len != 11) {
			return false;
		}
		String cd = calculate(code.substring(0, len));

		return cd.equals(code.substring(len));
	}

	private MyNumberCheckDigit() {
		// nop
	}
}
