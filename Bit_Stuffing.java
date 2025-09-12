import java.util.Scanner;

public class Bit_Stuffing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input
        System.out.print("Enter the bit stream (only 0s and 1s): ");
        String input = sc.nextLine();

        StringBuilder stuffed = new StringBuilder();
        int count = 0;

        // Bit stuffing logic
        for (int i = 0; i < input.length(); i++) {
            char bit = input.charAt(i);
            stuffed.append(bit);

            if (bit == '1') {
                count++;
                if (count == 5) {
                    stuffed.append('0');
                    count = 0;
                }
            } else {
                count = 0;
            }
        }

        System.out.println("Bit-stuffed output: " + stuffed);

        sc.close();
    }
}
