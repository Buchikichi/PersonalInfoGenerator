package to.kit.personal.making;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ファイル内のリストから選択.
 * @param <T> 選択するデータ型
 * @author Hidetaka Sasai
 */
public abstract class ListChooser<T> extends SimpleChooser<T> {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ListChooser.class);

	protected List<String> loadAll(String resourceName) {
		List<String> list;

		try (InputStream in = ListChooser.class.getResourceAsStream(resourceName)) {
			list = IOUtils.readLines(in, Charset.defaultCharset());
		} catch (IOException e) {
			LOG.error(e.getMessage());
			list = new ArrayList<>();
		}
		return list;
	}

	/**
	 * インスタンス生成.
	 * @param resourceName 読み込むリソース名
	 */
	public ListChooser(String resourceName) {
		super(resourceName);
	}
}
