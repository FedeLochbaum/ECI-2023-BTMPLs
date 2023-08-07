import jatyc.lib.Typestate;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Typestate("FileServer")
public class FileServer {
  private Socket socket;
  protected OutputStream out;
  protected BufferedReader in;
  protected String lastFilename;
  protected String lastCommand;

  public boolean start(Socket s) {
    try {
      socket = s;
      out = socket.getOutputStream();
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean hasRequest() throws Exception {
    String command = in.readLine();
    if (command != null) {
      this.lastCommand = command;
      this.lastFilename = in.readLine();
    }
    return command != null && command.equals("REQUEST");
  }

  public boolean isClosed() {
    return this.lastCommand != null && this.lastCommand.equals("CLOSE");
  }
  
  public void sendFile() throws Exception {

    File path = new File(this.lastFilename);
    try (FileInputStream fileInputStream = new FileInputStream(path)) {
      int byteRead;
      while ((byteRead = fileInputStream.read()) != -1) {
        out.write(byteRead);
      }
    } catch (FileNotFoundException e) {
      out.write(0); // file not found
    }

    out.write(0); // end of file
    out.flush();
  }

  public void close() throws Exception {
    in.close();
    out.close();
    socket.close();
  }

  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(1234);
    while (true) {
      new FileServerThread(serverSocket.accept()).run();
    }
  }
}
