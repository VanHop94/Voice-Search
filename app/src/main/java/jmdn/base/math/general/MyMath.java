/**
 * 
 */

package jmdn.base.math.general;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class MyMath {
	/**
	 * Computing the logarithm of x to base b, i.e., log_b(x).
	 * 
	 * @param b
	 *            the base
	 * @param x
	 *            the number to compute log_b value
	 * @return The value of log_b(x)
	 */
	public static double log(double b, double x) {
		return Math.log(x) / Math.log(b);
	}
}
