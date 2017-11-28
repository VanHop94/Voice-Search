/**
 * 
 */

package jmdn.base.struct.pair;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class Pair<F, S> {
	public F first;
	public S second;

	/**
	 * Class constructor.
	 * 
	 * @param f
	 *            the first element
	 * @param s
	 *            the second element
	 */
	public Pair(F f, S s) {
		this.first = f;
		this.second = s;
	}
}
