package smarttouch.uet.edu.voicesearch.classicfier.argument;

import jmdn.base.struct.observation.Observation;
import jmdn.base.struct.sequence.Sequence;
import jmdn.base.util.string.StrUtil;
import jmdn.method.mobile.classification.maxent.Classification;

import java.util.*;

public class CommandArgumentClassifier {
	private Classification classifier = null;
	
	public CommandArgumentClassifier(String commandArgumentClassificationModelDirectory) {
		this.classifier = new Classification(commandArgumentClassificationModelDirectory);
		classifier.init();
	}
	
	public void init() {
		if (!this.classifier.isInitialized()) {
			this.classifier.init();
		}
	}

	public List<String> doCommandArgumentClassification(String sentence) {
		List<String> arguments = new ArrayList<String>();

		Sequence sequence = new Sequence();		
		List<String> tokens = StrUtil.tokenizeString(StrUtil.normalizeString(sentence.toLowerCase()));
		for (String token : tokens) {
			Observation obs = new Observation();
			obs.addToken(token);
			sequence.addObservation(obs);
		}
		
		List<String> cpsList = CommandArgumentFeatureGenerator.scanFeatures(sequence);
		for (String cps : cpsList) {
			String argument = classifier.classify(cps);
			arguments.add(argument);
		}
		
		return arguments;
	}
	public static void main(String[] args){
		CommandArgumentClassifier cl = new CommandArgumentClassifier(System.getProperty("user.dir") + "/domain-models/post-domain-model");
		cl.init();
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.println(cl.doCommandArgumentClassification(scanner.nextLine()));
		}
		
	}
	
}
