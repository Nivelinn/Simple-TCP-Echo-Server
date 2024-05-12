import java.io.*;
import java.net.*;

public class TcpEchoClient {

    private static final int PORT = 7773;
    private static Socket socket = null;
    private static DataOutputStream outputStream;
    private static BufferedReader inputStream;


    public static void main(String[] args) throws IOException {
        // connect to the server
        SocketAddress remote = new InetSocketAddress(args[0], PORT);
        socket = new Socket();
        socket.connect(remote, 10000);

        // get I/O streams
        System.out.println("Connected to: " +
                socket.getInetAddress());

        inputStream =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        outputStream =
                new DataOutputStream(socket.getOutputStream());

        // create & start client handler
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }

    static class ClientThread extends Thread {

        public void run() {

            BufferedReader lineStream = null;

            try {


                // get system input stream
                lineStream =
                        new BufferedReader(
                                (new InputStreamReader(System.in)));

                // process the connection
                while (true) {
                    // read system input
                    String outLine = lineStream.readLine();

                    // write input to the server
                    outputStream.writeBytes(outLine + "\n");

                    // wait response
                    String inLine = inputStream.readLine();
                    System.out.println("Data from server: " +
                            inLine);

                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {

                try {
                    if (lineStream != null) {
                        lineStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }


    /**
     * Write data to the server and wait for response.
     * Used in load and function tests of the server
     *
     * @param message to be sent
     */
    public static void transmit(String message) {

        try {
            // write input to the server
            outputStream.writeBytes(message + "\n");
            // wait response
            String inLine = inputStream.readLine();
            System.out.println("Data from server: " +
                    inLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}