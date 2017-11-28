/**
 * 
 */

package jmdn.method.mobile.conjunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 26-08-2014
 */
public class ConjunctionMatcher {
	private ConjunctionCollection conjunctionCollection = null;
	private Words2Conjunctions words2Conjunctions = null;

	/**
	 * Default class constructor.
	 */
	public ConjunctionMatcher() {
		conjunctionCollection = new ConjunctionCollection();
		words2Conjunctions = new Words2Conjunctions();
	}

	/**
	 * Adding a conjunction to the conjunction collection.
	 * 
	 * @param conjString
	 *            the conjunction string of the conjunction to be added
	 */
	public void addConjunction(String conjString) {
		conjunctionCollection.addConjunction(conjString);

		int idx = conjunctionCollection.size() - 1;
		Conjunction conj = conjunctionCollection.getConjunctionAt(idx);
		words2Conjunctions.addConjunctionToMap((short) idx, conj);
	}

	/**
	 * Adding a conjunction to the conjunction collection.
	 * 
	 * @param conjunction
	 *            the conjunction to be added
	 */
	public void addConjunction(Conjunction conjunction) {
		conjunctionCollection.addConjunction(conjunction);

		int idx = conjunctionCollection.size() - 1;
		words2Conjunctions.addConjunctionToMap((short) idx, conjunction);
	}

	/**
	 * Getting the size of the conjunction collection.
	 * 
	 * @return The number of conjunctions in the collection
	 */
	public int size() {
		return conjunctionCollection.size();
	}

	/**
	 * Getting the conjunction at a given index from the conjunction collection.
	 * 
	 * @param index
	 *            the index at which the conjunction is fetched
	 * @return The fetched conjunction
	 */
	public Conjunction getConjunctionAt(int index) {
		return conjunctionCollection.getConjunctionAt(index);
	}

	/**
	 * Getting all conjunctions in the collection.
	 * 
	 * @return The list of all conjunctions
	 */
	public List<Conjunction> getAllConjunctions() {
		return conjunctionCollection.getAllConjunctions();
	}

	/**
	 * Finding relevant (i.e., active) conjunctions in the input text object
	 * (constructed from an input text string). A conjunction is active in the
	 * input text if the text contains all chunks of that conjunction.
	 * 
	 * @param inputText
	 *            the input text object
	 * @param conjDomain
	 *            the conjunction domain of interest. Set conjDomain = null for
	 *            matching all domains
	 * @return The list of all relevant (active) conjunctions
	 */
	protected List<Conjunction> getRelevantConjunctions(InputText inputText, String conjDomain) {
		List<Conjunction> relConjs = new ArrayList<Conjunction>();

		if (inputText.size() <= 0) {
			return relConjs;
		}

		Map<Short, Byte> conj2Count = new HashMap<Short, Byte>();

		List<String> uniqueWords = inputText.getUniqueWords();

		for (int i = 0; i < uniqueWords.size(); i++) {
			String word = uniqueWords.get(i);
			Map<Short, Byte> conjIdxes2Counts = words2Conjunctions.getConjunctionIdxes2CountsByWord(word);
			for (Map.Entry<Short, Byte> entry : conjIdxes2Counts.entrySet()) {
				if (inputText.getCountOfWord(word) == entry.getValue()) {
					Short conjIdx = entry.getKey();
					Byte count = conj2Count.get(conjIdx);
					if (count == null) {
						conj2Count.put(conjIdx, (byte) 1);
					} else {
						conj2Count.put(conjIdx, (byte) (count + 1));
					}
				}
			}
		}

		for (Map.Entry<Short, Byte> entry : conj2Count.entrySet()) {
			Conjunction conj = conjunctionCollection.getConjunctionAt(entry.getKey());

			if (conj != null) {
				if (conj.getNumUniqueTokens() == entry.getValue()) {
					if (conjDomain == null) {
						relConjs.add(conj);
					} else if (conj.getConjDomain().equalsIgnoreCase(conjDomain)) {
						relConjs.add(conj);
					}
				}
			}
		}

		return relConjs;
	}

