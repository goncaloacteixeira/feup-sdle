import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;

public class PublisherPT {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
            publisher.bind("tcp://*:5556");
            publisher.bind("ipc://weatherPT");

            // number generator
            Random random = new Random(System.currentTimeMillis());
            while (!Thread.currentThread().isInterrupted()) {
                int zipcode, temp, hum;
                zipcode = 1000 + random.nextInt(8999);
                temp = random.nextInt(215) - 80 + 1;
                hum = random.nextInt(50) + 10 + 1;

                String update = String.format(
                        "%04d %d %d", zipcode, temp, hum
                );
                publisher.send(update, 0);
            }
        }
    }
}
