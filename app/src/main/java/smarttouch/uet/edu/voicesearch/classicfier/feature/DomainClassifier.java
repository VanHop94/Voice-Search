package smarttouch.uet.edu.voicesearch.classicfier.feature;

import java.util.List;

import jmdn.base.util.string.StrUtil;
import jmdn.method.mobile.classification.maxent.Classification;

public class DomainClassifier {
	private Classification classifier = null;
	
	public DomainClassifier(String postDomainClassificationModelDirectory) {
		this.classifier = new Classification(postDomainClassificationModelDirectory);
	}
	
	public void init() {
		if (!this.classifier.isInitialized()) {
			this.classifier.init();
		}
	}

	public String doDomainClassification(String str) {
		List<String> cps = DomainFeatureGenerator.scanFeatures(str);
		return classifier.classify(StrUtil.join(cps));
	}
	
}
