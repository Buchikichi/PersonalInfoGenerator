package to.kit.personal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.arnx.jsonic.JSON;
import ognl.Ognl;
import ognl.OgnlException;
import to.kit.personal.dto.GeneratorInfo;
import to.kit.personal.dto.OutputInfo;
import to.kit.personal.dto.Preference;
import to.kit.personal.making.InfoMaker;

/**
 * The generator of personal information.
 * @author Hidetaka Sasai
 */
public class PersonalInfoGenerator {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(PersonalInfoGenerator.class);
	private static final Format DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");

	private List<String> choose(Preference pref, Map<String, InfoMaker<?>> generatorMap) {
		List<String> list = new ArrayList<>();
		Map<String, Object> valueMap = new HashMap<>();

		valueMap.putAll(generatorMap);
		for (OutputInfo outputInfo : pref.getOutputs()) {
			if (outputInfo == null) {
				continue;
			}
			String out = outputInfo.getOut();
			String var = outputInfo.getVar();
			String value = outputInfo.getValue();

			try {
				if (StringUtils.isNotBlank(out)) {
					Object o = Ognl.getValue(out, valueMap);

					list.add(String.valueOf(o));
				} else if (StringUtils.isNotBlank(var) && StringUtils.isNotBlank(value)) {
					Object o = Ognl.getValue(value, valueMap);

					valueMap.put(var, o);
				}
			} catch (OgnlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	private Map<String, InfoMaker<?>> getGeneratorMap(Preference pref) {
		Map<String, InfoMaker<?>> map = new HashMap<>();
		String basePackage = pref.getBasePackage();

		for (GeneratorInfo generator : pref.getGenerators()) {
			if (generator == null) {
				continue;
			}
			String className = basePackage + ClassUtils.PACKAGE_SEPARATOR + generator.getClazz();
			String args = StringUtils.defaultString(generator.getArgs());
			String expression = "new " + className + "(" + args + ")";

			try {
				InfoMaker<?> o = (InfoMaker<?>) Ognl.getValue(expression, null);

				map.put(generator.getId(), o);
			} catch (OgnlException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	private Preference loadPreference(File file) throws IOException {
		Preference preference;
		try (InputStream in = new FileInputStream(file)) {
			preference = JSON.decode(in, Preference.class);
		}
		return preference;
	}

	/**
	 * 処理を実行.
	 * @param file 設定ファイル
	 * @throws IOException 入出力例外
	 * @throws ArchiveException アーカイバ例外
	 */
	public void execute(File file) throws IOException, ArchiveException {
		Preference pref = loadPreference(file);
		Map<String, InfoMaker<?>> generatorMap = getGeneratorMap(pref);
		CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(pref.getHeader());
		Date now = new Date();
		String yyyymmdd = DATE_FORMATTER.format(now);
		File csv = new File(yyyymmdd + "_personalInfo.csv");

		LOG.debug("write");
		try (FileOutputStream stream = new FileOutputStream(csv);
				Writer out = new OutputStreamWriter(stream, pref.getCharset());
				CSVPrinter printer = csvFormat.print(out)) {
			for (int ix = 0; ix < pref.getRequests(); ix++) {
				List<String> line = choose(pref, generatorMap);

				printer.printRecord(line);
			}
		}
		LOG.debug("done.");
	}

	/**
	 * 処理メイン.
	 * @param args コマンドライン引数
	 * @throws Exception 例外
	 */
	public static void main(final String[] args) throws Exception {
		File preference = null;

		if (0 < args.length) {
			File file = new File(args[1]);

			if (file.exists()) {
				preference = file;
			}
		}
		if (preference == null) {
			preference = new File("preference.json");
		}
		PersonalInfoGenerator app = new PersonalInfoGenerator();

		app.execute(preference);
	}
}
