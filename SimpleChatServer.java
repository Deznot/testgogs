import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {
    ArrayList<PrintWriter> clientOutputStreams; //List for store client message

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

                Thread t = new Thread(new ClientHandler(clientSocket)); // need to add task for Thread
                t.start();
                System.out.println("got a connection");
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
