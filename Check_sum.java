import java.util.Scanner;

public class Check_sum {

    public static byte calculateChecksum(String data) {
        int sum = 0;
        for (int i = 0; i < data.length(); i++) {
            sum += (byte) data.charAt(i);
        }
        return (byte) (sum % 256);
    }

    public static byte[] sendFrame(String data) {
        byte checksum = calculateChecksum(data);
        byte SOF = 0x7E;
        byte EOF = 0x7F;

        byte[] dataBytes = data.getBytes();
        byte[] frame = new byte[dataBytes.length + 3];

        frame[0] = SOF;
        System.arraycopy(dataBytes, 0, frame, 1, dataBytes.length);
        frame[1 + dataBytes.length] = checksum;
        frame[2 + dataBytes.length] = EOF;

        return frame;
    }

    public static boolean receiveFrame(byte[] frame, StringBuilder outData) {
        byte SOF = 0x7E;
        byte EOF = 0x7F;

        if (frame[0] != SOF || frame[frame.length - 1] != EOF) {
            System.out.println("Frame error: Invalid framing bytes.");
            return false;
        }

        int dataLen = frame.length - 3;
        byte[] dataBytes = new byte[dataLen];
        System.arraycopy(frame, 1, dataBytes, 0, dataLen);

        String data = new String(dataBytes);
        outData.append(data);

        byte receivedChecksum = frame[1 + dataLen];
        byte calcChecksum = calculateChecksum(data);

        if (receivedChecksum != calcChecksum) {
            System.out.println("Checksum error!");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter data to send: ");
        String data = sc.nextLine();

        byte[] frame = sendFrame(data);

        System.out.print("Transmitted Frame (in hex): ");
        for (byte b : frame) {
            System.out.printf("%02X ", b);
        }
        System.out.println();

        StringBuilder receivedData = new StringBuilder();
        if (receiveFrame(frame, receivedData)) {
            System.out.println("Received data: " + receivedData);
            System.out.println("No error detected in frame.");
        }

        sc.close();
    }
}
