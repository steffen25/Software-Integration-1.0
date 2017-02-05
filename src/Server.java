import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Steffen on 05/01/16.
 */
public class Server extends Thread {

    private ServerSocket serverSocket;

    private static int PORT = 8888;

    private DataInputStream inFromClient;

    private DataOutputStream outToClient;

    private int balance = 10000;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run()
    {

        while (true)
        {
            System.out.println("Waiting for client on port " +serverSocket.getLocalPort());

            try {
                Socket server = serverSocket.accept();
                // Client has connected
                System.out.println("[SERVER]: A new client has connected with IP: " +server.getRemoteSocketAddress());

                outToClient = new DataOutputStream(server.getOutputStream());
                outToClient.writeUTF("[SERVER]: Your balance is, " + balance + " dkk");

                inFromClient = new DataInputStream(server.getInputStream());

                // The first argument we receive is the amount the client want to transfer.
                String amountToTransfer = inFromClient.readUTF();

                try {
                    int amount = Integer.parseInt(amountToTransfer);
                    if (!updateBalance(amount)) {
                        outToClient.writeUTF("[SERVER]: Something went wrong...");
                        outToClient.flush();
                        server.close();
                    }
                    outToClient.writeUTF("[SERVER]: You have successfully transferred, " + amountToTransfer + " dkk" + "\n" +
                            "[SERVER]: Your new balance is, " +balance + " dkk");
                } catch (NumberFormatException e) {
                    outToClient.writeUTF("[SERVER]: Invalid number...");
                    outToClient.flush();
                    serverSocket.close();
                }

                // Print out what we receive from the client
                outToClient.writeUTF("[SERVER]: The bank is now closed.");
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateBalance(int amountToTransfer) {
        if (amountToTransfer <= balance) {
            balance -= amountToTransfer;
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            Thread thread = new Server(PORT);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
