package smarttouch.uet.edu.voicesearch.classicfier.feature;

import java.util.ArrayList;
import java.util.List;

import jmdn.base.util.string.StrUtil;
import smarttouch.uet.edu.voicesearch.classicfier.Dictionary;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 01-11-2014
 */
public class DomainFeatureGenerator {

	public static List<String> scanFeatures(String sentence) {
		List<String> contextPredicates = new ArrayList<String>();
		List<String> tokens = null;
		sentence = sentence.toLowerCase();
		
		for(String pro : Dictionary.program){
			if(sentence.contains(pro.toLowerCase()))
				contextPredicates.add("tv tv tv tv tv");
		}
		
		for(String channel : Dictionary.channel){
			if(sentence.toLowerCase().contains(channel.toLowerCase())){
				contextPredicates.add("tv tv tv tv tv");
			}
		}
		
		for(String ci : Dictionary.cinema){
			if(sentence.toLowerCase().contains(ci.toLowerCase())){
				contextPredicates.add("fl fl fl fl fl");
			}
		}
		
		if(sentence.contains("phim")){
			contextPredicates.add("tv tv");
			contextPredicates.add("fl");
			contextPredicates.add("fl");
		}
		
		if(sentence.contains("chương trình") || sentence.contains("kênh")){
			contextPredicates.add("tv tv tv");
		}
		
		tokens = StrUtil.tokenizeString(sentence);
		for (int k = 0; k < tokens.size(); k++) {
			/* 1-gram context predicates */
			contextPredicates.add(tokens.get(k));

			/* 2-gram context predicates */
			if (k < tokens.size() - 1) {
				contextPredicates.add(tokens.get(k) + ":" + tokens.get(k + 1));
			}
		}
		return contextPredicates;

	}

	public static List<String> scanFeaturesForTraining(String sentence) {

		String str = sentence.substring(0, sentence.lastIndexOf(","));
		List<String> contextPredicatesLabel = scanFeatures(str.trim());

		contextPredicatesLabel.add(sentence.substring(sentence.lastIndexOf(",") + 1, sentence.length()));

		return contextPredicatesLabel;
	}
}
