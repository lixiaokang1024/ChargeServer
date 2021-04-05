package com.charge.controller.school;

public class BinarySearch {

  public static void main(String[] args) {
    int[] arry = new int[10];
    for(int i=0;i<arry.length;i++){
      arry[i] = i;
    }
    System.out.println(binaryDgSearch(arry, 5, 0, arry.length - 1));
  }
  public static int binarySearch(int[] array, int meta){
    int from = 0;
    int to = array.length - 1;
    while (from <= to) {
      int mid = (from + to) / 2;
      if(array[mid] == meta){
        return mid;
      }
      if(array[mid] > meta){
        to = mid - 1;
      }else if(array[mid] < meta){
        from = mid + 1;
      }
    }
    return -1;
  }

  public static int binaryDgSearch(int[] array, int meta, int from, int to){
    if(from > to){
      return -1;
    }
    int mid = (from + to) / 2;
    if(array[mid] == meta){
      return mid;
    }
    if(array[mid] > meta){
      to = mid - 1;
    }else{
      from = mid + 1;
    }
    return binaryDgSearch(array, meta, from, to);
  }
}
