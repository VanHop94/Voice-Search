/**
 * 
 */

package jmdn.method.mobile.conjunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jmdn.base.util.string.StrUtil;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 26-08-2014
 */
public class InputText {
	private String text = null;
	private Map<String, Short> words2Counts = null;

	/**
	 * Default class constructor.
	 */
	public InputText() {
		text = null;
		words2Counts = new HashMap<String, Short>();
	}

	/**
	 * Class constructor.
	 * 
	 * @param text
	 *            the input text
	 */
	public InputText(String text) {
		this();

		parseText(text);
	}

	/**
	 * Parsing input text.
	 * 
	 * @param text
	 *            the input text to be parsed
	 */
	public final void parseText(String text) {
		words2Counts.clear();

		List<String> words = StrUtil.tokenizeString(text.toLowerCase(), " \t\r\n");
		this.text = StrUtil.join(words);
		int len = words.size();

		for (int i = 0; i < len; i++) {
			String word = words.get(i);

			Short currentCount = words2Counts.get(word);
			if (currentCount == null) {
				words2Counts.put(word, (short)1);
			} else {
				words2Counts.put(word, (short)(currentCount + 1));
			}
		}
	}

	/**
	 * Getting the number of words in the input text.
	 * 
	 * @return The number of words in the input text
	 */
	public int size() {
		return words2Counts.size();
	}

	/**
	 * Setting the new text.
	 * 
	 * @param text
	 *            the new input text
	 */
	public void setText(String text) {
		parseText(text);
	}

	/**
	 * Getting the input text.
	 * 
	 * @return The input text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Getting the list of unique words in the input text.
	 * 
	 * @return The list of all unique words
	 */
	public List<String> getUniqueWords() {
		List<String> uniqueWords = new ArrayList<String>();

		for (Map.Entry<String, Short> entry : words2Counts.entrySet()) {
			uniqueWords.add(entry.getKey());
		}

		return uniqueWords;
	}

	/**
	 * Getting the count (frequency) of a given words in the input text.
	 * 
	 * @param word
	 *            the word to be counted
	 * @return The count/frequency of the given words
	 */
	public int getCountOfWord(String word) {
		Short wordCount = words2Counts.get(word);
		if (wordCount != null) {
			return wordCount.intValue();
		} else {
			return 0;
		}
	}

	/**
	 * Displaying the input text.
	 */
	public void print() {
		System.out.println(text);
		System.out.println(words2Counts.toString());
	}
}
