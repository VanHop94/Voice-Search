/**
 * 
 */

package jmdn.base.struct.collection;

import java.util.*;

import jmdn.base.math.random.RandomNumber;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class CollectionUtil<T> {
	/**
	 * Randomly swapping elements of a list of elements. This method will call
	 * randomSwap(list, 1).
	 * 
	 * @param list
	 *            the input list of elements
	 */
	public void randomSwap(List<T> list) {
		randomSwap(list, 1);
	}

	/**
	 * Randomly swapping the elements of a list.
	 * 
	 * @param list
	 *            the input list of elements
	 * @param times
	 *            random swap will be repeated (sizeof(list) * times) times
	 */
	public void randomSwap(List<T> list, int times) {
		RandomNumber randNum = new RandomNumber();
		int lstSize = list.size();

		int repeat = times * lstSize;
		for (int i = 0; i < repeat; i++) {
			int idx1 = randNum.genNextInt(lstSize);
			int idx2 = randNum.genNextInt(lstSize);
			if (idx1 != idx2) {
				Collections.swap(list, idx1, idx2);
			}
		}
	}

	/**
	 * Randomly divided the original list into noParts partitions.
	 * 
	 * @param orgLst
	 *            the input (original) list of elements
	 * @param firstLst
	 *            the list containing the first part
	 * @param secondLst
	 *            the list containing the remaining parts
	 * @param noParts
	 *            the number of partitions
	 * @return return 0 if partitioning successfully, 1 otherwise
	 */
	public int randomPartition(List<T> orgLst, List<T> firstLst, List<T> secondLst, int noParts) {
		if (noParts < 2) {
			return 1;
		}

		randomSwap(orgLst, 3);

		int orgSize = orgLst.size();
		int partSize = orgSize / noParts;

		if (partSize <= 0) {
			return 1;
		}

		firstLst.clear();
		secondLst.clear();

		Set<Integer> idxes = new HashSet<Integer>();
		RandomNumber randNum = new RandomNumber();

		while (idxes.size() < partSize) {
			int randInt = randNum.genNextInt(orgSize);
			if (idxes.contains(randInt)) {
				continue;
			} else {
				idxes.add(randInt);
			}
		}

		for (int i = 0; i < orgSize; i++) {
			if (idxes.contains(i)) {
				firstLst.add(orgLst.get(i));
			} else {
				secondLst.add(orgLst.get(i));
			}
		}

		return 0;
	}

	/**
	 * Randomly partitioning an input list of elements into n folds.
	 * 
	 * @param orgLst
	 *            the original list of elements
	 * @param nFolds
	 *            the number of folds (partitions)
	 * @return A list of partitions (each partition is a list of elements)
	 */
	public List<List<T>> randomNFoldsPartition(List<T> orgLst, int nFolds) {
		List<List<T>> res = null;

		int orgSize = orgLst.size();
		if (orgSize <= 0 || nFolds < 2 || orgSize < nFolds) {
			return res;
		}

		res = new ArrayList<List<T>>();
		for (int i = 0; i < nFolds; i++) {
			res.add(new ArrayList<T>());
		}

		randomSwap(orgLst);

		for (int i = 0; i < orgSize; i++) {
			int idx = i % nFolds;
			List<T> list = (List<T>) res.get(idx);
			list.add(orgLst.get(i));
		}

		return res;
	}
}
