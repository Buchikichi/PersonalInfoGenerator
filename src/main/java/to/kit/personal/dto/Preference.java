package to.kit.personal.dto;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 出力設定.
 * @author Hidetaka Sasai
 */
public final class Preference {
	private String basePackage;
	private String charset = Charset.defaultCharset().toString();
	private String[] header;
	private List<GeneratorInfo> generators = new ArrayList<>();
	private List<OutputInfo> outputs = new ArrayList<>();
	private int requests;

	/**
	 * @return the basePackage
	 */
	public String getBasePackage() {
		return this.basePackage;
	}
	/**
	 * @param basePackage the basePackage to set
	 */
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	/**
	 * @return the charset
	 */
	public String getCharset() {
		return this.charset;
	}
	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
	/**
	 * @return the header
	 */
	public String[] getHeader() {
		return this.header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(String[] header) {
		this.header = header;
	}
	/**
	 * @return the generators
	 */
	public List<GeneratorInfo> getGenerators() {
		return this.generators;
	}
	/**
	 * @param generators the generators to set
	 */
	public void setGenerators(List<GeneratorInfo> generators) {
		this.generators = generators;
	}
	/**
	 * @return the outputs
	 */
	public List<OutputInfo> getOutputs() {
		return this.outputs;
	}
	/**
	 * @param outputs the outputs to set
	 */
	public void setOutputs(List<OutputInfo> outputs) {
		this.outputs = outputs;
	}
	/**
	 * @return the requests
	 */
	public int getRequests() {
		return this.requests;
	}
	/**
	 * @param requests the requests to set
	 */
	public void setRequests(int requests) {
		this.requests = requests;
	}
}
