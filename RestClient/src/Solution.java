import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String num1 = String.valueOf(scanner.next());
//        String num1 = String.valueOf(scanner.nextInt());
        String num2 = String.valueOf(scanner.next());
        if (num1.length()>110 || num2.length()>110){
            return;
        }

        int m = Integer.valueOf(parseInt(num1)).intValue();
        int n = Integer.valueOf(parseInt(num2)).intValue();

        System.out.println(m + n);


    }

//    public static int multiply(String num1, String num2) {
//
////            String num1 = "123";
////            String num2 = "123";
//        int m = Integer.valueOf(num1).intValue();
//        int n = Integer.valueOf(num2).intValue();
//        int i = m + n;
//        return i;
//    }
}

