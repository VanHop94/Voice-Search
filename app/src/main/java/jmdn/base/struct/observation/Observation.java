/**
 * 
 */

package jmdn.base.struct.observation;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import jmdn.base.util.string.StrUtil;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class Observation {
	private List<String> tokens = null;
	private String tag = null;

	/**
	 * Default class constructor.
	 */
	public Observation() {
		this.tokens = new ArrayList<String>();
	}

	/**
	 * Class constructor.
	 * 
	 * @param token
	 *            the first token of the observation
	 * @param tag
	 *            the tag label of the observation
	 */
	public Observation(String token, String tag) {
		this();

		this.tokens.add(token);
		this.tag = tag;
	}

	/**
	 * Class constructor.
	 * 
	 * @param tokens
	 *            a list of tokens of the observation
	 * @param tag
	 *            the tag label of the observation
	 */
	public Observation(List<String> tokens, String tag) {
		this();

		for (int i = 0; i < tokens.size(); i++) {
			this.tokens.add(tokens.get(i));
		}

		this.tag = tag;
	}

	/**
	 * Class constructor.
	 * 
	 * @param tokens
	 *            a list of tokens (and tag) of the observation
	 * @param hasTag
	 *            true if the last element of tokens is the tag, false otherwise
	 */
	public Observation(List<String> tokens, boolean hasTag) {
		this();

		int len = tokens.size();

		if (hasTag) {
			len--;
		}

		for (int i = 0; i < len; i++) {
			this.tokens.add(tokens.get(i));
		}

		if (hasTag) {
			this.tag = tokens.get(len);
		}
	}

	/**
	 * Class constructor.
	 * 
	 * @param tokens
	 *            a list of tokens of the observation (there is no tag)
	 */
	public Observation(List<String> tokens) {
		this(tokens, false);
	}

	/**
	 * Class constructor.
	 * 
	 * @param string
	 *            a text string containing tokens of observation
	 * @param hasTag
	 *            true if the last token of the string is the tag, false
	 *            otherwise
	 */
	public Observation(String string, boolean hasTag) {
		this();

		List<String> tokens = StrUtil.tokenizeString(string);
		int len = tokens.size();

		if (hasTag) {
			len--;
		}

		for (int i = 0; i < len; i++) {
			this.tokens.add(tokens.get(i));
		}

		if (hasTag) {
			this.tag = tokens.get(len);
		}
	}

	/**
	 * Class constructor.
	 * 
	 * @param string
	 *            a text string containing the tokens of the observation (there
	 *            is no tag)
	 */
	public Observation(String string) {
		this(string, false);
	}

	/**
	 * Getting the token at the index "index".
	 * 
	 * @param index
	 *            the index of the token that needs to be fetched
	 * @return the token at the index "index"
	 */
	public String getToken(int index) {
		String res = null;

		if (index >= 0 && index < tokens.size()) {
			res = tokens.get(index);
		}

		return res;
	}

	/**
	 * Adding a token to the list of tokens of the observation.
	 * 
	 * @param token
	 *            the token that needs to be added
	 */
	public void addToken(String token) {
		this.tokens.add(token);
	}

	/**
	 * Getting all tokens of the observation.
	 * 
	 * @return the list of tokens of the observation
	 */
	public List<String> getTokens() {
		return tokens;
	}

	/**
	 * Setting the new list of tokens for the observation.
	 * 
	 * @param tokens
	 *            the new list of tokens of the observation
	 */
	public void setTokens(List<String> tokens) {
		this.tokens.clear();

		for (int i = 0; i < tokens.size(); i++) {
			this.tokens.add(tokens.get(i));
		}
	}

	/**
	 * Getting the tag of the observation.
	 * 
	 * @return the tag of the observation
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * Setting the tag for the observation.
	 * 
	 * @param tag
	 *            the new tag of the observation
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		String res = "";
		if (tokens.size() > 0) {
			res = tokens.get(0);
		}

		for (int i = 1; i < tokens.size(); i++) {
			res += "\t" + tokens.get(i);
		}

		if (tag != null) {
			res += "\t" + tag;
		}

		return res;
	}

	/**
	 * Displaying observation.
	 */
	public void print() {
		System.out.println(toString());
	}

	/**
	 * Saving the observation to file.
	 * 
	 * @param out
	 *            the output stream
	 * @throws IOException
	 *             IO exception
	 */
	public void write(Writer out) throws IOException {
		out.write(toString() + "\n");
	}
}
