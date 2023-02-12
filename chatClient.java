import java.net.*;
import java.io.*;

public class chatClient {
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;

    public chatClient(String serverName, int serverPort){
        System.out.println("Establishing connection, pls wait...");
        try{
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected "+ socket);
            start();
        }
        catch(UnknownHostException host){
            System.out.println("Host unknown: " + host.getMessage());
        }
        catch(IOException ioe){
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }

        String line = null;
        while(!line.equals(".bye")){
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                line = reader.readLine();
                streamOut.writeUTF(line);
                streamOut.flush();
            }
            catch(IOException ioe){
                System.out.println("Sending error: " + ioe.getMessage());
            }
        }
    }

    public void start() throws IOException
    {
        console = new DataInputStream(System.in);
        streamOut = new DataOutputStream(socket.getOutputStream());
    }

    public void stop() throws IOException
    {
        try{
            if(console != null) console.close();
            if(socket != null) socket.close();
            if(streamOut != null) streamOut.close();
        }
        catch(IOException ioe){
            System.out.println("Error closing...");
        }
    }
    public static void main(String args[]){
        chatClient client = null;
        if(args.length != 1){
            System.out.println("Usage: java chatClient host port");
        }
        else client = new chatClient(args[0], Integer.parseInt(args[1]));
    }
}
