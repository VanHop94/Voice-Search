/**
 * 
 */

package jmdn.base.math.random;

import java.util.List;

import jmdn.base.struct.pair.PairIntDouble;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class SimpleSampler {
	private final double ADDITIONAL_WEIGHT = 1.5;
	private boolean isScaled = false;
	private List<PairIntDouble> distributions = null;

	public void doScaling() {
		if (!isScaled) {
			for (int i = 0; i < distributions.size() - 1; i++) {
				PairIntDouble pair = distributions.get(i);
				pair.second = Math.log10(pair.second + ADDITIONAL_WEIGHT);
			}

			isScaled = true;
		}
	}
}
