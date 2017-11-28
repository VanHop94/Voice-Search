/**
 * 
 */

package jmdn.base.struct.pair;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class PairIntInt implements Comparable<Object> {
	public Integer first;
	public Integer second;

	/**
	 * Class constructor.
	 * 
	 * @param f
	 *            the first element
	 * @param s
	 *            the second element
	 */
	public PairIntInt(int f, int s) {
		this.first = f;
		this.second = s;
	}

	/**
	 * Class constructor.
	 * 
	 * @param f
	 *            the first element (Integer)
	 * @param s
	 *            the second element (Integer)
	 */
	public PairIntInt(Integer f, Integer s) {
		this.first = f;
		this.second = s;
	}

	@Override
	public int compareTo(Object o) {
		return this.second - ((PairIntInt) o).second;
	}
}
