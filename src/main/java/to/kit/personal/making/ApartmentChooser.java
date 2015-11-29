package to.kit.personal.making;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import to.kit.personal.dto.KanjiName;
import to.kit.personal.util.NameUtils;

/**
 * アパート・マンションを生成.
 * @author Hidetaka Sasai
 */
public final class ApartmentChooser extends ListChooser<String>{
	private static final String APART = "/apartment.txt";
	private static final String[] KU_CHO = { "区", "町" };
	private static final String SEI = "/familyName.txt";

	private final KanjiKanaMaker fnm = new KanjiKanaMaker(SEI);

	@Override
	protected List<String> load(String... resources) {
		List<String> list = new ArrayList<>();

		for (String line : loadAll(resources[0])) {
			String[] csv = line.split(",");

			list.add(csv[0]);
		}
		return list;
	}

	private String chooseName(String... candidates) {
		List<String> list = new ArrayList<>();
		KanjiName kanjiName = this.fnm.next();
		String name;

		list.addAll(Arrays.asList(candidates));
		list.add(kanjiName.getKanji());
		list.add(kanjiName.getKana());
		if (0 < rand(3)) {
			name = candidates[0];
		} else {
			StringChooser sc = new StringChooser(list.toArray(new String[list.size()]));

			name = sc.next();
			name = NameUtils.toHiragana(name);
		}
		if (name.contains("丁目")) {
			return "";
		}
		for (String ku : KU_CHO) {
			if (name.contains(ku) && !name.endsWith(ku)) {
				int ix = name.indexOf(ku);

				name = name.substring(ix + 1);
			}
		}
		return name;
	}

	@Override
	public String next(String... conditions) {
		String apartment = super.next();

		if (apartment.contains("+")) {
			String name = chooseName(conditions);

			apartment = apartment.replace("+", name);
		} else if (apartment.contains("*")) {
			String name = "";

			if (0 < rand(3)) {
				name = chooseName(conditions);
			}
			apartment = apartment.replace("*", name);
		}
		setChose(apartment);
		return apartment;
	}

	/**
	 * インスタンス生成.
	 */
	public ApartmentChooser() {
		super(APART);
	}
}
