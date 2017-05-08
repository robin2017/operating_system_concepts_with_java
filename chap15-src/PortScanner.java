import java.net.*;

public class PortScanner
{
	public static final int PORT_MAX = 1024; // the maximum port we will scan to
	public static final int TIMEOUT_VALUE = 1000; // 1 second

	public static void main(String[] args) throws java.net.UnknownHostException {
		if (args.length != 1) {
			System.err.println("Usage: java PortScanner [host]");
			System.exit(0);
		}

		InetAddress host = InetAddress.getByName(args[0]);

		for (int port = 0; port <= PORT_MAX; port++) {
			try {
				SocketAddress addr  = new InetSocketAddress(host,port);
				Socket sock = new Socket();

				// attempt to make a socket address to (host + port)
				// with a timeout value of 1 second
				sock.connect(addr,TIMEOUT_VALUE);

				System.out.println("Listening at port " + port);

				// we could now try to exploit this service ......
			}
			catch (java.io.IOException ioe) { /* not listening to this port */ }
		}
	}
}
