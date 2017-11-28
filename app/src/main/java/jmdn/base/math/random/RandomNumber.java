/**
 * 
 */

package jmdn.base.math.random;

import java.util.Random;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class RandomNumber {
	private Random randomGenerator = null;

	/**
	 * Class default constructor.
	 */
	public RandomNumber() {
		randomGenerator = new Random();
	}

	/**
	 * Generating the next random integer in [0..MAX-1], MAX depends on
	 * platform, machine architecture.
	 * 
	 * @return A random (integer) number
	 */
	public int genNextInt() {
		return randomGenerator.nextInt();
	}

	/**
	 * Generating the next random integer in [0..max].
	 * 
	 * @param max
	 *            an integer that is the upper limit of resulting random numbers
	 * @return A random (integer) number
	 */
	public int genNextInt(int max) {
		return randomGenerator.nextInt(max);
	}
}
