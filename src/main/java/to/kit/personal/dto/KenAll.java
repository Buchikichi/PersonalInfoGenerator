package to.kit.personal.dto;

import org.apache.commons.lang3.StringUtils;

public class KenAll {
	private String x0401;
	private String x0402;
	private String zip5;
	private String zip7;
	/** prefecture. */
	private String prefKana;
	/** municipality. */
	private String municKana;
	private String cityKana;
	private String pref;
	private String munic;
	private String city;
	private boolean hasSomeCity;
	private boolean hasNumber;
	private boolean hasChome;
	// ext
	private final Street street = new Street();
	private String apartment = StringUtils.EMPTY;
	private String room = StringUtils.EMPTY;

	public String getX0401() {
		return this.x0401;
	}
	public void setX0401(String x0401) {
		this.x0401 = x0401;
	}
	public String getX0402() {
		return this.x0402;
	}
	public void setX0402(String x0402) {
		this.x0402 = x0402;
	}
	public String getZip5() {
		return this.zip5;
	}
	public void setZip5(String zip5) {
		this.zip5 = zip5;
	}
	public String getZip7() {
		return this.zip7;
	}
	public void setZip7(String zip7) {
		this.zip7 = zip7;
	}
	public String getPrefKana() {
		return this.prefKana;
	}
	public void setPrefKana(String prefKana) {
		this.prefKana = prefKana;
	}
	public String getMunicKana() {
		return this.municKana;
	}
	public void setMunicKana(String municKana) {
		this.municKana = municKana;
	}
	public String getCityKana() {
		return this.cityKana;
	}
	public void setCityKana(String cityKana) {
		this.cityKana = cityKana;
	}
	public String getPref() {
		return this.pref;
	}
	public void setPref(String pref) {
		this.pref = pref;
	}
	public String getMunic() {
		return this.munic;
	}
	public void setMunic(String munic) {
		this.munic = munic;
	}
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public boolean isHasSomeCity() {
		return this.hasSomeCity;
	}
	public void setHasSomeCity(boolean hasSomeCity) {
		this.hasSomeCity = hasSomeCity;
	}
	public boolean isHasNumber() {
		return this.hasNumber;
	}
	public void setHasNumber(boolean hasNumber) {
		this.hasNumber = hasNumber;
	}
	public boolean isHasChome() {
		return this.hasChome;
	}
	public void setHasChome(boolean hasChome) {
		this.hasChome = hasChome;
	}
	public Street getStreet() {
		return this.street;
	}
	public String getApartment() {
		return this.apartment;
	}
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
	public String getRoom() {
		return this.room;
	}
	public void setRoom(String room) {
		this.room = room;
	}

	/**
	 * 番地・番号.
	 * @author Hidetaka Sasai
	 */
	public class Street {
		private String hi;
		private String lo = "";

		public String getHi() {
			return this.hi;
		}
		public void setHi(String hi) {
			this.hi = hi;
		}
		public String getLo() {
			return this.lo;
		}
		public void setLo(String lo) {
			this.lo = lo;
		}

		@Override
		public String toString() {
			StringBuilder buff = new StringBuilder();
			buff.append(this.hi);
			if (this.lo != null && !this.lo.isEmpty()) {
				buff.append('－');
				buff.append(this.lo);
			}
			return buff.toString();
		}
	}
}
