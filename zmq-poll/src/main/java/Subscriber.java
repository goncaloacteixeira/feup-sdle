import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Subscriber {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            //  Socket to talk to server
            System.out.println("Collecting updates from weather server");
            ZMQ.Socket subscriberUS = context.createSocket(SocketType.SUB);
            ZMQ.Socket subscriberPT = context.createSocket(SocketType.SUB);
            subscriberUS.connect("tcp://localhost:5555");
            subscriberPT.connect("tcp://localhost:5556");

            //  Subscribe to zipcode, default is NYC, 10001
            String filterUS = (args.length > 0) ? args[0] : "10001 ";
            subscriberUS.subscribe(filterUS.getBytes(ZMQ.CHARSET));

            String filterPT = (args.length > 0) ? args[0] : "1001 ";
            subscriberPT.subscribe(filterPT.getBytes(ZMQ.CHARSET));

            ZMQ.Poller poller = context.createPoller(2);
            poller.register(subscriberUS, ZMQ.Poller.POLLIN);
            poller.register(subscriberPT, ZMQ.Poller.POLLIN);

            while (!Thread.currentThread().isInterrupted()) {
                poller.poll();
                if (poller.pollin(0)) {
                    String messageUS = subscriberUS.recvStr();
                    System.out.println("US Temperature: " + messageUS);
                }
                if (poller.pollin(1)) {
                    String messagePT = subscriberPT.recvStr();
                    System.out.println("PT Temperature: " + messagePT);
                }
            }
        }
    }
}
