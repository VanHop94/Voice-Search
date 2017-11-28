/**
 * 
 */

package jmdn.base.struct.sequence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jmdn.base.struct.collection.CollectionUtil;
import jmdn.base.struct.observation.Observation;
import jmdn.base.util.string.StrUtil;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 26-08-2014
 */
public class SequenceCollection {
	private List<Sequence> sequences = null;

	/**
	 * Default class constructor.
	 */
	public SequenceCollection() {
		sequences = new ArrayList<Sequence>();
	}

	/**
	 * Class constructor from a list of sequence.
	 * 
	 * @param sequenceCollection
	 *            the list of sequence for constructing the sequence collection
	 */
	public SequenceCollection(List<Sequence> sequenceCollection) {
		this();

		for (int i = 0; i < sequenceCollection.size(); i++) {
			sequences.add(sequenceCollection.get(i));
		}
	}

	/**
	 * Getting the size of sequence collection.
	 * 
	 * @return The number of sequences in the sequence collection
	 */
	public int size() {
		return sequences.size();
	}

	/**
	 * Clearing the sequence collection.
	 */
	public void clear() {
		sequences.clear();
	}

	/**
	 * Adding a sequence to the sequence collection.
	 * 
	 * @param sequence
	 *            The sequence to be added to the collection
	 */
	public void addSequence(Sequence sequence) {
		sequences.add(sequence);
	}

	/**
	 * Adding a list of sequences to the sequence collection.
	 * 
	 * @param sequences
	 *            the list of sequences to be added
	 */
	public void addSequences(List<Sequence> sequences) {
		for (int i = 0; i < sequences.size(); i++) {
			this.sequences.add(sequences.get(i));
		}
	}

	/**
	 * Setting the new content (new sequences) for the sequence collection.
	 * 
	 * @param sequences
	 *            the list of new sequences for the sequence collection
	 */
	public void setSequences(List<Sequence> sequences) {
		clear();

		addSequences(sequences);
	}

	/**
	 * Getting a sequence at a given index of the sequence collection.
	 * 
	 * @param index
	 *            the index of the sequence to be fetched
	 * @return The sequence at the index "index"
	 */
	public Sequence getSequenceAt(int index) {
		Sequence res = null;
		if (index >= 0 && index < sequences.size()) {
			res = sequences.get(index);
		}
		return res;
	}

	/**
	 * Removing a sequence at a given index from the sequence collection.
	 * 
	 * @param index
	 *            the index at which the sequence will be removed
	 */
	public void removeSequenceAt(int index) {
		if (index >= 0 && index < sequences.size()) {
			sequences.remove(index);
		}
	}

	/**
	 * Displaying the sequence collection.
	 */
	public void print() {
		for (int i = 0; i < sequences.size(); i++) {
			sequences.get(i).print();
		}
	}

	/**
	 * Saving the sequence collection to an output stream.
	 * 
	 * @param out
	 *            the output stream
	 * @throws IOException
	 *             IO exception
	 */
	public void write(Writer out) throws IOException {
		for (int i = 0; i < sequences.size(); i++) {
			sequences.get(i).write(out);
		}
	}

	/**
	 * Saving the sequence collection to a file.
	 * 
	 * @param filename
	 *            the filename of the file for saving the sequence collection
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
	 * Reading the sequence collection from an input stream.
	 * 
	 * @param in
	 *            the input stream
	 * @param hasTag
	 *            true if each line of the input stream containing a tag, false
	 *            otherwise
	 * @throws IOException
	 *             IO exception
	 */
	public void read(BufferedReader in, boolean hasTag) throws IOException {
		Sequence sequence = new Sequence();
		String line;

		while ((line = in.readLine()) != null) {
			List<String> tokens = StrUtil.tokenizeString(line);

			if (tokens.isEmpty()) {
				// sequence delimiter
				if (sequence.size() > 0) {
					// add this sequence to sequence collection
					addSequence(sequence);
					// allocate a new sequence
					sequence = new Sequence();
				}
			} else {
				// a new observation
				Observation observation = new Observation(tokens, hasTag);
				// add this observation to the current sequence
				sequence.addObservation(observation);
			}
		}

		// add the last sequence (if any)
		if (sequence.size() > 0) {
			addSequence(sequence);
		}
	}

	/**
	 * Reading the sequence collection from a file.
	 * 
	 * @param filename
	 *            the file containing the sequence collection
	 * @param hasTag
	 *            true if each line of the file containing a tag, false
	 *            otherwise
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
	 * Reading the sequence from a file.
	 * 
	 * @param filename
	 *            the file containing the sequence collection
	 */
	public void read(String filename) {
		read(filename, true);
	}

	/**
	 * Randomly swapping the sequences in the sequence collection.
	 */
	public void randomSwap() {
		(new CollectionUtil<Sequence>()).randomSwap(sequences);
	}

	/**
	 * Randomly swapping the sequences in the sequence collection. The swapping
	 * is repeated "times" times.
	 * 
	 * @param times
	 *            the number of repeats of random swapping
	 */
	public void randomSwap(int times) {
		(new CollectionUtil<Sequence>()).randomSwap(sequences, times);
	}

	/**
	 * Randomly partitioning the sequence collection into two parts. The
	 * collection is first divided into nFolds partitions. The first part then
	 * consists of one partition, and the second list consists of the remaining
	 * partitions.
	 * 
	 * @param firstPart
	 *            the first list containing one partition
	 * @param secondPart
	 *            the second list containing the remaining partitions
	 * @param nFolds
	 *            the number of partitions
	 */
	public void randomPartition(SequenceCollection firstPart, SequenceCollection secondPart, int nFolds) {
		List<Sequence> firstLst = new ArrayList<Sequence>();
		List<Sequence> secondLst = new ArrayList<Sequence>();

		(new CollectionUtil<Sequence>()).randomPartition(sequences, firstLst, secondLst, nFolds);

		firstPart.setSequences(firstLst);
		secondPart.setSequences(secondLst);
	}

	/**
	 * Randomly partitioning the sequence collection into nFolds partitions.
	 * 
	 * @param nFolds
	 *            the number of partitions
	 * @return A list of nFolds collections of sequences
	 */
	public List<SequenceCollection> nFoldsRandomPartition(int nFolds) {
		List<SequenceCollection> res = null;

		List<List<Sequence>> lst = (new CollectionUtil<Sequence>()).randomNFoldsPartition(sequences, nFolds);
		if (lst.size() > 0) {
			res = new ArrayList<SequenceCollection>();
			for (int i = 0; i < lst.size(); i++) {
				SequenceCollection sequenceCollection = new SequenceCollection((List<Sequence>) lst.get(i));
				res.add(sequenceCollection);
			}
		}

		return res;
	}
}
