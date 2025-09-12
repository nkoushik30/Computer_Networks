import java.util.Scanner;

public class Character_Stuffing {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input Section
        System.out.print("Enter the data to be stuffed: ");
        String input = sc.nextLine();

        System.out.print("Enter the starting delimiter character: ");
        char startDelim = sc.next().charAt(0);

        System.out.print("Enter the ending delimiter character: ");
        char endDelim = sc.next().charAt(0);

        // Prepare doubled delimiters
        String doubleStart = "" + startDelim + startDelim;
        String doubleEnd = "" + endDelim + endDelim;

        // Start framing with starting delimiter
        StringBuilder stuffed = new StringBuilder();
        stuffed.append(doubleStart);

        // Stuffing logic
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == startDelim) {
                stuffed.append(doubleStart); // Stuff start delimiter
            } else if (ch == endDelim) {
                stuffed.append(doubleEnd); // Stuff end delimiter
            } else {
                stuffed.append(ch);
            }
        }

        // Add ending delimiter
        stuffed.append(doubleEnd);

        // Output
        System.out.println("Data after character stuffing: " + stuffed.toString());

        sc.close();
    }
}
