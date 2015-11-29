package to.kit.personal.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import to.kit.name.FamilyNameConverterMain;
import to.kit.name.MeargeMain;
import to.kit.personal.dto.KenAll;

public final class AddressUtils {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(AddressUtils.class);
	private static final String ZIP = "/ken_all.zip";

	private AddressUtils() {
		// nop
	}

	public static List<String> loadAll() {
		List<String> list;

		try (InputStream in = FamilyNameConverterMain.class.getResourceAsStream(ZIP);
				ArchiveInputStream ai = new ArchiveStreamFactory().createArchiveInputStream(in)) {
			ArchiveEntry entry = ai.getNextEntry();

			list = IOUtils.readLines(ai, Charset.forName("Shift_JIS"));
			LOG.debug("Entry[{}]", entry.getName());
			LOG.debug("zipList:" + list.size());
		} catch (IOException | ArchiveException e) {
			LOG.debug(e.getMessage());
			list = new ArrayList<>();
		}
		return list;
	}

	private static KenAll refill(final String[] csv) {
		KenAll rec = new KenAll();
		String x402 = csv[0];
		String zip5 = StringUtils.strip(csv[1], "\"");
		String zip7 = StringUtils.strip(csv[2], "\"");
		String prefKana = StringUtils.strip(csv[3], "\"");
		String municKana = StringUtils.strip(csv[4], "\"");
		String cityKana = StringUtils.strip(csv[5], "\"");
		String pref = StringUtils.strip(csv[6], "\"");
		String munic = StringUtils.strip(csv[7], "\"");
		String city = StringUtils.strip(csv[8], "\"");

		rec.setX0401(x402.substring(0, 2));
		rec.setX0402(x402);
		rec.setZip5(zip5);
		rec.setZip7(zip7);
		rec.setPrefKana(prefKana);
		rec.setMunicKana(municKana);
		rec.setCityKana(cityKana);
		rec.setPref(pref);
		rec.setMunic(munic);
		rec.setCity(city);
		rec.setHasSomeCity("1".equals(csv[9]));
		rec.setHasNumber("1".equals(csv[10]));
		rec.setHasChome("1".equals(csv[11]));
		return rec;
	}

	/**
	 * 住所情報を読み込む.
	 * @param predicator Predicate
	 * @return 住所リスト
	 */
	public static List<KenAll> load(final Predicate<KenAll> predicator) {
		List<KenAll> resultList = new ArrayList<>();

		for (String line : loadAll()) {
			String[] csv = line.split(",");
			String cityKana = StringUtils.strip(csv[5], "\"");
			String city = StringUtils.strip(csv[8], "\"");

			if (cityKana.startsWith("ｲｶﾆ") || cityKana.contains("(") || cityKana.contains(")")
					 || city.contains("（") || city.contains("）")) {
				continue;
			}
			KenAll rec = refill(csv);
			if (predicator != null && !predicator.test(rec)) {
				continue;
			}
			resultList.add(rec);
		}
		return resultList;
	}
}
