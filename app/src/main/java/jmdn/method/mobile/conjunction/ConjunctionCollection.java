/**
 * 
 */

package jmdn.method.mobile.conjunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 26-08-2014
 */
public class ConjunctionCollection {
	private List<Conjunction> conjunctions;

	/**
	 * Default class constructor.
	 */
	public ConjunctionCollection() {
		conjunctions = new ArrayList<Conjunction>();
	}

	/**
	 * Getting the number of conjunctions in the collection.
	 * 
	 * @return The size of conjunction collection
	 */
	public int size() {
		return conjunctions.size();
	}

	/**
	 * Clearing the conjunction collection.
	 */
	public void clear() {
		conjunctions.clear();
	}

	/**
	 * Getting the conjunction at a given index.
	 * 
	 * @param index
	 *            the index at which the conjunction is fetched
	 * @return The conjunction at the index "index"
	 */
	public Conjunction getConjunctionAt(int index) {
		if (index < 0 || index >= conjunctions.size()) {
			return null;
		}

		return conjunctions.get(index);
	}

	/**
	 * Adding a conjunction to the collection.
	 * 
	 * @param conjString
	 *            the conjunction string of the conjunction to be added
	 */
	public void addConjunction(String conjString) {
		Conjunction conjunction = new Conjunction(conjString);
		addConjunction(conjunction);
	}

	/**
	 * Adding a conjunction to the collection.
	 * 
	 * @param conjunction
	 *            the conjunction to be added
	 */
	public void addConjunction(Conjunction conjunction) {
		conjunctions.add(conjunction);
	}

	/**
	 * Getting all conjunctions in the collection.
	 * 
	 * @return A list of all conjunctions in the collection
	 */
	public List<Conjunction> getAllConjunctions() {
		return conjunctions;
	}

	/**
	 * Displaying the conjunction collection.
	 */
	public void print() {
		for (int i = 0; i < conjunctions.size(); i++) {
			conjunctions.get(i).print();
		}
	}
}
