/**
 * Time-of-day server listening to port 6013.
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DateServerMultiThread
{
	public static void main(String[] args) throws IOException {
		//Socket client = null;
		ServerSocket sock = null;

		try {
			sock = new ServerSocket(6013);
			// now listen for connections
			while (true) {


  				new Thread(new Connection(sock.accept())).start();

			}
		}
		catch (IOException ioe) {
				System.err.println(ioe);
		}
		finally {
			if (sock != null)
				sock.close();

		}
	}
}

 class Connection implements Runnable
{
	private Socket	outputLine;

	public Connection(Socket s) {
		outputLine = s;
	}

	public void run() {
		// getOutputStream returns an OutputStream object
		// allowing ordinary file IO over the socket.
		try {
			// create a new PrintWriter with automatic flushing
			PrintWriter pout = new PrintWriter(outputLine.getOutputStream(), true);
			// now send the current date to the client
			pout.println(new java.util.Date() );
			// now close the socket
			outputLine.close();
		}
		catch (java.io.IOException e) {
			System.out.println(e);
		}
	}
}
