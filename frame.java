import java.util.Scanner;
public class frame {
    public static byte calculateChecksum(String data) {
        int sum = 0;
        for (byte b : data.getBytes()) sum += (b & 0xFF);
        return (byte)(sum & 0xFF);
    }
    public static int sendFrame(String data, byte[] frame) {
        byte[] dataBytes = data.getBytes();
        int data_len = dataBytes.length;
        frame[0] = (byte) 0x7E;
        System.arraycopy(dataBytes, 0, frame, 1, data_len);
        frame[1 + data_len] = calculateChecksum(data);
        frame[2 + data_len] = (byte) 0x7F;
        return 3 + data_len;
    }
    public static String receiveFrame(byte[] frame, int frame_len) {
        if (frame[0] != (byte) 0x7E || frame[frame_len - 1] != (byte) 0x7F) {
            System.out.println("Frame error: Invalid framing bytes."); 
            return null;
        }
        int data_len = frame_len - 3;
        String out_data = new String(frame, 1, data_len);
        if (frame[1 + data_len] != calculateChecksum(out_data)) {
            System.out.println("Checksum error!"); return null;
        }
        return out_data;
    }
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); byte[] frame = new byte[110];
        System.out.print("Enter data to send: ");
        String data = sc.next(); sc.close();
        int frame_len = sendFrame(data, frame);
        System.out.print("Transmitted Frame (in hex): ");
        for (int i = 0; i < frame_len; i++) System.out.printf("%02X ", frame[i] & 0xFF);
        System.out.println("\n");
        String received_data = receiveFrame(frame, frame_len);
        if (received_data != null) System.out.println("Received data: " + received_data + "\nNo error detected in frame.");
    }
}
