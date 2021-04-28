package cli;

import java.util.Scanner;

public class InputReader {

    public static int readIntFromStdin(String text) {
        Scanner in = new Scanner(System.in);
        int x = -1;
        boolean isNumber;

        do {
            System.out.println(text);
            if (in.hasNextInt()){
                x = in.nextInt();
                isNumber = true;
            }else {
                System.out.println("please enter a number");
                isNumber = false;
                in.next();
            }
        } while (!(isNumber));
        return x;
    }

    public static String readStringfromStdIn(String text) {
        Scanner in = new Scanner(System.in);
        System.out.println(text);
        String readString = in.nextLine();
        return readString;
    }
}
