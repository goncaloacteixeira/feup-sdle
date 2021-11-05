import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Random;

public class PublisherUS {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);
            publisher.bind("tcp://*:5555");
            publisher.bind("ipc://weatherUS");

            // number generator
            Random random = new Random(System.currentTimeMillis());
            while (!Thread.currentThread().isInterrupted()) {
                int zipcode, temp, hum;
                zipcode = 10000 + random.nextInt(10000);
                temp = random.nextInt(215) - 80 + 1;
                hum = random.nextInt(50) + 10 + 1;

                //  Send message to all subscribers
                String update = String.format(
                        "%05d %d %d", zipcode, temp, hum
                );
                publisher.send(update, 0);
            }
        }
    }
}

