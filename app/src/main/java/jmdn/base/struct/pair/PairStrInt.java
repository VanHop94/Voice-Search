/**
 * 
 */

package jmdn.base.struct.pair;

import jmdn.base.util.string.StrUtil;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class PairStrInt implements Comparable<Object> {
	public String first;
	public Integer second;

	/**
	 * Class constructor.
	 * 
	 * @param f
	 *            the first element (String)
	 * @param s
	 *            the second element (int)
	 */
	public PairStrInt(String f, int s) {
		this.first = f;
		this.second = s;
	}

	/**
	 * Class constructor.
	 * 
	 * @param f
	 *            the first element (String)
	 * @param s
	 *            the second element (Integer)
	 */
	public PairStrInt(String f, Integer s) {
		this.first = f;
		this.second = s;
	}

	@Override
	public int compareTo(Object o) {
		return this.second - ((PairStrInt) o).second;
	}

	/**
	 * Displaying pair.
	 */
	public void print() {
		System.out.println(this.first + "\t\t\t" + this.second);
	}

	@Override
	public String toString() {
		return StrUtil.normalizeString(this.first);
	}
}
