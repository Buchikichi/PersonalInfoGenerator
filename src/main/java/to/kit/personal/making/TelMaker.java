package to.kit.personal.making;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 電話番号を生成.
 * @author Hidetaka Sasai
 */
public final class TelMaker extends SimpleChooser<String> {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ListChooser.class);
	private static final String RESOURCE = "/ziptel.txt";

	private Map<String, String> map;
	private NumberMaker number = new NumberMaker(0, 9999);

	/**
	 * 郵便番号に依存した電話番号を生成.
	 * @param zip7 郵便番号
	 * @return 電話番号
	 */
	public String next(final String zip7) {
		String tel;

		if (1 < Math.random() * 5) {
			String hi;

			if (StringUtils.isNotBlank(zip7)) {
				hi = this.map.get(zip7);
			} else {
				hi = super.next();
			}
			int len = 6 - hi.length();
			String mid = StringUtils.right(String.format("%04d", this.number.next()), len);
			String lo = String.format("%04d", this.number.next());

			tel = hi + '-' + mid + '-' + lo;
		} else {
			String hi;

			if (1 < Math.random() * 4) {
				hi = "090";
			} else {
				int ptn = (int) (Math.random() * 4);
				if (ptn == 0) {
					hi = "080";
				} else if (ptn == 1) {
					hi = "070";
				} else {
					hi = "050";
				}
			}
			String mid = String.format("%04d", this.number.next());
			String lo = String.format("%04d", this.number.next());

			tel = hi + '-' + mid + '-' + lo;
		}
		setChose(tel);
		return tel;
	}

	@Override
	public String next() {
		return next(null);
	}

	@Override
	protected List<String> load(String... resources) {
		List<String> list = new ArrayList<>();

		this.map = new HashMap<>();
		try (InputStream in = ListChooser.class.getResourceAsStream(resources[0])) {
			for (String line : IOUtils.readLines(in, Charset.defaultCharset())) {
				String[] csv = line.split(",");
				String zip = csv[0];
				String tel = '0' + csv[1];

				this.map.put(zip, tel);
				if (!list.contains(tel)) {
					list.add(tel);
				}
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return list;
	}

	/**
	 * インスタンス生成.
	 */
	public TelMaker() {
		super(RESOURCE);
	}
}
