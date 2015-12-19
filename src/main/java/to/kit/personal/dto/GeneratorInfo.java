package to.kit.personal.dto;

/**
 * ジェネレーター情報.
 * @author Hidetaka Sasai
 *
 */
public final class GeneratorInfo {
	private String id;
	private String clazz;
	private String args;

	/**
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the clazz
	 */
	public String getClazz() {
		return this.clazz;
	}
	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	/**
	 * @return the args
	 */
	public String getArgs() {
		return this.args;
	}
	/**
	 * @param args the args to set
	 */
	public void setArgs(String args) {
		this.args = args;
	}
}
