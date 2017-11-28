/**
 * 
 */

package jmdn.method.mobile.conjunction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jmdn.base.util.string.StrUtil;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 12-12-2015
 */
public class Conjunction {
	private String conjDomain = null;
	private String conjType = null;
	private byte conjPriority = 1;
	private boolean starting = false;
	private boolean ending = false;
	private List<String> conjChunks = null;
	private int[] comChunkIdxes = null;
	private byte numUniqueTokens = 0;

	/**
	 * Default class constructor.
	 */
	public Conjunction() {
		conjDomain = "";
		conjType = "";
		conjPriority = 1;
		starting = false;
		ending = false;
		conjChunks = new ArrayList<String>();
		comChunkIdxes = null;
		numUniqueTokens = 0;
	}

	/**
	 * Class constructor.
	 * 
	 * @param conjString
	 *            the conjunction string
	 */
	public Conjunction(String conjString) {
		this();

		parseConjString(conjString);
	}

	/**
	 * Class constructor.
	 * 
	 * @param conjDomain
	 *            the domain of the conjunction
	 * @param conjType
	 *            the type of the conjunction
	 * @param conjString
	 *            the conjunction string
	 */
	public Conjunction(String conjDomain, String conjType, String conjString) {
		this();

		this.conjDomain = conjDomain;
		this.conjType = conjType;
		parseConjString(conjString);
	}

	/**
	 * Class constructor.
	 * 
	 * @param conjDomain
	 *            the domain of the conjunction
	 * @param conjType
	 *            the type of the conjunction
	 * @param conjPriority
	 *            the priority of the conjunction
	 * @param conjString
	 *            the conjunction string
	 */
	public Conjunction(String conjDomain, String conjType, int conjPriority, String conjString) {
		this();

		this.conjDomain = conjDomain;
		this.conjType = conjType;
		this.conjPriority = (byte) conjPriority;
		parseConjString(conjString);
	}

	/**
	 * Parsing the conjunction string.
	 * 
	 * @param conjString
	 *            the conjunction string to be parsed
	 */
	public final void parseConjString(String conjString) {
		conjChunks.clear();
		Set<String> uniqueTokens = new HashSet<String>();

		List<Integer> tempComChunkIdxes = new ArrayList<Integer>();

		List<String> chunks = StrUtil.tokenizeString(conjString.toLowerCase(), "{}[]() \t\r\n");
		for (int i = 0; i < chunks.size(); i++) {
			String chunk = chunks.get(i);

			if (chunk.startsWith("^")) {
				starting = true;
				if (chunk.length() <= 1) {
					continue;
				} else {
					chunk = chunk.substring(1);
				}
			}

			if (chunk.endsWith("$")) {
				ending = true;
				if (chunk.length() <= 1) {
					continue;
				} else {
					chunk = chunk.substring(0, chunk.length() - 1);
				}
			}

			List<String> tokens = StrUtil.tokenizeString(chunk, "&");
			String tempChunk = StrUtil.join(tokens);

			conjChunks.add(tempChunk);

			if (tokens.size() > 1) {
				tempComChunkIdxes.add(i);
			}

			for (int j = 0; j < tokens.size(); j++) {
				uniqueTokens.add(tokens.get(j));
			}
		}

		int numComChunks = tempComChunkIdxes.size();
		if (numComChunks > 0) {
			comChunkIdxes = new int[numComChunks];
			for (int i = 0; i < numComChunks; i++) {
				comChunkIdxes[i] = tempComChunkIdxes.get(i).intValue();
			}
		}

		numUniqueTokens = (byte) uniqueTokens.size();
	}

	/**
	 * Getting the number of chunks in the conjunction.
	 * 
	 * @return The number of chunks of the conjunction
	 */
	public int getNumConjChunks() {
		return conjChunks.size();
	}

	/**
	 * Getting the number of unique words/tokens in the conjunction.
	 * 
	 * @return The number of unique words/tokens in the conjunction string
	 */
	public int getNumUniqueTokens() {
		return numUniqueTokens;
	}

	/**
	 * Setting a new conjunction string for the conjunction.
	 * 
	 * @param conjString
	 *            the new conjunction string
	 */
	public void setConjString(String conjString) {
		parseConjString(conjString);
	}

	/**
	 * Getting the conjunction string.
	 * 
	 * @return The conjunction string
	 */
	public String getConjString() {
		List<String> orgConjChunks = new ArrayList<String>();

		for (int i = 0; i < conjChunks.size(); i++) {
			orgConjChunks.add(conjChunks.get(i).replace(" ", "&"));
		}

		return "{" + (starting ? "^" : "") + StrUtil.join(orgConjChunks) + (ending ? "$" : "") + "}";
	}

	/**
	 * Setting the conjunction domain.
	 * 
	 * @param conjDomain
	 *            the domain of the conjunction
	 */
	public void setConjDomain(String conjDomain) {
		this.conjDomain = conjDomain;
	}

	/**
	 * Getting the conjunction domain.
	 * 
	 * @return The domain of the conjunction
	 */
	public String getConjDomain() {
		return conjDomain;
	}

	/**
	 * Setting the conjunction type.
	 * 
	 * @param conjType
	 *            the type of the conjunction
	 */
	public void setConjType(String conjType) {
		this.conjType = conjType;
	}

	/**
	 * Getting the conjunction type.
	 * 
	 * @return The type of the conjunction
	 */
	public String getConjType() {
		return conjType;
	}

	/**
	 * Setting the conjunction priority.
	 * 
	 * @param conjPriority
	 *            the priority of the conjunction
	 */
	public void setConjPriority(int conjPriority) {
		this.conjPriority = (byte) conjPriority;
	}

	/**
	 * Getting the conjunction priority.
	 * 
	 * @return The priority of the conjunction
	 */
	public int getConjPriority() {
		return (int) conjPriority;
	}

	/**
	 * Indicating whether the conjunction must start at the beginning of the
	 * text.
	 * 
	 * @return True if the conjunction must start at the beginning of the text
	 */
	public boolean isStarting() {
		return starting;
	}

	/**
	 * Indicating whether the conjunction must stop at the end of the text.
	 * 
	 * @return True if the conjunction must stop at the end of the text
	 */
	public boolean isEnding() {
		return ending;
	}

	/**
	 * Getting the chunks of the conjunction.
	 * 
	 * @return A list of chunks of the conjunction
	 */
	public List<String> getConjChunks() {
		return conjChunks;
	}

	/**
	 * Getting the indexes of the complex chunks.
	 * 
	 * @return The list of indexes of complex chunks
	 */
	public int[] getComChunkIdxes() {
		return comChunkIdxes;
	}

	/**
	 * Displaying the conjunction.
	 */
	public void print() {
		System.out.println(conjDomain);
		System.out.println(conjType);
		System.out.println(conjPriority);
		System.out.println(getConjString());
	}
}
