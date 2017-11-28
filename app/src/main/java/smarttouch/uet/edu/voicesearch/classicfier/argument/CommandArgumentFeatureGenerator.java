package smarttouch.uet.edu.voicesearch.classicfier.argument;

import java.util.ArrayList;
import java.util.List;

import jmdn.base.struct.sequence.Sequence;
import jmdn.base.util.string.StrUtil;
import smarttouch.uet.edu.voicesearch.classicfier.Dictionary;
import smarttouch.uet.edu.voicesearch.util.Regex;

public class CommandArgumentFeatureGenerator {


	private static List<String> genFeatures(Sequence sequence, int len, int i) {
		List<String> cps = new ArrayList<String>();
		// 1-gram
		for (int j = -2; j <= 2; j++) {
			if (i + j >= 0 && i + j < len) {
				cps.add("w:" + j + ":" + sequence.getText(i + j).get(0));
			}
		}

		// 2-grams
		for (int j = -2; j <= 1; j++) {
			if (i + j >= 0 && i + j + 1 < len) {
				cps.add("ww:" + j + ":" + (j + 1) + ":" + sequence.getText(i + j).get(0) + ":"
						+ sequence.getText(i + j + 1).get(0));
			}
		}

		// 3-grams
		for (int j = -2; j <= 0; j++) {
			if (i + j >= 0 && i + j + 2 < len) {
				cps.add("www:" + j + ":" + (j + 1) + ":" + (j + 2) + ":" + sequence.getText(i + j).get(0) + ":"
						+ sequence.getText(i + j + 1).get(0) + ":" + sequence.getText(i + j + 2).get(0));
			}
		}

		List<String> inWindowCps = new ArrayList<String>();

		/* Window size = 1 , 1-gram */
		inWindowCps.add(sequence.getText(i).get(0));

		/* Window size = 3 , 2-gram */
		for (int j = -1; j <= 0; j++) {
			if (i + j >= 0 && i + j + 1 < len) {
				inWindowCps.add(sequence.getText(i + j).get(0) + " " + sequence.getText(i + j + 1).get(0));
			}
		}

		// 3-grams, window size = 5
		for (int j = -2; j <= 0; j++) {
			if (i + j >= 0 && i + j + 2 < len) {
				inWindowCps.add(sequence.getText(i + j).get(0) + " " + sequence.getText(i + j + 1).get(0) + " "
						+ sequence.getText(i + j + 2).get(0));
			}
		}

		// 4-grams, window size = 7
		for (int j = -3; j <= 0; j++) {
			if (i + j >= 0 && i + j + 3 < len) {
				inWindowCps.add(sequence.getText(i + j).get(0) + " " + sequence.getText(i + j + 1).get(0) + " "
						+ sequence.getText(i + j + 2).get(0) + " " + sequence.getText(i + j + 3).get(0));
			}
		}

		// 5-grams, window size = 9
		for (int j = -4; j <= 0; j++) {
			if (i + j >= 0 && i + j + 4 < len) {
				inWindowCps.add(sequence.getText(i + j).get(0) + " " + sequence.getText(i + j + 1).get(0) + " "
						+ sequence.getText(i + j + 2).get(0) + " " + sequence.getText(i + j + 3).get(0) + " "
						+ sequence.getText(i + j + 4).get(0));
			}
		}

		// 6-grams, window size = 11
		for (int j = -5; j <= 0; j++) {
			if (i + j >= 0 && i + j + 5 < len) {
				inWindowCps.add(sequence.getText(i + j).get(0) + " " + sequence.getText(i + j + 1).get(0) + " "
						+ sequence.getText(i + j + 2).get(0) + " " + sequence.getText(i + j + 3).get(0) + " "
						+ sequence.getText(i + j + 4).get(0) + " " + sequence.getText(i + j + 5).get(0));
			}
		}

		// 7-grams, window size = 13
		for (int j = -6; j <= 0; j++) {
			if (i + j >= 0 && i + j + 6 < len) {
				inWindowCps.add(sequence.getText(i + j).get(0) + " " + sequence.getText(i + j + 1).get(0) + " "
						+ sequence.getText(i + j + 2).get(0) + " " + sequence.getText(i + j + 3).get(0) + " "
						+ sequence.getText(i + j + 4).get(0) + " " + sequence.getText(i + j + 5).get(0) + " "
						+ sequence.getText(i + j + 6).get(0));
			}
		}
		
		// time
        boolean time1 = false;
        for (String in : inWindowCps) {
            if (Dictionary.time.contains(in)) {
                cps.add("TIME");
                time1 = true;
                break;
            }
        }
        if (!time1) {
            for (String in : inWindowCps) {
                if (in.matches(Regex.TIME)) {
                    cps.add("TIME");
                    break;
                }
            }
        }

        // day
        boolean day1 = false;
        for (String in : inWindowCps) {
            if (Dictionary.day.contains(in)) {
                cps.add("DAY");
                day1 = true;
                break;
            }
        }
        if (!day1) {
            for (String in : inWindowCps) {
                if (in.matches(Regex.DAY)) {
                    cps.add("DAY");
                    break;
                }
            }
        }
        
        // check date
        for (String in : inWindowCps) {
            if (in.matches(Regex.DATE)) {
                cps.add("DATE");
                break;
            }
        }
        
        
        // check full date
        for (String in : inWindowCps) {
            if (in.matches(Regex.FULL_DATE)) {
                cps.add("FULLDATE");
                break;
            }
        }
		
		// check channel
        /*for (String in : inWindowCps) {
            if (channel.contains(in)) {
                cps.add("CHANNEL");
                break;
            }
        }*/
		
		// check program
        /*for (String in : inWindowCps) {
            if (program.contains(in)) {
                cps.add("PROGRAM");
                break;
            }
        }*/
		
		// check cinema
        for (String in : inWindowCps) {
            if (Dictionary.cinema.contains(in)) {
                cps.add("CI CI CI CI CI");
                break;
            }
        }

		return cps;
	}

	public static List<String> scanFeatures(Sequence sequence) {
		List<String> cpsList = new ArrayList<String>();

		int len = sequence.size();
		for (int i = 0; i < len; i++) {
			List<String> cps = genFeatures(sequence, len, i);

			cpsList.add(StrUtil.join(cps));
		}

		return cpsList;
	}

	public static List<String> scanFeaturesForTraining(Sequence sequence) {
		List<String> cpsList = new ArrayList<String>();

		int len = sequence.size();
		for (int i = 0; i < len; i++) {
			List<String> cps = genFeatures(sequence, len, i);
			String tag = sequence.getTag(i);
			cps.add(tag);
			cpsList.add(StrUtil.join(cps));
		}

		return cpsList;
	}
}
