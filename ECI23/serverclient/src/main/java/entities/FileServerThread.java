import java.net.*;

public class FileServerThread extends Thread {
  private Socket socket;

  public FileServerThread(Socket socket) {
    this.socket = socket;
  }

  public void run(String path_name) {
    try {
      FileServer server = new FileServer();
      if (server.start(socket)) {
        System.out.println("File server started!");
        
        while (server.hasRequest()) { server.sendFile(); }
        
        server.close();
      } else {
        System.out.println("Could not start server!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
