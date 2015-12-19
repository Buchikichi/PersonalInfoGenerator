package to.kit.personal.making;

import java.util.Arrays;

import org.apache.commons.validator.routines.checkdigit.CheckDigit;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;

import to.kit.personal.util.MyNumberCheckDigit;

/**
 * マイナンバー(12桁)を生成.
 * @author Hidetaka Sasai
 */
public final class MyNumberMaker implements InfoMaker<String> {
	private static final int RETRY = 10;
	private final NumberMaker nm = new NumberMaker(1, 9999);
	private final CheckDigit checkDigit = MyNumberCheckDigit.MY_NUMBER_CHECK_DIGIT;
	private String chose;

	private int rack(long ... excludes) {
		int num = 1357;

		for (int ix = 0; ix < RETRY; ix++) {
			num = this.nm.next().intValue();
			if (num % 1111 == 0) {
				continue;
			}
			if (Arrays.binarySearch(excludes, num) != -1) {
				continue;
			}
			break;
		}
		return num;
	}

	@Override
	public String next() {
		long hi = rack();
		long mid = rack(hi);
		long lo = rack(hi, mid);
		long result = (hi * 100000000L + mid * 10000 + lo) / 10;
		String code = String.format("%011d", Long.valueOf(result));
		String cd = "0";

		try {
			cd = this.checkDigit.calculate(code);
		} catch (CheckDigitException e) {
			e.printStackTrace();
		}
		this.chose = code + cd;
		return this.chose;
	}

	@Override
	public String current() {
		return this.chose;
	}
}
