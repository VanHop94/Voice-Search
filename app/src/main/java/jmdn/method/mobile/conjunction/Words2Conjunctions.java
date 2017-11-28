/**
 * 
 */

package jmdn.method.mobile.conjunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jmdn.base.util.string.StrUtil;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 26-08-2014
 */
public class Words2Conjunctions {
	private Map<String, Map<Short, Byte>> words2ConjsCounts;

	/**
	 * Default class constructor.
	 */
	public Words2Conjunctions() {
		words2ConjsCounts = new HashMap<String, Map<Short, Byte>>();
	}

	/**
	 * Building the words-2-conjunctions map.
	 * 
	 * @param conjunctionCollection
	 *            the conjunction collection for building the map
	 */
	public void buildMap(ConjunctionCollection conjunctionCollection) {
		System.out.println("Building Words2Conjunctions map ...");

		if (conjunctionCollection == null) {
			System.out.println("No conjunction collection is provided!");
			return;
		}

		int conjCount = conjunctionCollection.size();
		for (int i = 0; i < conjCount; i++) {
			Conjunction conj = conjunctionCollection.getConjunctionAt(i);
			addConjunctionToMap((short)i, conj);
		}

		System.out.println("Building Words2Conjunctions map completed.");
	}

	/**
	 * Adding a conjunction to the map.
	 * 
	 * @param conjIndex
	 *            the index of the conjunction to be added in the conjunction
	 *            collection list
	 * @param conjunction
	 *            the conjunction to be added
	 */
	public void addConjunctionToMap(Short conjIndex, Conjunction conjunction) {
		List<String> conjChunks = conjunction.getConjChunks();

		for (int i = 0; i < conjChunks.size(); i++) {
			List<String> words = StrUtil.tokenizeString(conjChunks.get(i));
			for (int j = 0; j < words.size(); j++) {
				addWordToMap(conjIndex, words.get(j));
			}
		}
	}

	/**
	 * Adding a word to the map.
	 * 
	 * @param conjIndex
	 *            the index of the conjunction containing the input word
	 * @param word
	 *            the word to be added
	 */
	public void addWordToMap(Short conjIndex, String word) {
		Map<Short, Byte> conjIdxes2Counts = words2ConjsCounts.get(word);

		if (conjIdxes2Counts == null) {
			conjIdxes2Counts = new HashMap<Short, Byte>();
			conjIdxes2Counts.put(conjIndex, (byte)1);
			words2ConjsCounts.put(word, conjIdxes2Counts);
		} else {
			Byte currentCount = conjIdxes2Counts.get(conjIndex);
			if (currentCount == null) {
				conjIdxes2Counts.put(conjIndex, (byte)1);
			} else {
				conjIdxes2Counts.put(conjIndex, (byte)(currentCount.byteValue() + 1));
			}
		}
	}

	/**
	 * Getting all the conjunctions relevant to or active for a given word.
	 * 
	 * @param word
	 *            the given word
	 * @return The list of conjunctions relevant to the word
	 */
	public Map<Short, Byte> getConjunctionIdxes2CountsByWord(String word) {
		Map<Short, Byte> conjIdxes2Counts = words2ConjsCounts.get(word);

		if (conjIdxes2Counts == null) {
			conjIdxes2Counts = new HashMap<Short, Byte>();
		}

		return conjIdxes2Counts;
	}

	/**
	 * Displaying the map.
	 */
	public void print() {
		for (Map.Entry<String, Map<Short, Byte>> entry : words2ConjsCounts.entrySet()) {
			System.out.println(entry.getKey() + ":");
			Map<Short, Byte> conjs2Counts = entry.getValue();
			for (Map.Entry<Short, Byte> entry1 : conjs2Counts.entrySet()) {
				System.out.println("\tConj: " + entry1.getKey() + "\tCount: " + entry1.getValue());
			}
		}
	}
}
