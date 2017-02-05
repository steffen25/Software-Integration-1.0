import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Steffen on 05/02/2017.
 */
public class Client {

    private static DataOutputStream outToServer;

    private static DataInputStream inFromServer;

    private static String IP = "192.168.0.11";

    private static int PORT = 8888;

    public static void main(String[] args) {
        try {

            System.out.println("Connecting to " +IP +" on port " +PORT );
            Socket client = new Socket(IP, PORT);

            OutputStream out = client.getOutputStream();
            outToServer = new DataOutputStream(out);

            InputStream in = client.getInputStream();
            inFromServer = new DataInputStream(in);

            System.out.println(inFromServer.readUTF());

            Scanner reader = new Scanner(System.in);

            System.out.println("Enter the amount you would like to transfer (no decimal numbers):");

            String amountToTransfer = reader.nextLine();

            // Send amount to transfer to the server
            outToServer.writeUTF(amountToTransfer);

            // Once we sent the amount we expect some kind of response back telling us if we succeed or failed.
            System.out.println(inFromServer.readUTF());

            System.out.println("[CLIENT]: Closing socket...");

            client.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
