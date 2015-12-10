package search;

//二分查找
public class BinSearch {
	
	//非递归
	private int search(int[] array, int pos) {
		int low = 0;
		int high = array.length -1;
		while (low <= high) {
			int middle = (low + high)/2;
			if (array[middle] == pos) {
				return 1;
			} else if (array[middle] < pos) {
				low = middle + 1;
			} else if (array[middle] > pos) {
				high = middle -1;
			}
		}
		return -1;
	}
	
	//递归
	private int search(int[] array, int pos, int low, int high) {
		int middle = (low + high)/2;
		if (array[middle] == pos) {
			return 1;
		} else if (low <= high && array[middle] > pos) {
			return search(array, pos, low, middle-1);
		} else if (low <= high && array[middle] < pos) {
			return search(array, pos, middle+1, high);
		} else {
			return -1;
		} 
	}
	public static void main(String[] args) {
		int[] array = new int[]{1,3,5,7,9,12,14,19};
//		System.out.println(new BinSearch().search(array, 7));
		System.out.println(new BinSearch().search(array, -1, 0, array.length));
	}
}
