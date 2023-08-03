import java.net.*;
import java.io.*;
import jatyc.lib.Typestate;

@Typestate("FileServer")
public class FileServer {
  private Socket socket;
  protected OutputStream out;
  protected BufferedReader in;
  protected String lastFilename;

  public boolean start(Socket s) {
    try {
      socket = s;
      out = socket.getOutputStream(); // to write
      in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // to read
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean hasRequest() throws Exception {
    String command = in.readLine();
    if (command != null && command.equals("REQUEST")) {
      // TODO
      lastFilename = in.readLine();
      try {
        file = new FileReader(filename);
        curr = file.read();
        return Status.OK;
      } catch (IOException exp) {
        return Status.ERROR;
      }
      
      return true;
    }
    return false;
  }

  // TODO

  public boolean sendFile() {

  }

  public void close() throws Exception {
    in.close();
    out.close();
    socket.close();
    // TODO
  }

  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(1234);
    while (true) {
      new FileServerThread(serverSocket.accept()).start();
    }
  }
}
