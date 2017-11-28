/**
 * 
 */

package jmdn.base.struct.sequence;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import jmdn.base.struct.observation.Observation;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 26-08-2014
 */
public class Sequence {
	private List<Observation> observations = null;

	/**
	 * Default class constructor.
	 */
	public Sequence() {
		observations = new ArrayList<Observation>();
	}

	/**
	 * Adding an observation to the sequence.
	 * 
	 * @param observation
	 *            the observation to be added
	 */
	public void addObservation(Observation observation) {
		observations.add(observation);
	}

	/**
	 * Adding an observation to the sequence. The method first creates an
	 * observation from a text string, and then add that observation to the
	 * sequence.
	 * 
	 * @param string
	 *            the text string for creating the observation
	 * @param hasTag
	 *            true if the string contains the tag of the observation, false
	 *            otherwise
	 */
	public void addObservation(String string, boolean hasTag) {
		Observation observation = new Observation(string, hasTag);
		observations.add(observation);
	}

	/**
	 * Adding an observation to the sequence. The method first creates an
	 * observation from a text string, and then add that observation to the
	 * sequence.
	 * 
	 * @param string
	 *            the text string for creating the observation (there is no tag)
	 */
	public void addObservation(String string) {
		addObservation(string, false);
	}

	/**
	 * Getting the tag of the observation at a given index in the sequence.
	 * 
	 * @param index
	 *            the index of the observation for getting tag
	 * @return The tag of the observation at the input index
	 */
	public String getTag(int index) {
		return observations.get(index).getTag();
	}

	/**
	 * Getting the text content of the observation at a given index in the
	 * sequence.
	 * 
	 * @param index
	 *            the index of the observation for getting observation content
	 * @return The text content of the observation at the index "index"
	 */
	public List<String> getText(int index) {
		return observations.get(index).getTokens();
	}

	/**
	 * Clearing the sequence.
	 */
	public void clear() {
		observations.clear();
	}

	/**
	 * Getting the sequence size.
	 * 
	 * @return The size of the sequence
	 */
	public int size() {
		return observations.size();
	}

	/**
	 * Displaying the sequence.
	 */
	public void print() {
		if (observations.size() <= 0) {
			return;
		}

		for (int i = 0; i < observations.size(); i++) {
			observations.get(i).print();
		}

		System.out.println();
	}

	/**
	 * Saving the sequence to an output stream.
	 * 
	 * @param out
	 *            the output stream
	 * @throws IOException
	 *             IO exception
	 */
	public void write(Writer out) throws IOException {
		if (observations.size() <= 0) {
			return;
		}

		for (int i = 0; i < observations.size(); i++) {
			observations.get(i).write(out);
		}

		out.write("\n");
	}
}
