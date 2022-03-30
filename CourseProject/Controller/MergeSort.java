package Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This sorting method splits the list into sublists. These sublists are then
 * sorted and then merged back together. It's time complexity is O(nlogn) in the
 * every-case.
 * 
 * @author Patrick Muradaz
 * @param <E> element type being sorted
 */
public class MergeSort<E>
{

	/**
	 * Entry-place for this sort algorithm. This method enters into the
	 * recursive method, which does the heavy lifting, and then returns the
	 * sorted list.
	 * 
	 * @param list is the list to sort.
	 * @param comp is the comparator that defines how the sorter will operate
	 *             (lowest vals first/last, how to compare elements).
	 */
	public void sort(List<E> list, Comparator<E> comp)
	{
		List<E> tempList = new ArrayList<E>(list); // cannot pass incoming param
													// list to mergeSort
		mergeSort(list, tempList, 0, list.size() - 1, comp);
	}

	/**
	 * Helper method that recursively implements MergeSort.
	 * 
	 * @param list  is the list to sort.
	 * @param temp  is the temporary list to populate and sort (then merge).
	 * @param left  is the starting index of the sublist.
	 * @param right is the ending index of the sublist.
	 * @param comp  is the comparator for comparing items in the list.
	 */
	private void mergeSort(List<E> list, List<E> temp, int left, int right,
			Comparator<E> comp)
	{
		if (left == right)
		{ // List has one record
			return;
		}
		int mid = (left + right) / 2; // Select midpoint

		mergeSort(list, temp, left, mid, comp); // Mergesort first half
		mergeSort(list, temp, mid + 1, right, comp); // Mergesort second half

		for (int i = left; i <= right; i++)
		{ // Copy sublist to temp
			temp.set(i, list.get(i));
		}
		int i1 = left;
		int i2 = mid + 1;

		for (int curr = left; curr <= right; curr++)
		{
			if (i1 == mid + 1)
			{ // Left sublist exhausted
				list.set(curr, temp.get(i2++));
			} else if (i2 > right)
			{ // Right sublist exhausted
				list.set(curr, temp.get(i1++));
			} else if (comp.compare(temp.get(i1), temp.get(i2)) <= 0)
			{ // Get smaller value
				list.set(curr, temp.get(i1++));
			} else
			{
				list.set(curr, temp.get(i2++));
			}
		}
	}
}
