import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(ZMQ.REQ);
            socket.connect("tcp://localhost:5555");

            String message;

            do {
                Scanner scanner = new Scanner(System.in);
                System.out.print("New Message: ");

                message = scanner.nextLine();
                socket.send(message.getBytes(ZMQ.CHARSET), 0);
                byte[] reply = socket.recv(0);
                System.out.println(
                        "Received: [" + new String(reply, ZMQ.CHARSET) + "]"
                );
            } while (!message.equals("DONE"));

            socket.close();
        }
    }
}
