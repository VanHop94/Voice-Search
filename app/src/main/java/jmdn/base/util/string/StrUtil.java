/**
 * 
 */

package jmdn.base.util.string;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 22-08-2014
 */
public class StrUtil {
	/**
	 * Tokenizing an input text string using the default delimiter including the
	 * white space, tab character, and the newline character (" \t\r\n").
	 * 
	 * @param string
	 *            the input text string
	 * @return A list of tokens
	 */
	public static List<String> tokenizeString(String string) {
		return StrUtil.tokenizeString(string, " \t\r\n");
	}

	/**
	 * Tokenizing an input text string using an explicit delimiter.
	 * 
	 * @param string
	 *            the input text string
	 * @param delimiter
	 *            the delimiter
	 * @return A list of tokens
	 */
	public static List<String> tokenizeString(String string, String delimiter) {
		List<String> res = new ArrayList<String>();

		StringTokenizer strtok = new StringTokenizer(string, delimiter);
		int len = strtok.countTokens();
		for (int i = 0; i < len; i++) {
			res.add(strtok.nextToken());
		}

		return res;
	}

	/**
	 * Tokenizing an input text string with a delimiter consisting of many
	 * special characters (" \t\r\n.,;:?!~^*-$%#@().…'\"=/")
	 * 
	 * @param string
	 *            the input text string
	 * @return A list of tokens
	 */
	public static List<String> tokenizeStringAll(String string) {
		return StrUtil.tokenizeString(string, " \t\r\n.,;:?!~^*-$%#@().…'\"=/");
	}

	/**
	 * Normalizing an input text string by removing unnecessary characters.
	 * 
	 * @param string
	 *            the input text string
	 * @return The normalized text string
	 */
	public static String normalizeString(String string) {
		String res = "";

		StringTokenizer strtok = new StringTokenizer(string, " \t\r\n");

		int len = strtok.countTokens();
		if (len > 0) {
			res = strtok.nextToken();
		}
		for (int i = 1; i < len; i++) {
			res += " " + strtok.nextToken();
		}

		return res;
	}

	/**
	 * Joining text elements in a list (from index "start" to "end") to form a
	 * text string. The delimiter, by default, is the white space.
	 * 
	 * @param list
	 *            the list of text elements
	 * @param start
	 *            the starting index
	 * @param end
	 *            the ending index
	 * @return The resulting text string
	 */
	public static String join(List<String> list, int start, int end) {
		return join(list, start, end, " ");
	}

	/**
	 * Joining the text elements in a list (from index "start" to "end") to form
	 * a text string. The delimiter must be specified.
	 * 
	 * @param list
	 *            the list of text elements
	 * @param start
	 *            the starting index
	 * @param end
	 *            the ending index
	 * @param delimiter
	 *            the delimiter
	 * @return The resulting text string
	 */
	public static String join(List<String> list, int start, int end, String delimiter) {
		String res = "";

		if (start < 0) {
			start = 0;
		}

		if (end >= list.size()) {
			end = list.size() - 1;
		}

		if (start > end) {
			return res;
		} else {
			res = (String) list.get(start);
			for (int i = start + 1; i <= end; i++) {
				res += delimiter + (String) list.get(i);
			}
			return res;
		}
	}

	/**
	 * Joining all text elements in a list using an explicit delimiter.
	 * 
	 * @param list
	 *            the list of text elements
	 * @param delimiter
	 *            the delimiter
	 * @return The resulting text string
	 */
	public static String join(List<String> list, String delimiter) {
		String res = "";

		if (list.size() > 0) {
			res = list.get(0);
		}

		for (int i = 1; i < list.size(); i++) {
			res += delimiter + list.get(i);
		}

		return res;
	}

	/**
	 * Joining all text elements in a list using the white space as the
	 * delimiter.
	 * 
	 * @param list
	 *            the list of text elements
	 * @return The resulting text string
	 */
	public static String join(List<String> list) {
		return join(list, " ");
	}

	/**
	 * Removing the last character of a text string.
	 * 
	 * @param string
	 *            the input text string
	 * @return The resulting text string after removing the last character
	 */
	public static String removeLastChar(String string) {
		if (string == null || string.length() == 0) {
			return string;
		} else {
			return string.substring(0, string.length() - 1);
		}
	}

	/**
	 * Removing the first character of a text string.
	 * 
	 * @param string
	 *            the input text string
	 * @return The resulting text string after removing the first character
	 */
	public static String removeFirstChar(String string) {
		if (string == null || string.length() == 0) {
			return string;
		} else {
			return string.substring(1, string.length());
		}
	}

	/**
	 * Capitalizing the first character of a text string and lowercasing the
	 * remaining part.
	 * 
	 * @param string
	 *            the input text string
	 * @return The resulting text string
	 */
	public static String toFirstCap(String string) {
		if (string == null || string.length() <= 0) {
			return string;
		}

		String firstChar = string.substring(0, 1);
		String remaining = string.substring(1, string.length());

		return firstChar.toUpperCase() + remaining.toLowerCase();
	}

	/**
	 * Capitalizing the first character of a text string and keeping the
	 * remaining part the same.
	 * 
	 * @param string
	 *            the input text string
	 * @return The resulting text string
	 */
	public static String toFirstCharCap(String string) {
		if (string == null || string.length() <= 0) {
			return string;
		}

		String firstChar = string.substring(0, 1);
		String remaining = string.substring(1, string.length());

		return firstChar.toUpperCase() + remaining;
	}

	/**
	 * Capitalizing the first character of all text tokens/words of a text
	 * string and lowercasing the remaining part.
	 * 
	 * @param string
	 *            the input text string
	 * @return The resulting text string
	 */
	public static String toInitCap(String string) {
		List<String> tokens = StrUtil.tokenizeString(string);

		List<String> normalizedTokens = new ArrayList<String>();
		for (int i = 0; i < tokens.size(); i++) {
			normalizedTokens.add(toFirstCap((String) tokens.get(i)));
		}

		return StrUtil.join(normalizedTokens);
	}
}
