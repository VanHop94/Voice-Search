/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmdn.base.nlp.general;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 04-10-2014
 */
public class VnDiacriticAccent {
	private static final Set<Character> diacriticChars = new HashSet<Character>();
	private static final Map<Character, Character> vnCharMap = new HashMap<Character, Character>();

	static {
		String chars = "ÀẢÃÁẠĂẰẲẴẮẶÂẦẨẪẤẬĐÈẺẼÉẸÊỀỂỄẾỆÌỈĨÍỊÒỎÕÓỌÔỒỔỖỐỘƠỜỞỠỚỢÙỦŨÚỤƯỪỬỮỨỰỲỶỸÝỴàảãáạăằẳẵắặâầẩẫấậđèẻẽéẹêềểễếệìỉĩíịòỏõóọôồổỗốộơờởỡớợùủũúụưừửữứựỳỷỹýỵ";

		for (int i = 0; i < chars.length(); i++) {
			diacriticChars.add(chars.charAt(i));
		}

		vnCharMap.put('À', 'A');
		vnCharMap.put('Ả', 'A');
		vnCharMap.put('Ã', 'A');
		vnCharMap.put('Á', 'A');
		vnCharMap.put('Ạ', 'A');
		vnCharMap.put('Ă', 'A');
		vnCharMap.put('Ằ', 'A');
		vnCharMap.put('Ẳ', 'A');
		vnCharMap.put('Ẵ', 'A');
		vnCharMap.put('Ắ', 'A');
		vnCharMap.put('Ặ', 'A');
		vnCharMap.put('Â', 'A');
		vnCharMap.put('Ầ', 'A');
		vnCharMap.put('Ẩ', 'A');
		vnCharMap.put('Ẫ', 'A');
		vnCharMap.put('Ấ', 'A');
		vnCharMap.put('Ậ', 'A');
		vnCharMap.put('Đ', 'D');
		vnCharMap.put('È', 'E');
		vnCharMap.put('Ẻ', 'E');
		vnCharMap.put('Ẽ', 'E');
		vnCharMap.put('É', 'E');
		vnCharMap.put('Ẹ', 'E');
		vnCharMap.put('Ê', 'E');
		vnCharMap.put('Ề', 'E');
		vnCharMap.put('Ể', 'E');
		vnCharMap.put('Ễ', 'E');
		vnCharMap.put('Ế', 'E');
		vnCharMap.put('Ệ', 'E');
		vnCharMap.put('Ì', 'I');
		vnCharMap.put('Ỉ', 'I');
		vnCharMap.put('Ĩ', 'I');
		vnCharMap.put('Í', 'I');
		vnCharMap.put('Ị', 'I');
		vnCharMap.put('Ò', 'O');
		vnCharMap.put('Ỏ', 'O');
		vnCharMap.put('Õ', 'O');
		vnCharMap.put('Ó', 'O');
		vnCharMap.put('Ọ', 'O');
		vnCharMap.put('Ô', 'O');
		vnCharMap.put('Ồ', 'O');
		vnCharMap.put('Ổ', 'O');
		vnCharMap.put('Ỗ', 'O');
		vnCharMap.put('Ố', 'O');
		vnCharMap.put('Ộ', 'O');
		vnCharMap.put('Ơ', 'O');
		vnCharMap.put('Ờ', 'O');
		vnCharMap.put('Ở', 'O');
		vnCharMap.put('Ỡ', 'O');
		vnCharMap.put('Ớ', 'O');
		vnCharMap.put('Ợ', 'O');
		vnCharMap.put('Ù', 'U');
		vnCharMap.put('Ủ', 'U');
		vnCharMap.put('Ũ', 'U');
		vnCharMap.put('Ú', 'U');
		vnCharMap.put('Ụ', 'U');
		vnCharMap.put('Ư', 'U');
		vnCharMap.put('Ừ', 'U');
		vnCharMap.put('Ử', 'U');
		vnCharMap.put('Ữ', 'U');
		vnCharMap.put('Ứ', 'U');
		vnCharMap.put('Ự', 'U');
		vnCharMap.put('Ỳ', 'Y');
		vnCharMap.put('Ỷ', 'Y');
		vnCharMap.put('Ỹ', 'Y');
		vnCharMap.put('Ý', 'Y');
		vnCharMap.put('Ỵ', 'Y');

		vnCharMap.put('à', 'a');
		vnCharMap.put('ả', 'a');
		vnCharMap.put('ã', 'a');
		vnCharMap.put('á', 'a');
		vnCharMap.put('ạ', 'a');
		vnCharMap.put('ă', 'a');
		vnCharMap.put('ằ', 'a');
		vnCharMap.put('ẳ', 'a');
		vnCharMap.put('ẵ', 'a');
		vnCharMap.put('ắ', 'a');
		vnCharMap.put('ặ', 'a');
		vnCharMap.put('â', 'a');
		vnCharMap.put('ầ', 'a');
		vnCharMap.put('ẩ', 'a');
		vnCharMap.put('ẫ', 'a');
		vnCharMap.put('ấ', 'a');
		vnCharMap.put('ậ', 'a');
		vnCharMap.put('đ', 'd');
		vnCharMap.put('è', 'e');
		vnCharMap.put('ẻ', 'e');
		vnCharMap.put('ẽ', 'e');
		vnCharMap.put('é', 'e');
		vnCharMap.put('ẹ', 'e');
		vnCharMap.put('ê', 'e');
		vnCharMap.put('ề', 'e');
		vnCharMap.put('ể', 'e');
		vnCharMap.put('ễ', 'e');
		vnCharMap.put('ế', 'e');
		vnCharMap.put('ệ', 'e');
		vnCharMap.put('ì', 'i');
		vnCharMap.put('ỉ', 'i');
		vnCharMap.put('ĩ', 'i');
		vnCharMap.put('í', 'i');
		vnCharMap.put('ị', 'i');
		vnCharMap.put('ò', 'o');
		vnCharMap.put('ỏ', 'o');
		vnCharMap.put('õ', 'o');
		vnCharMap.put('ó', 'o');
		vnCharMap.put('ọ', 'o');
		vnCharMap.put('ô', 'o');
		vnCharMap.put('ồ', 'o');
		vnCharMap.put('ổ', 'o');
		vnCharMap.put('ỗ', 'o');
		vnCharMap.put('ố', 'o');
		vnCharMap.put('ộ', 'o');
		vnCharMap.put('ơ', 'o');
		vnCharMap.put('ờ', 'o');
		vnCharMap.put('ở', 'o');
		vnCharMap.put('ỡ', 'o');
		vnCharMap.put('ớ', 'o');
		vnCharMap.put('ợ', 'o');
		vnCharMap.put('ù', 'u');
		vnCharMap.put('ủ', 'u');
		vnCharMap.put('ũ', 'u');
		vnCharMap.put('ú', 'u');
		vnCharMap.put('ụ', 'u');
		vnCharMap.put('ư', 'u');
		vnCharMap.put('ừ', 'u');
		vnCharMap.put('ử', 'u');
		vnCharMap.put('ữ', 'u');
		vnCharMap.put('ứ', 'u');
		vnCharMap.put('ự', 'u');
		vnCharMap.put('ỳ', 'y');
		vnCharMap.put('ỷ', 'y');
		vnCharMap.put('ỹ', 'y');
		vnCharMap.put('ý', 'y');
		vnCharMap.put('ỵ', 'y');
	}

	/**
	 * Indicating whether the input Vietnamese text string has the diacritic
	 * accents or not.
	 * 
	 * @param str
	 *            The input Vietnamese text string to be checked
	 * @return True if there is no diacritic characters, false otherwise
	 */
	public static boolean hasVnDiacriticAccents(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (diacriticChars.contains(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Removing the diacritics of a Vietnamese text string.
	 * 
	 * @param str
	 *            the input Vietnamese text string
	 * @return The corresponding Vietnamese text string without diacritics.
	 */
	public static String removeVnDiacriticAccents(String str) {
		String result = "";

		for (int i = 0; i < str.length(); i++) {
			Character sourceChar = str.charAt(i);
			Character destinationChar = vnCharMap.get(sourceChar);
			if (destinationChar != null) {
				result += destinationChar;
			} else {
				result += sourceChar;
			}
		}

		return result;
	}
}
