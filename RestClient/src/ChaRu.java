import java.util.Arrays;

public class ChaRu {
    public static void main(String[] args){
        int[] arr = {1,5,3,2,7,9};
        for(int i=1; i<arr.length; i++) { //假设第一个数位置时正确的；要往后移，必须要假设第一个。

            int j = i;
            int target = arr[i]; //待插入的

            //后移
            while(j > 0 && target < arr[j-1]) {
                arr[j] = arr[j-1];
                j --;
            }

            //插入
            arr[j] = target;
        }
        System.out.println(Arrays.toString(arr));
    }
}
