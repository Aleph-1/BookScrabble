package Model.MileStone3.test;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
	void handleClient(InputStream inFromclient, OutputStream outToClient);
	void close();
}
