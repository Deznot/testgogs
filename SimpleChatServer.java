import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {
    ArrayList<PrintWriter> clientOutputStreams; //List for store client message

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;

        public ClientHandler(Socket clientSocket) {
            //it's a class constructor.
            try {
                sock = clientSocket; // set current socket to sock
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream()); // create bridje between byte stream and chars
                reader =  new BufferedReader(isReader); // read the chars
            }catch(Exception ex) {ex.printStackTrace();}
        }

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    //while the value of reader isn't null we do circle
                    System.out.println("read " + message);
                    tellEveryone(message);// send message for everyone
                }
            }catch(Exception ex) {ex.printStackTrace();}
        }
    }
    public static void main(String[] args) {
        new SimpleChatServer().go();
    }

    public void go() {
        clientOutputStreams = new ArrayList<PrintWriter>(); 
        try {
            ServerSocket serverSock = new ServerSocket(5000);
            
            while(true) {
                Socket clientSocket = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());//create bridje byte stream and chars after this we can write strings to socket
                clientOutputStreams.add(writer); //add writer for current socket to List

                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("got a connection");
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator(); //create iterator for ClientOutputStreams
        while(it.hasNext()) {
            //while the collection has next elements do
            try {
              PrintWriter writer = (PrintWriter) it.next();
              writer.println(message);
              writer.flush();  
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
