package to.kit.personal.making;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import to.kit.personal.dto.KenAll;
import to.kit.personal.dto.KenAll.Street;
import to.kit.personal.util.AddressUtils;
import to.kit.personal.util.NameUtils;

/**
 * 住所を生成.
 * @author Hidetaka Sasai
 */
public final class AddressMaker implements InfoMaker<KenAll> {
	private static final String CHOME = "丁目";
	private final List<KenAll> zipList;
	private final int max;

	private KenAll chose;
	private NumberMaker chome = new NumberMaker(1, 9);
	private NumberMaker hi = new NumberMaker(1, 15);
	private NumberMaker lo = new NumberMaker(1, 30);
	private ApartmentChooser apart = new ApartmentChooser();

	@Override
	public KenAll next() {
		int ix = (int) (Math.random() * this.max);
		KenAll rec = this.zipList.get(ix);
		String city = rec.getCity();
		Street street = rec.getStreet();

		street.setHi(NameUtils.toFull(String.valueOf(this.hi.next())));
		if (rec.isHasChome() && !city.contains(CHOME)) {
			String num = String.valueOf(this.chome.next());
			String chomeStr = NameUtils.toKansuuji(num) + CHOME;

			city += chomeStr;
			rec.setCity(city);
		}
		if (0 < (int) (Math.random() * 5)) {
			street.setLo(NameUtils.toFull(String.valueOf(this.lo.next())));
		}
		if ((int) (Math.random() * 8) == 0) {
			String apartment = this.apart.next(city, rec.getCityKana());
			int floor = this.chome.next().intValue() * 100;
			int roomNum = floor + this.lo.next().intValue();
			String room = NameUtils.toFull(String.valueOf(roomNum));

			rec.setApartment(apartment);
			rec.setRoom(room);
		}
		this.chose = rec;
		return this.chose;
	}

	@Override
	public KenAll current() {
		return this.chose;
	}

	/**
	 * インスタンス生成.
	 * @param codes 都道府県コード
	 */
	public AddressMaker(final String ... codes) {
		List<String> list = Arrays.asList(codes);
		Predicate<KenAll> predicator;

		if (list.isEmpty()) {
			predicator = null;
		} else {
			predicator = rec -> list.contains(rec.getX0401());
		}
		this.zipList = AddressUtils.load(predicator);
		this.max = this.zipList.size();
	}

	/**
	 * インスタンス生成.
	 */
	public AddressMaker() {
		this(new String[] {});
	}
}
