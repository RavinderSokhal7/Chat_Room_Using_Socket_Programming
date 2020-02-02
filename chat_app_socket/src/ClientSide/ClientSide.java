package ClientSide;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ravinder's PC
 */

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSide {
    private int port = 5555;
    private Socket soc;
    private String server;
    private final String username;
    private ObjectInputStream InFromServer;
    private ObjectOutputStream  OutToServer;
//    private DataOutputStream FOutToServer;
//    private FileInputStream FInFromServer;
    private final GUIClient guiC;
    private boolean keepGoing = true;
    
    public ClientSide(String server, int port, GUIClient guiC, String username){
        this.server = server;
        this.port = port;
        this.guiC = guiC;
        this.username = username;
    }
    
    
    public String getUsername(){
        return this.username;
    }
    
    public void StartClient() {
        try {
            soc = new Socket(this.server, this.port);
            guiC.displayMsg("Connected!");
            System.out.println("Client Created Socket ...");
            OutToServer = new ObjectOutputStream(soc.getOutputStream());
            InFromServer = new ObjectInputStream(soc.getInputStream());
            
            System.out.println("Created ...");
            OutToServer.writeObject(username);
            new ListenFromServer().start();
        } catch (IOException ex) {
            Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
            guiC.displayMsg(ex.toString());
            System.out.println(ex.toString());
        }
    }
    
    public void close(){
        if(OutToServer != null){
            try {
                OutToServer.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(InFromServer != null){
            try {
               InFromServer.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(soc != null){
            try {
                soc.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        keepGoing = false;
        guiC.displayMsg("Successfully Disconnected!");
    }
    
    public void sendMsg(String msg){
        try {
            this.OutToServer.writeObject(msg);
            System.out.println("Msg sent ...");
        } catch (IOException ex) {
            Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
            guiC.setText(ex.toString());
        }
    }
    
    public void sendFile(File f){
        try {
            this.OutToServer.writeObject(f);
            System.out.println("File sent ...");
        } catch (IOException ex) {
            Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
            guiC.displayMsg(ex.toString());
        }
//        FileInputStream fin = null;
//        try {
//            String filePath = username+" : "+f.getAbsolutePath();
//            byte [] bytearray  = new byte [(int)f.length()];
//            fin = new FileInputStream(f);
//            BufferedInputStream bin = new BufferedInputStream(fin);
//            bin.read(bytearray,0,bytearray.length);
//            OutputStream os = soc.getOutputStream();
//            System.out.println("Sending Files...");
//            os.write(bytearray,0,bytearray.length);
//            os.flush();
//            System.out.println("File transfer complete");
//            OutToServer = new ObjectOutputStream(soc.getOutputStream());
//            InFromServer = new ObjectInputStream(soc.getInputStream());
//            
//            sendMsg(filePath);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    class ListenFromServer extends Thread {

        public void run() {
            while (keepGoing) {
                try {
                    String msg = (String) InFromServer.readObject();
                    // if console mode print the message and add back the prompt
                    if (guiC == null) {
                        System.out.println(msg);
                        System.out.print("> ");
                    } else if(msg.equals("Server Stopped!\n")){
                        String s = guiC.getText();
                        guiC.Disconnect();
                        guiC.setText(s+"\n"+"Server Stopped!");
                    }                    
                    else{
                        guiC.displayMsg(msg);
                    }
                } catch (IOException e) {
//                    display("Server has close the connection: " + e);
//                    if (guiC != null) {
//                        guiC.connectionFailed();
//                    }
                    break;
                } // can't happen with a String object but need the catch anyhow
                catch (ClassNotFoundException e2) {
                }
            }
        }
    }
    
}
