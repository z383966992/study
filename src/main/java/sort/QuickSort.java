package sort;

//设要排序的数组是A[0]……A[N-1]，
//首先任意选取一个数据（通常选用数组的第一个数）作为关键数据，
//然后将所有比它小的数都放到它前面，所有比它大的数都放到它后面，
//这个过程称为一趟快速排序。值得注意的是，快速排序不是一种稳定的排序算法，
//也就是说，多个相同的值的相对位置也许会在算法结束时产生变动。
public class QuickSort {

	public int[] sort(int[] array, int start, int end) {
		int key = array[start];
		int i = start; 
		int j = end;
		
		while (i < j) {
			while(i < j && array[j] >= key) {
				j--;
			}
			
			if(i < j) {
				array[i] = array[j];
				i++;
			}
			
			while(i < j && array[i] <= key) {
				 i++;
			}
			
			if(i < j) {
				array[j] = array[i];
				j--;
			}
		}
		
		array[i] = key;
		
		if((i-1) > start) this.sort(array, start, i-1);
		if((j+1) < end)this.sort(array, j+1, end);
		return array;
	}
	
	public static void main(String[] args) {
		QuickSort sort = new QuickSort();
		int[] array = new int[]{6,2,7,3,8,9,5,7,12,56,52,15,35,79,56,32,15,68,49,86,15,35,16,32,64,95,78,100};
		array = sort.sort(array, 0, array.length-1);
		for(int i=0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
		
	}
	
}
