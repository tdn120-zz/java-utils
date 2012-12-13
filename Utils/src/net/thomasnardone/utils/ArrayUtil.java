package net.thomasnardone.utils;

public class ArrayUtil {
	/**
	 * @return The first index whose value that matches <tt>value</tt>, or -1 if <tt>value</tt> is not found.
	 */
	public static int find(final int value, final int[] array) {
		final int length = array.length;
		for (int i = 0; i < length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Insert <tt>value</tt> into <tt>array</tt> at <tt>index</tt>.
	 * 
	 * @return The resulting array (<tt>array</tt> will remain unchanged).
	 */
	public static int[] insert(final int value, final int[] array, final int index) {
		int[] newArray = new int[array.length + 1];
		System.arraycopy(array, 0, newArray, 0, index);
		newArray[index] = value;
		if ((index + 1) < newArray.length) {
			System.arraycopy(array, index, newArray, index + 1, array.length - index);
		}
		return newArray;
	}

	public static int[] remove(final int[] array, final int index) {
		int[] newArray = new int[array.length - 1];
		System.arraycopy(array, 0, newArray, 0, index);
		System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
		return newArray;
	}

	/**
	 * Swaps the values in indexes i and j
	 */
	public static boolean swap(final int[] array, final int i, final int j) {
		if ((j >= array.length) || (j < 0) || (i >= array.length) || (i < 0)) {
			return false;
		}

		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		return true;
	}
}
