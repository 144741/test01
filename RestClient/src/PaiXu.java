import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class PaiXu {
    public static void main(String[] args) {
        int[] nums = {1, 0, 2, 1, 2, 1, 0};
        sortBycolor(nums);
        System.out.println(Arrays.toString(nums));
    }
    private static void sortBycolor(int[] nums) {
        int length = nums.length;
        int p = 0;
        for (int i = 0; i < length; ++i) {
            if (nums[i] == 0) {
                int temp = nums[i];
                nums[i] = nums[p];
                nums[p] = temp;
                ++p;
            }
        }
        for (int i = p; i < length; ++i) {
            if (nums[i] == 1) {
                int temp = nums[i];
                nums[i] = nums[p];
                nums[p] = temp;
                ++p;
            }
        }
    }


    }

