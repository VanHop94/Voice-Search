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
public class PairStrDouble implements Comparable<Object> {
	public String first;
	public Double second;

	/**
	 * Class constructor.
	 * 
	 * @param f
	 *            the first element (String)
	 * @param s
	 *            the second element (double)
	 */
	public PairStrDouble(String f, double s) {
		this.first = f;
		this.second = s;
	}

	/**
	 * Class constructor.
	 * 
	 * @param f
	 *            the first element (String)
	 * @param s
	 *            the second element (Double)
	 */
	public PairStrDouble(String f, Double s) {
		this.first = f;
		this.second = s;
	}

	@Override
	public int compareTo(Object o) {
		double val1 = second.doubleValue();
		double val2 = ((PairStrDouble) o).second.doubleValue();

		if (val1 > val2) {
			return 1;
		} else if (val1 == val2) {
			return 0;
		} else {
			return -1;
		}
	}

	/**
	 * Display pair.
	 */
	public void print() {
		System.out.println(this.first + "\t\t\t" + this.second);
	}

	@Override
	public String toString() {
		return StrUtil.normalizeString(this.first);
	}
}
