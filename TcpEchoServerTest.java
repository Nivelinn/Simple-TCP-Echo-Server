import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class TcpEchoServerTest {

    private static final String SERVER_NAME = "localhost";
    private static final String MESSAGE = "list";
    private static final int CLIENT_NUMBER = 10;
    private static final int CREATE_TIMEOUT = 1000;

    @BeforeEach
    void setUp() {
        System.out.println("unit test setup");
    }

    @AfterEach
    void tearDown() {
        System.out.println("unit test done");
    }

    @Test
    void main() {
        String[] args = new String[1];
        args[0] = SERVER_NAME;
        System.out.println("start unit test test");

        for (int clients = 0; clients < CLIENT_NUMBER; clients++)
            try {
                TcpEchoClient.main(args);
                TcpEchoClient.transmit(MESSAGE);
                Thread.sleep(CREATE_TIMEOUT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        while (true) {

        }
    }
}