/**
 * 
 */

package jmdn.base.struct.collection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jmdn.base.struct.observation.Observation;
import jmdn.base.util.string.StrUtil;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class ObservationCollection {
	private List<Observation> observations = null;

	/**
	 * Default class constructor.
	 */
	public ObservationCollection() {
		observations = new ArrayList<Observation>();
	}

	/**
	 * Class constructor.
	 * 
	 * @param observations
	 *            a list of observations
	 */
	public ObservationCollection(List<Observation> observations) {
		this();

		for (int i = 0; i < observations.size(); i++) {
			this.observations.add(observations.get(i));
		}
	}

	/**
	 * Getting the size of the observation collection.
	 * 
	 * @return the collection size
	 */
	public int size() {
		return observations.size();
	}

	/**
	 * Clearing the list of observations.
	 */
	public void clear() {
		observations.clear();
	}

	/**
	 * Adding an observation to the collection.
	 * 
	 * @param observation
	 *            the observation to be added
	 */
	public void addObservation(Observation observation) {
		observations.add(observation);
	}

	/**
	 * Adding a list of observations to the collection.
	 * 
	 * @param observations
	 *            the list of observations to be added
	 */
	public void addObservations(List<Observation> observations) {
		for (int i = 0; i < observations.size(); i++) {
			this.observations.add(observations.get(i));
		}
	}

	/**
	 * Setting a new list of observations for the collection.
	 * 
	 * @param observations
	 *            the new list of observations
	 */
	public void setObservations(List<Observation> observations) {
		clear();
		addObservations(observations);
	}

	/**
	 * Getting the observation at the index "index".
	 * 
	 * @param index
	 *            the index at which the observation is fetched
	 * @return the fetched observation
	 */
	public Observation getObservation(int index) {
		Observation observation = null;

		if (index >= 0 && index < observations.size()) {
			observation = observations.get(index);
		}

		return observation;
	}

	/**
	 * Removing the observation at the index "index".
	 * 
	 * @param index
	 *            the index at which the observation will be removed
	 */
	public void removeObservation(int index) {
		if (index >= 0 && index < observations.size()) {
			observations.remove(index);
		}
	}

	/**
	 * Displaying the collection.
	 */
	public void print() {
		for (int i = 0; i < observations.size(); i++) {
			observations.get(i).print();
		}
	}

	/**
	 * Saving the collection to a file.
	 * 
	 * @param out
	 *            the output stream for saving
	 * @throws IOException
	 *             IO exception
	 */
	public void write(Writer out) throws IOException {
		for (int i = 0; i < observations.size(); i++) {
			observations.get(i).write(out);
		}
	}

	/**
	 * Saving the collection to a file.
	 * 
	 * @param filename
	 *            the filename which will contain the collection
	 */
	public void write(String filename) {
		BufferedWriter out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF8"));
			write(out);

			out.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Reading the collection from input stream.
	 * 
	 * @param in
	 *            input stream
	 * @param hasTag
	 *            true if each line of the input stream contains an observation
	 *            tag, false otherwise
	 * @throws IOException
	 *             IO exception
	 */
	public void read(BufferedReader in, boolean hasTag) throws IOException {
		String line;
		while ((line = in.readLine()) != null) {
			List<String> tokens = StrUtil.tokenizeString(line);

			if (tokens.isEmpty()) {
				continue;
			}

			// add a new observation
			observations.add(new Observation(tokens, hasTag));
		}
	}

	/**
	 * Reading the collection from a file.
	 * 
	 * @param filename
	 *            the filename of the file containing the collection
	 * @param hasTag
	 *            true if each line of the file containing an observation tag,
	 *            false otherwise
	 */
	public void read(String filename, boolean hasTag) {
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"));

			read(in, hasTag);

			in.close();

		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Reading the collection from a file.
	 * 
	 * @param filename
	 *            the filename of the file containing the collection
	 */
	public void read(String filename) {
		read(filename, true);
	}

	/**
	 * Randomly swapping the observations in the collection.
	 */
	public void randomSwap() {
		(new CollectionUtil<Observation>()).randomSwap(observations);
	}

	/**
	 * Randomly swapping the observations in the collection.
	 * 
	 * @param times
	 *            the number of swapping loops
	 */
	public void randomSwap(int times) {
		(new CollectionUtil<Observation>()).randomSwap(observations, times);
	}

	/**
	 * Randomly partitioning the collection into two parts. The original
	 * collection will be randomly divided into "noFolds" partitions. The first
	 * part is one partition, and the second part consists of the remaining
	 * partitions.
	 * 
	 * @param firstPart
	 *            the sub-collection containing the first part
	 * @param secondPart
	 *            the sub-collection containing the second part
	 * @param nFolds
	 *            the number of folds for partitioning
	 */
	public void randomPartition(ObservationCollection firstPart, ObservationCollection secondPart, int nFolds) {
		List<Observation> firstLst = new ArrayList<Observation>();
		List<Observation> secondLst = new ArrayList<Observation>();

		(new CollectionUtil<Observation>()).randomPartition(observations, firstLst, secondLst, nFolds);

		firstPart.setObservations(firstLst);
		secondPart.setObservations(secondLst);
	}

	/**
	 * Randomly partitioning the collection into nFolds partitions.
	 * 
	 * @param nFolds
	 *            the number of folds for partitioning
	 * @return A list of nFolds collections of observations
	 */
	public List<ObservationCollection> nFoldsRandomPartition(int nFolds) {
		List<ObservationCollection> res = null;

		List<List<Observation>> list = (new CollectionUtil<Observation>()).randomNFoldsPartition(observations, nFolds);

		if (list.size() > 0) {
			res = new ArrayList<ObservationCollection>();
			for (int i = 0; i < list.size(); i++) {
				ObservationCollection observationCollection = new ObservationCollection(list.get(i));
				res.add(observationCollection);
			}
		}

		return res;
	}
}
