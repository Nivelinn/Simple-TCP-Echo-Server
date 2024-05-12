import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TcpEchoServer {

    private static final int PORT = 7773;


    public static void main(String[] args) throws IOException {

        // start TCP server. Might throw exceptions: security, bind
        ServerSocket serverSocket = new ServerSocket(PORT);

        // create a map to hold the list of current and active connections and the number of messages sent on each of them
        Map<String, Integer> connectionList;
        connectionList = new ConcurrentHashMap<>();
        connectionList.clear();

        while (true) {
            // wait for connection request
            System.out.println("waiting for a request...");
            Socket connectionSocket = serverSocket.accept();

            // start a handling thread for just connected client
            System.out.println("connection received from: " +
                    connectionSocket.getInetAddress() + ":" + connectionSocket.getPort());
            ConnectionHandler ch =
                    new ConnectionHandler(connectionSocket, connectionList);
            (new Thread(ch)).start();

        }

    }
}