	/**
	 * Finding relevant (i.e., active) conjunctions in an input text string. A
	 * conjunction is active in the input text if the text string contains all
	 * chunks of that conjunction.
	 * 
	 * @param text
	 *            the input text string
	 * @param conjDomain
	 *            the conjunction domain of interest. Set conjDomain = null for
	 *            matching all domains
	 * @return The list of all relevant (active) conjunctions
	 */
	public List<Conjunction> getMatchedConjunctions(String text, String conjDomain) {
		List<Conjunction> mtedConjs = new ArrayList<Conjunction>();

		InputText inputText = new InputText(text);
		String textStr = inputText.getText();
		List<Conjunction> relConjs = getRelevantConjunctions(inputText, conjDomain);

		for (int i = 0; i < relConjs.size(); i++) {
			Conjunction conj = relConjs.get(i);
			List<String> conjChunks = conj.getConjChunks();

			if (conj.isStarting()) {
				if (textStr.startsWith(conjChunks.get(0)) == false) {
					continue;
				}
			}

			if (conj.isEnding()) {
				if (textStr.endsWith(conjChunks.get(conjChunks.size() - 1)) == false) {
					continue;
				}
			}

			int[] comChunkIdxes = conj.getComChunkIdxes();
			if (comChunkIdxes == null) {
				mtedConjs.add(conj);
				continue;
			}

			int numComChunks = comChunkIdxes.length;
			boolean isMatched = true;
			int j = 0;
			while (j < numComChunks) {
				String comChunk = conjChunks.get(comChunkIdxes[j]);
				if (textStr.indexOf(comChunk) < 0) {
					isMatched = false;
					break;
				}

				j++;
			}

			if (isMatched) {
				mtedConjs.add(conj);
			}

		}

		return mtedConjs;
	}

	/**
	 * Finding relevant (i.e., active) conjunctions in the input text string. A
	 * conjunction is relevant/active in an input text string if that text
	 * string contains all chunks of that conjunction in right order. That is
	 * all the chunks of the conjunction appear in the right order in the input
	 * text string.
	 * 
	 * @param text
	 *            the input text string
	 * @param conjDomain
	 *            the conjunction domain of interest. Set conjDomain = null for
	 *            matching all domains
	 * @return The list of all relevant/active conjunctions
	 */
	public List<Conjunction> getMatchedConjunctionsWithOrder(String text, String conjDomain) {

		List<Conjunction> mtedConjs = new ArrayList<Conjunction>();

		InputText inputText = new InputText(text);
		String textStr = inputText.getText();
		List<Conjunction> relConjs = getRelevantConjunctions(inputText, conjDomain);
		int numRelConjs = relConjs.size();

		for (int i = 0; i < numRelConjs; i++) {
			Conjunction conj = relConjs.get(i);
			List<String> conjChunks = conj.getConjChunks();

			if (conj.isStarting()) {
				if (textStr.startsWith(conjChunks.get(0)) == false) {
					continue;
				}
			}

			if (conj.isEnding()) {
				if (textStr.endsWith(conjChunks.get(conjChunks.size() - 1)) == false) {
					continue;
				}
			}

			boolean isMatched = true;
			int j = 0;
			int startIndex = 0;
			int numChunks = conjChunks.size();
			while (j < numChunks) {
				String chunk = conjChunks.get(j);

				int index = textStr.indexOf(chunk, startIndex);
				if (index >= 0) {
					startIndex = index + chunk.length();
				} else {
					isMatched = false;
					break;
				}

				j++;
			}

			if (isMatched) {
				mtedConjs.add(conj);
			}
		}

		return mtedConjs;
	}

	/**
	 * Displaying the conjunction collection and the words-2-conjunctions map.
	 */
	public void print() {
		conjunctionCollection.print();
		words2Conjunctions.print();
	}
}
