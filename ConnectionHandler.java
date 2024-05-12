import java.net.*;
import java.io.*;
import java.util.Map;

public class ConnectionHandler implements Runnable {
    private final Socket socket;
    private final Map<String, Integer> connectionList;
    private String connectionId;


    public ConnectionHandler(Socket socket, Map<String, Integer> connectionList) {
        this.socket = socket;
        this.connectionList = connectionList;
    }


    public void run() {

        DataOutputStream outputStream = null;
        BufferedReader inputStream = null;

        // form a unique ID of newly created connection, add it to the list and reset message number
        connectionId = formConnectionId(socket);
        connectionList.put(connectionId, Integer.valueOf(0));

        try {

            // get I/O streams
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new DataOutputStream(socket.getOutputStream());

            // process connection
            while (true) {
                String line = inputStream.readLine();
                if (line == null) {
                    // connection is closed by client, termination will be done in expeditions handling
                    continue;
                }
                System.out.println("Data from client: " + line);

// get this connection current message number and increase it
                Integer messageNumber = connectionList.get(connectionId);
                if (messageNumber == null) {
                    // this should not happen, might be only some terrible runtime error
                    throw new Exception("Runtime Error");
                }
                messageNumber++;
                connectionList.put(connectionId, messageNumber);

// if 'list' command is sent, send back connection statistics, otherwise: echo
                String lineToSent = line.trim().equals("list") ? getStats(connectionList) : line;
                outputStream.writeBytes(lineToSent + "\n");
                outputStream.flush();
                System.out.println("Data to client: " + lineToSent);
            }

        } catch (Exception e) {
            // remove this connection from the list
            connectionList.remove(connectionId);
            System.out.println("Client: disconnected");
        } finally {

            try {
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

    /**
     * Forms unique connection ID
     *
     * @param socket of connection
     * @return unique ID
     */
    private String formConnectionId(final Socket socket) {
        StringBuffer key = new StringBuffer();
        key.append(socket.getInetAddress());
        key.append(":");
        key.append(socket.getPort());
        return key.toString();
    }

    /**
     * Gets connections statistics, their number and the number of messages send by each
     *
     * @param connectionList the list of all current connections
     * @return statistics data
     */
    private String getStats(final Map<String, Integer> connectionList) {
        StringBuffer data = new StringBuffer();
        data.append("Current connections number: ");
        data.append(connectionList.size());
        data.append(" / ");
        data.append("Current number of messages for each connection: ");
        data.append(connectionList.values());
        return data.toString();
    }
}