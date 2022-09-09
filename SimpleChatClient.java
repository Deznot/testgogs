import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleChatClient {
    Socket socket;
    PrintWriter writer;
    JTextField outgoing;
    JTextArea incoming;
    BufferedReader reader;

    public static void main(String[] args) {
        new SimpleChatClient().go();
    }

    public void go() {

    }   
}