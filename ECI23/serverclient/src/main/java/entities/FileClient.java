import java.net.*;
import java.io.*;
import jatyc.lib.Typestate;

@Typestate("FileClient")
public class FileClient {
  private Socket socket;
  protected OutputStream out;
  protected BufferedReader in;
  protected int lastByte;

  public boolean start() {
    try {
      socket = new Socket("localhost", 1234);
      out = socket.getOutputStream(); // to write
      in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // to read
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public void request(String filename) throws Exception {
    out.write("REQUEST\n".getBytes());
    out.write((filename + "\n").getBytes());
  }

  public int nextByte() throws Exception {
    this.lastByte = in.read();
    return lastByte;
  }

  public boolean hasNext() {
    return this.lastByte != 0;
  }

  public void close() throws Exception {
    out.write("CLOSE\n".getBytes());
    in.close();
    out.close();
    socket.close();
  }

  public static void main(String[] args) throws Exception {
    FileClient client = new FileClient();
    if (client.start()) {
      System.out.println("File client started!");
      // FileServerThread serverThread = new FileServerThread(client.socket);
      // serverThread.run();
      // TODO
      client.close();
    } else {
      System.out.println("Could not start client!");
    }
  }
}
