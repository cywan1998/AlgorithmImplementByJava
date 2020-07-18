package LeetCode;

import java.util.Arrays;

public class SortPractice {
    static class QuickSort {
        static void patition(int[] nums, int begin, int end) {
            if (begin < end) {
                int tar = nums[begin];
                int left = begin;
                int right = end;
                while (left < right) {
                    while (left < right) {
                        if (nums[right] >= tar) {
                            right--;
                        } else {
                            nums[left] = nums[right];
                            break;
                        }
                    }
//                    left++;
                    while (left < right) {
                        if (nums[left] < tar) {
                            left++;
                        } else {
                            nums[right] = nums[left];
                            break;
                        }
                    }
//                    right--;
                }
                nums[left] = tar;
                patition(nums, begin, left - 1);
                patition(nums, left + 1, end);
            }

        }
        public static void sort(int[] nums) {
            patition(nums, 0, nums.length - 1);
        }
    }

    static class HeapSort {
        static void bottomToUp(int[] nums, int index) {
            int father;
            while (index > 0) {
                father = (index - 1) / 2;
                if (father >= 0 && nums[father] < nums[index]) {
                    int temp = nums[father];
                    nums[father] = nums[index];
                    nums[index] = temp;
                    index = father;
                } else {
                    break;
                }
            }
        }
        static void buildHeap(int[] nums) {
            for (int i = nums.length - 1; i >= 0; i--) {
                bottomToUp(nums, i);
            }
        }

        static void rebuildHeap(int[] nums, int end) {
            int index = 0;
            int child = 2 * index + 1;
            //第一个元素下沉
            while (child < end) {
                if (child + 1 < end && nums[child + 1] > nums[child]) {
                    child++;
                }
                if (nums[child] > nums[index]) {
                    int temp = nums[child];
                    nums[child] = nums[index];
                    nums[index] = temp;
                    index = child;
                    child = 2 * index + 1;
                } else {
                    break;
                }
            }
        }
        static void sort(int[] nums) {
            buildHeap(nums);
            int len = nums.length;
            for (int i = 0; i < nums.length; i++) {
                //与末尾交换
                int temp = nums[len - i - 1];
                nums[len - i - 1] = nums[0];
                nums[0] = temp;
                rebuildHeap(nums, len - i - 1);
            }
        }
    }

    // 归并排序, 拆分合并的过程,
    static class MergeSort{
        static void mergeSort(int[] nums, int left, int right) {
            if (left >= right) {
                return;
            }
            int mid = (left + right) / 2;
            //排序左部分
            mergeSort(nums, left, mid);
            //排序右部分
            mergeSort(nums, mid + 1, right);
            //合并
            merge(nums, left, mid, mid + 1, right);
        }

        // 合并两个有序数组, System.arraycopy()用法
        static void merge(int[] nums, int leftBegin, int leftEnd, int rightBegin, int rightEnd) {
            int begin = leftBegin;
            int[] newNums = new int[leftEnd - leftBegin + rightEnd - rightBegin + 2];
            int index = 0;
            while (leftBegin <= leftEnd && rightBegin <= rightEnd) {
                if (nums[leftBegin] < nums[rightBegin]) {
                    newNums[index] = nums[leftBegin++];
                } else {
                    newNums[index] = nums[rightBegin++];
                }
                index++;
            }
            while (leftBegin <= leftEnd) {
                newNums[index++] = nums[leftBegin++];
            }
            while ((rightBegin <= rightEnd)) {
                newNums[index++] = nums[rightBegin++];
            }
            /**
             * args1: 源数组
             * args2: 起点
             * args3: 目的数组
             * args4: 复制长度.
             */
            System.arraycopy(newNums, 0, nums, begin, newNums.length);

        }
    }
    public static void main(String[] args) {
        int[] nums = new int[]{-1, 2, 1, 3, 1, 9, 7};
//        QuickSort.sort(nums);
//        HeapSort.sort(nums);
        MergeSort.mergeSort(nums, 0, nums.length - 1);

        System.out.println(Arrays.toString(nums));
    }
}
