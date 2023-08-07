import jatyc.lib.Typestate;

@Typestate("FileClient2")
public class FileClient2 extends FileClient {
    protected String lastLine = "";
    public String nextLine() throws Exception {
        String line = in.readLine();
        this.lastLine = line;
        return line;
    }

    public static void main(String[] args) throws Exception {
        String fileName = "sarasa2.txt";
        FileClient2 client = new FileClient2();
        if (client.start()) {
            System.out.println("File client 2 started!");

            client.request(fileName); // Makes the server request
            while (client.hasNext()) {
                System.out.println(client.nextLine());
            }

            client.close();
        } else {
            System.out.println("Could not start client 2!");
        }
    }

}
