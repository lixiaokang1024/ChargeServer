package com.charge.controller.school;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArraySort {

  public static void main(String[] args) {
    int[] arry = new int[10];
    for(int i=0;i<arry.length;i++){
      arry[i] = (int) (Math.random()*10);
    }
    System.out.println(Arrays.toString(arry));
    // System.out.println(Arrays.toString(bubbingSort(arry)));
    // System.out.println(Arrays.toString(reverse(arry)));
    // quickSort(arry, 0, arry.length - 1);
    // move(arry, 3);
    // System.out.println(Arrays.toString(arry));
    System.out.println(Arrays.toString(move2(arry, 3)));
  }

  /**
   * 冒泡排序
   * @param array
   * @return
   */
  public static int[] bubbingSort(int[] array){
    for(int i=0;i<array.length-1;i++){
      for(int j=0;j<array.length-1-i;j++){
        if(array[j] > array[j+1]){
          int k = array[j];
          array[j] = array[j+1];
          array[j+1] = k;
        }
      }
    }
    return array;
  }

  public static void quickSort(int[] arry, int left, int right){
    if(left >= right){
      return;
    }
    int key = arry[right];
    int i = left;
    int j = right;
    while(i < j){

      while(arry[i] <= key && i < j){
        i++;
      }

      while (arry[j] >= key && i < j){
        j--;
      }

      if(i < j){
        int temp = arry[i];
        arry[i] = arry[j];
        arry[j] = temp;
      }
    }
    arry[right] = arry[i];
    arry[i] = key;
    quickSort(arry, 0, i-1);
    quickSort(arry, i+1, right);
  }
  /**
   * 数组反转
   */
  public static int[] reverse(int[] arry){
    for(int i=0;i<arry.length/2;i++){
      int k = arry[i];
      arry[i] = arry[arry.length - 1 - i];
      arry[arry.length - 1 - i] = k;
    }
    return arry;
  }

  public static void move(int[] arry, int k){
    for(int j=0;j<k;j++){
      int temp = arry[arry.length - 1];
      for(int i=0;i<arry.length;i++){
        int pre = arry[i];
        arry[i] = temp;
        temp = pre;
      }
    }
  }

  public static int[] move2(int[] arry, int k){
    int[] newArray = new int[arry.length];
    for(int i=0;i<arry.length;i++){
      if(i < k){
        newArray[i] = arry[arry.length - k + i];
      }else{
        newArray[i] = arry[i - k];
      }
    }
    return newArray;
  }
}
