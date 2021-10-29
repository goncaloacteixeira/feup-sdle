import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Subscriber {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            System.out.println("Connecting to publisher");

            ZMQ.Socket socket = context.createSocket(SocketType.SUB);
            socket.connect("tcp://localhost:5555");
            socket.subscribe("updates");

            for (int requestNbr = 0; requestNbr != 10; requestNbr++) {
                socket.recv(0); // discard the topic
                byte[] update = socket.recv(0);
                System.out.println(
                        "Received " + new String(update, ZMQ.CHARSET) + " " +
                                requestNbr
                );
            }
        }
    }
}
