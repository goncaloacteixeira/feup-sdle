import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Publisher {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket socket = context.createSocket(SocketType.PUB);

            socket.bind("tcp://localhost:5555");

            while (!Thread.currentThread().isInterrupted()) {
                String update = "Server listening on: localhost:5555";
                System.out.println("Sending update: " + update);
                socket.sendMore("updates");
                socket.send(update.getBytes(ZMQ.CHARSET), 0);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
