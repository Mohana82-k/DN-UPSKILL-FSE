// Client.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        Scanner sc = new Scanner(System.in);
        String msg;
        while (true) {
            System.out.print("You: ");
            msg = sc.nextLine();
            out.println(msg);
            if (msg.equalsIgnoreCase("bye")) break;
            System.out.println("Server: " + in.readLine());
        }
        s.close();
        sc.close();
    }
}
/*
You: Hello from client
Server: Echo: Hello from client
You: hii
Server: Echo: hii
You: hello again 
Server: Echo: hello again */