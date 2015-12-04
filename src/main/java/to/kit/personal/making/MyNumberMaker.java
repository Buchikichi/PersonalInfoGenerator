package to.kit.personal.making;

import java.util.Arrays;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.checkdigit.CheckDigit;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;

/**
 * マイナンバー(12桁)を生成.
 * @author Hidetaka Sasai
 */
public final class MyNumberMaker implements InfoMaker<Long> {
	private static final int RETRY = 10;
	private final NumberMaker nm = new NumberMaker(1, 9999);
	private final CheckDigit ean13 = EAN13CheckDigit.EAN13_CHECK_DIGIT;
	private Long chose;

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
	public Long next(String... conditions) {
		long hi = rack();
		long mid = rack(hi);
		long lo = rack(hi, mid);
		long result = (hi * 100000000L + mid * 10000 + lo) / 10;
		int cd = 0;

		try {
			cd = NumberUtils.toInt(this.ean13.calculate(String.valueOf(result)));
		} catch (CheckDigitException e) {
			e.printStackTrace();
		}
		this.chose = Long.valueOf(result * 10L + cd);
		return this.chose;
	}

	@Override
	public Long current() {
		return this.chose;
	}
}
