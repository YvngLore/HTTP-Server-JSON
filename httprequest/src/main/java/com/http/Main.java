package com.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

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
                DataOutputStream outbytes = new DataOutputStream(s.getOutputStream());
                sendResponse(out, in, outbytes);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getPath(String str){

        String file_path = str.split(" ")[1];
        String result = "./httprequest/site";
        switch(str){
            case "/":
                file_path = "/index.html";
                break;
        }

        return result + file_path;
    }

    public static String getTypeFromPath(String path){
        String result = "text/html";

        String ext = path.startsWith(".") ? path.substring(1) : path;
        //System.out.println("EXT PRIMA: " + ext + "\n");
        //System.out.println("GRANDEZZA: " + ext.split("\\.").length + "\n");
        ext = ext.split("\\.").length < 1 ? ".html" : ext.split("\\.")[1];
        //System.out.println("EXT DOPO: " + ext + "\n");
        switch (ext) {
            case ("html"):
                result = "text/html";
                break;
            case ("js"):
                result = "text/javascript";
                break;
            case ("css"):
                result = "text/css";
                break;
            case ("ico"):
                result = "image/vnd.microsoft.icon";
                break;
            case ("png"):
                result = "image/png";
                break;
            case ("jpg"):
                result = "image/jpg";
                break;
            case ("jpeg"):
                result = "image/jpeg";
                break;
            case ("json"):
                result = "application/json";
                break;
            default:
                result = "text/html";
                break;
        }

        return result;
    }
    
    public static void sendBinaryFile(DataOutputStream outbytes, File file) throws Exception{
        System.out.println("inizio");
        InputStream reader = new FileInputStream(file);
        System.out.println("inizio 2");
        byte[] buf = new byte[8192];
        System.out.println("inizio 3");
        int n;

        while((n = reader.read(buf)) != -1){
            System.out.println("inizio ciclo " + n);
            outbytes.write(buf,0,n);
           System.out.println("done " + n); 
        }
        System.out.println("ciao");
        outbytes.close();
        reader.close();
        System.out.println("ciao2");
    }

    public static void sendResponse(PrintWriter out, BufferedReader in, DataOutputStream outbytes){
        try {
            String str = in.readLine();
            System.out.println(str);
            String file_path = getPath(str);
            String type = getTypeFromPath(file_path);

            if(type.endsWith("json")){
                Studente s = new Studente("Rayan", "Moh'd", "ccff1", 18, "5Dia");
                ObjectMapper json = new ObjectMapper();

                String result = json.writeValueAsString(s);
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Length: " + result.length());
                out.println("Server: Java HTTP Server from YvngLore: 1.0");
                out.println("Date: " + new Date());
                out.println("Content-Type: " + type);
                out.println();
                out.println(result);
                out.flush();
            }
            else{
                File file = new File(file_path);
                long datalength = 0;
                String data = "";
                if(type.startsWith("text")){
                    Scanner reader = new Scanner(file);
                    do{
                        str = in.readLine();
                        System.out.println(str);
                    }while(!str.equals("") || !str.isEmpty());

                    System.out.println(str);

                    while(reader.hasNextLine()){
                        data += reader.nextLine();
                    }
                    datalength = data.length();
                    reader.close();
                }
                else{
                    datalength = file.length() ;
                }

                System.out.println("RESPONSE: \n");
                out.println("HTTP/1.1 200 OK");
                System.out.println("HTTP/1.1 200 OK");
                out.println("Content-Length: " + datalength);
                System.out.println("Content-Length: " + datalength);
                out.println("Server: Java HTTP Server from Benve: 1.0");
                System.out.println("Server: Java HTTP Server from Benve: 1.0");
                out.println("Date: " + new Date());
                System.out.println("Date: " + new Date());
                out.println("Content-Type: " + type );
                System.out.println("Content-Type: " + type );
                System.out.println();
                out.println();

                if(type.startsWith("text")){
                    out.println(data);
                    out.flush();
                }
                else{
                    sendBinaryFile(outbytes, file);
                }
            }

        } catch (Exception e) {
            out.println("HTTP/1.1 404 NOT FOUND");
            out.println();
            System.out.println(e.getMessage());
        }
    }
}