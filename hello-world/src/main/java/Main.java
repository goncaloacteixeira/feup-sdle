import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Main {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // Socket to talk to clients
            ZMQ.Socket socket = context.createSocket(SocketType.REP);
            socket.bind("tcp://localhost:5555");

            while (!Thread.currentThread().isInterrupted()) {
                // Block until a message is received
                byte[] reply = socket.recv(0);

                String replyStr = new String(reply, ZMQ.CHARSET);

                // Print the message
                System.out.println("Received: [" + replyStr + "]");

                // Send a response
                String response = "Hello! Received: " + replyStr;
                socket.send(response.getBytes(ZMQ.CHARSET), 0);
            }
        }
    }
}
