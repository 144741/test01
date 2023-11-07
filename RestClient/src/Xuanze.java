import java.util.Arrays;

public class Xuanze {
    public static void main(String[] args){
        int[] arr = {1,5,3,2,7,9};
        int minIndex = 0;
        for(int i=0;i<arr.length-1;i++){
            minIndex = i;
            for (int j = i+1;j<arr.length;j++){
                if(arr[j]<arr[minIndex]){
                    minIndex =j;
                }
            }
            int temp = 0;
            if (minIndex != i){
                temp = arr[i];
                arr[i] =arr[minIndex];
                arr[minIndex] =temp;
            }

        }
        System.out.println(Arrays.toString(arr));
    }
}
