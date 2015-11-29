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
	private String street;
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
	public String getStreet() {
		return this.street;
	}
	public void setStreet(String street) {
		this.street = street;
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
}
