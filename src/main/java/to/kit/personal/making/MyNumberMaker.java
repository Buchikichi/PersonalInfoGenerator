package to.kit.personal.making;

import java.util.Arrays;

/**
 * マイナンバー(12桁)を生成.
 * @author Hidetaka Sasai
 */
public final class MyNumberMaker implements InfoMaker<Long> {
	private static final int RETRY = 10;
	private final NumberMaker nm = new NumberMaker(1, 9999);
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
		long result = hi * 100000000L + mid * 10000 + lo;

		this.chose = Long.valueOf(result);
		return this.chose;
	}

	@Override
	public Long current() {
		return this.chose;
	}
}
