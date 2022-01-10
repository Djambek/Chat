package com.company;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here

        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Program started, port is opening, waiting for client");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected");
        PrintStream sout = new PrintStream(socket.getOutputStream());
        HostMyOutputThread hostMyOutputThread = new HostMyOutputThread(sout);
        hostMyOutputThread.start();
        Scanner sin = new Scanner(socket.getInputStream());
        sout.println("Hello user! What's your name?");
        String username = sin.nextLine();
        System.out.println("user - "+username);
        sout.println("Nice to meet you, "+username);
        String text = sin.nextLine();
        while (!Objects.equals(text, "Bye")) {
            System.out.println("user - " + text);
            text = sin.nextLine();
        }
        hostMyOutputThread.interrupt();
        //System.out.println(sin);
    }
}

class HostMyOutputThread extends Thread{
    PrintStream sout;
    HostMyOutputThread(PrintStream sout){
        this.sout = sout;
    }
    public void run(){
        while (! interrupted()){
            Scanner scanner = new Scanner(System.in);
            sout.println("HOST: "+ scanner.nextLine());
        }
        sout.println("Bye!");
    }
}