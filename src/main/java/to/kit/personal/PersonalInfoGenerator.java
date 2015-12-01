package to.kit.personal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import to.kit.personal.dto.KanjiName;
import to.kit.personal.dto.KenAll;
import to.kit.personal.making.AddressMaker;
import to.kit.personal.making.KanjiKanaMaker;
import to.kit.personal.making.MyNumberMaker;
import to.kit.personal.making.NumberMaker;
import to.kit.personal.making.StringChooser;
import to.kit.personal.making.TelMaker;
import to.kit.personal.util.NameUtils;

/**
 * The generator of personal information.
 * @author Hidetaka Sasai
 */
public class PersonalInfoGenerator {
	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(PersonalInfoGenerator.class);
	private static final String SEI = "/familyName.txt";
	private static final String MEI = "/firstName.txt";
	private static final String[] HEADER = { "A", "B", "C", "D", "E", };
	private static final String SEQ2 = "00";
	private static final int BRANCH = 1;
	private static final String CONTRACT_INDEX = "00";
	private static final String CONTRACT_TYPE = "5";
	private static final List<String> TARGET_PREF = Arrays.asList(new String[] { "31", "32", "33", "34", "35", });
	private static final int REQ = 100000;

	private final NumberMaker custNum = new NumberMaker(100000000, 999999999);
	private final MyNumberMaker myNum = new MyNumberMaker();
	private final StringChooser appType = new StringChooser("1A", "1B");
	private final StringChooser bizType = new StringChooser("1", "2", "3", "4", "5", "6", "7", "9");
	private final KanjiKanaMaker fnm = new KanjiKanaMaker(SEI, s -> NameUtils.toHalfKana(s));
	private final KanjiKanaMaker gnm = new KanjiKanaMaker(MEI, s -> NameUtils.toHalfKana(s));
	private final AddressMaker adr = new AddressMaker(rec -> TARGET_PREF.contains(rec.getX0401()));
	private final TelMaker telMaker = new TelMaker();
	private final StringChooser pcd = new StringChooser("00", "10", "20", "30", "80");
	private final NumberMaker months = new NumberMaker(6, 360);
	private final NumberMaker avg = new NumberMaker(300, 1100);

	private boolean dice(int max) {
		return 0 == (int) (Math.random() * max);
	}

	private List<String> choose() {
		List<String> list = new ArrayList<>();
		String myNumber = String.format("%012d", this.myNum.next());
		KanjiName sei = this.fnm.next();
		KanjiName mei = this.gnm.next();
		String nameKanji = sei.getKanji() + '　' + mei.getKanji();
		String nameKana = sei.getKana() + ' ' + mei.getKana();
		KenAll ken = this.adr.next();
		String zip7 = ken.getZip7();
		String[] tel = this.telMaker.next(zip7).split("-");

		list.add(this.custNum.next().toString());
		list.add(SEQ2);
		list.add(myNumber.substring(0, 4));
		list.add(myNumber.substring(4));
		list.add(String.valueOf(BRANCH));
		list.add(CONTRACT_INDEX);
		list.add(CONTRACT_TYPE);
		list.add(this.appType.next());
		list.add(this.bizType.next());

		list.add(nameKanji);
		list.add(nameKana);
		list.add(tel[0]);
		list.add(tel[1]);
		list.add(tel[2]);
		list.add(zip7);
		list.add(StringUtils.EMPTY);
		list.add(StringUtils.EMPTY);
		list.add(ken.getPref() + ken.getMunic());
		list.add(ken.getCity());
		list.add(ken.getStreet());
		list.add(ken.getApartment());
		list.add(ken.getRoom());
		list.add(StringUtils.EMPTY);

		list.add(StringUtils.EMPTY);
		list.add(StringUtils.EMPTY);
		if (dice(5)) {
			mei = this.gnm.next();
			if (dice(3)) {
				sei = this.fnm.next();
			}
			nameKanji = sei.getKanji() + '　' + mei.getKanji();
			nameKana = sei.getKana() + ' ' + mei.getKana();
		}
		list.add(nameKanji);
		list.add(nameKana);
		list.add(zip7);
		list.add(StringUtils.EMPTY);
		list.add(StringUtils.EMPTY);
		list.add(ken.getPref() + ken.getMunic());
		list.add(ken.getCity());
		list.add(ken.getStreet());
		list.add(ken.getApartment());
		list.add(ken.getRoom());
		list.add(StringUtils.EMPTY);

		list.add(this.pcd.next());
		list.add("1");
		list.add("1");
		list.add(this.months.next().toString());
		double avguse = this.avg.next().doubleValue() * 1.13;
		int amount = (int) (avguse * 13);
		list.add(String.valueOf(avguse));
		list.add(String.valueOf(amount));
		list.add(String.valueOf((int) (amount * 0.93)));
		list.add(String.valueOf((int) (amount * 0.96)));
		return list;
	}

	/**
	 * 処理を実行.
	 * @throws IOException 入出力例外
	 * @throws ArchiveException アーカイバ例外
	 */
	public void execute() throws IOException, ArchiveException {
		CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(HEADER);
		File file = new File("personalInfo.csv");

		LOG.debug("write");
		try (FileOutputStream stream = new FileOutputStream(file);
				Writer out = new OutputStreamWriter(stream, "MS932");
				CSVPrinter printer = csvFormat.print(out)) {
			for (int ix = 0; ix < REQ; ix++) {
				List<String> line = choose();

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
	public static void main(String[] args) throws Exception {
		PersonalInfoGenerator app = new PersonalInfoGenerator();

		app.execute();
	}
}
