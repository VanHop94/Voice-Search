/**
 * 
 */

package jmdn.base.util.filesystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import jmdn.base.struct.pair.PairStrInt;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 12-09-2014
 */
public class FileSaver {
	/**
	 * Opening a buffered writer for saving data to a file.
	 * 
	 * @param filename
	 *            the filename (may include a path to it)
	 * @param encoding
	 *            the encoding of the resulting file
	 * @return A reference to the BufferedWriter object or null if failed to
	 *         create the file
	 */
	public static BufferedWriter openBufferedWriter(String filename, String encoding) {
		BufferedWriter bufferedWriter = null;
		String streamEncoding = (encoding == null) ? "UTF8" : encoding;

		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), streamEncoding));

		} catch (Exception e) {
			System.err.println(e.toString());
			bufferedWriter = null;
		}

		return bufferedWriter;
	}

	/**
	 * Opening a buffered writer for saving data to a file.
	 * 
	 * @param directory
	 *            the directory the file
	 * @param filename
	 *            the filename
	 * @param encoding
	 *            the encoding of the resulting file
	 * @return A reference to the BufferedWriter object or null if failed to
	 *         create the file
	 */
	public static BufferedWriter openBufferedWriter(String directory, String filename, String encoding) {
		BufferedWriter bufferedWriter = null;
		String streamEncoding = (encoding == null) ? "UTF8" : encoding;

		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					DirFileUtil.normalizeDirectory(directory) + File.separator + filename), streamEncoding));

		} catch (Exception e) {
			System.err.println(e.toString());
			bufferedWriter = null;
		}

		return bufferedWriter;
	}

	/**
	 * Saving a list of text strings to a file.
	 * 
	 * @param list
	 *            the input list of text strings to be saved
	 * @param filename
	 *            the filename
	 * @param encoding
	 *            the encoding of the resulting file
	 * @return True if saving successfully, false otherwise
	 */
	public static boolean saveListString(List<String> list, String filename, String encoding) {
		BufferedWriter bufferedWriter = openBufferedWriter(filename, encoding);

		if (bufferedWriter == null) {
			return false;
		}

		try {
			for (int i = 0; i < list.size(); i++) {
				bufferedWriter.write((String) list.get(i) + "\n");
			}

			bufferedWriter.close();

		} catch (IOException e) {
			System.err.println(e.toString());
			return false;
		}

		return true;
	}

	/**
	 * Saving a list of text strings to a file. The default encoding of the
	 * resulting file is UTF8.
	 * 
	 * @param list
	 *            the input list of text strings to be saved
	 * @param filename
	 *            the filename
	 * @return True if saving successfully, false otherwise
	 */
	public static boolean saveListString(List<String> list, String filename) {
		return saveListString(list, filename, "UTF8");
	}

	/**
	 * Saving a list of pair of String-Integer to a file.
	 * 
	 * @param list
	 *            the input list of pair of String-Integer to be saved
	 * @param filename
	 *            the filename
	 * @param encoding
	 *            the encoding of the resulting file
	 * @return True if saving successfully, false otherwise
	 */
	public static boolean saveListStrInt(List<PairStrInt> list, String filename, String encoding) {
		BufferedWriter bufferedWriter = openBufferedWriter(filename, encoding);

		if (bufferedWriter == null) {
			return false;
		}

		try {
			for (int i = list.size() - 1; i >= 0; i--) {
				bufferedWriter.write(list.get(i).toString() + "\n");
			}

			bufferedWriter.close();

		} catch (IOException e) {
			System.err.println(e.toString());
			return false;
		}

		return true;
	}

	/**
	 * Saving a list of pair of String-Integer to a file. The default encoding
	 * of the resulting file is UTF8.
	 * 
	 * @param list
	 *            the input list of pair of String-Integer to be saved
	 * @param filename
	 *            the filename
	 * @return True if saving successfully, false otherwise
	 */
	public static boolean saveListStrInt(List<PairStrInt> list, String filename) {
		return saveListStrInt(list, filename, "UTF8");
	}
}
