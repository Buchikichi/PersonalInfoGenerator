package to.kit.personal.making;

import java.util.List;
import java.util.function.Predicate;

import to.kit.personal.dto.KenAll;
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
	public KenAll next(String... conditions) {
		int ix = (int) (Math.random() * this.max);
		KenAll rec = this.zipList.get(ix);
		String city = rec.getCity();
		String street = String.valueOf(this.hi.next());

		if (rec.isHasChome() && !city.contains(CHOME)) {
			String num = String.valueOf(this.chome.next());
			String chomeStr = NameUtils.toKansuuji(num) + CHOME;

			city += chomeStr;
			rec.setCity(city);
		}
		if (0 < (int) (Math.random() * 5)) {
			street += '-';
			street += String.valueOf(this.lo.next());
		}
		street = NameUtils.toFull(street);
		rec.setStreet(street);
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
	 * @param predicator Predicate
	 */
	public AddressMaker(final Predicate<KenAll> predicator) {
		this.zipList = AddressUtils.load(predicator);
		this.max = this.zipList.size();
	}
}
