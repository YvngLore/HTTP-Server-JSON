package com.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Server in avvio");
            ServerSocket server = new ServerSocket(3002);
            System.out.println("Server avviato");
            while (true) {
                Socket s = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream());
                sendResponse(out, in);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sendResponse(PrintWriter out, BufferedReader in){
        try {
            File file = new File("index.html");
            Scanner reader = new Scanner(file);
            String str = "";
                do{
                    str = in.readLine();
                    System.out.println(str);
                }while(!str.equals("") || !str.isEmpty());
                String data = "";

                while(reader.hasNextLine()){
                    data += reader.nextLine();
                }
                reader.close();
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Length: " + data.length());
                out.println("Server: Java HTTP Server from Benve: 1.0");
                out.println("Date: " + new Date());
                out.println("Content-Type: text/html; charset-utf:8");
                out.println();
                
                out.println(data);
                out.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}