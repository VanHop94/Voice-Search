package smarttouch.uet.edu.voicesearch.classicfier.action;

import java.util.List;

import jmdn.method.mobile.conjunction.Conjunction;
import jmdn.method.mobile.conjunction.ConjunctionMatcher;
import smarttouch.uet.edu.voicesearch.classicfier.Dictionary;

/**
 * Created by VanHop on 4/14/2016.
 */
public class ClassifiAction {

    private static boolean initialled = false;
    private static ConjunctionMatcher matcher;
    public static void init() {
        matcher = new ConjunctionMatcher();
        for (String line : Dictionary.actions) {
            String [] tokens = line.split(":");
            if(tokens.length < 3) continue;
            String feature = tokens[0].trim();
            String action = tokens[1].trim();
            String listCojs = tokens[2].trim();

            String [] conjunctions = listCojs.split(",");
            for(String conjunction : conjunctions){
                String [] conjText = conjunction.split("-");
                int priority = Integer.parseInt(conjText[0].trim());
                String conjuc = conjText[1].trim();

                matcher.addConjunction(new Conjunction(feature,action, priority, conjuc));
            }
        }

        initialled = true;
    }

    public static String getAction(String feature, String text) {
        if (!initialled) return "none";

        List<Conjunction> conjunctions = matcher.getMatchedConjunctions(text, feature);
        if(conjunctions.size() == 0)
            return "none";

        Conjunction conjTemp = conjunctions.get(0);
        for(Conjunction conj : conjunctions){
            if(conj.getConjPriority() < conjTemp.getConjPriority())
                conjTemp = conj;
        }

        return conjTemp.getConjType();
    }
}
