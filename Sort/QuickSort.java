package Sort;

import java.util.Arrays;

public class QuickSort {
    public static void quickSort(int[] arr,int left,int right){
        if(left>=right)
            return;
        int begin = left;
        int end = right;
        int temp = arr[left];
        while (left<right){
            while (left<right){
                if(arr[right]<arr[left]){
                    arr[left] = arr[right];
                    break;
                }else {
                    right--;
                }
            }
            while (left<right){
                if(arr[left]>arr[right]){
                    arr[right] = arr[left];
                    break;
                }else{
                    left++;
                }
            }
        }
        arr[left] = temp;
        quickSort(arr,begin,right-1);
        quickSort(arr,right+1,end);
    }
    public static void Solotion(int[] arr){
        quickSort(arr,0,arr.length-1);
    }

    public static void main(String[] args) {
        int[] arr = {2,3,4,1,5,3};
        Solotion(arr);
        System.out.println(Arrays.toString(arr));
    }
}
