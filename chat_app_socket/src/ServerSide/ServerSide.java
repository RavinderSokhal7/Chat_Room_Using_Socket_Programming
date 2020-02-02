package ServerSide;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ravinder's PC
 */
public class ServerSide {
    private ArrayList<ClientThread> clist;
    ServerSocket ssoc;
    Socket soc;
    int port = 5555;
    ObjectInputStream InFromClient;
    ObjectOutputStream OutFromServer;
    private GUIServer guiS;
    
    ServerSide(int port, GUIServer guis){
        this.port = port;
        this.guiS = guis;
        this.clist = new ArrayList<ClientThread>();
    }
    
    public void DisplayMsgOnGui(String msg){
        guiS.displayMsg(msg);
    }
    
    public void StartServerSide() {
        String msg=null;
        
        try {
            ssoc = new ServerSocket(port);
            DisplayMsgOnGui("Waiting For Clients ...");
            while(true){
                soc = ssoc.accept();
                System.out.println("Connection Established!");
            ClientThread t = new ClientThread(soc);
            clist.add(t);
            t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void broadcast(String msg){
        clist.forEach((x) -> {
            try {
                x.sOutput.writeObject((Object)msg);
            } catch (IOException ex) {
                Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public void StopServer(){
        clist.forEach((x) -> {
            try {
                x.sOutput.writeObject((Object)"Server Stopped!\n");
                x.close();
                x.stop();
            } catch (IOException ex) {
                Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        try {
            ssoc.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String whoisonline(){
        String s = "Online Users:\n";
//        for( ClientThread x : clist){
//            s += x.username + "\n";
//        }
        s = clist.stream().map((x) -> x.username + "\n").reduce(s, String::concat);// Same as above for loop
        return s;
    }
    
    class ClientThread extends Thread {
        // the socket where to listen/talk

        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        String username;
        String date;
        boolean keepGoing = true;

        // Constructore
        ClientThread(Socket socket) {
            // a unique id
            //id = ++uniqueId;
            this.socket = socket;
            /* Creating both Data Stream */
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                // create output first
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                System.out.println("Created ...");
                try {
                    // read the username
                    username = (String) sInput.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
                }
                DisplayMsgOnGui(username + " just connected.");
            }catch (IOException e) {
//                display("Exception creating new Input/output Streams: " + e);
                return;
            }
            // but I read a String, I am sure it will work
             // have to catch ClassNotFoundException
            // but I read a String, I am sure it will work
            date = new Date().toString() + "\n";
        }

        // what will run forever
        public void run() {
            // to loop until LOGOUT
            
            while (keepGoing) {
                // read a String (which is an object)
                try {
                    Object obj =  sInput.readObject();
                    if(obj instanceof File){
                        File f = (File) obj;
                        writeFile(f);
                    }
                    else{
                        String cm = (String) obj;
                        System.out.println("String Reieved "+cm);
                        if(cm.equals("Who is online?")){
                            String s = whoisonline();
                            sOutput.writeObject(s);
                        }
                        else
                        writeMsg(cm);
                        //sOutput.writeObject(cm);
                    }
                    
                } catch (IOException e) {
                    DisplayMsgOnGui(username + " Exception reading Streams: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                    break;
                }
            }
            // remove myself from the arrayList containing the list of the
            // connected Clients
//            remove(id);
            close();
        }

        // try to close everything
        private void close() {
            // try to close the connection
            try {
                if (sOutput != null) {
                    sOutput.close();
                }
            } catch (Exception e) {
            }
            try {
                if (sInput != null) {
                    sInput.close();
                }
            } catch (Exception e) {
            };
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
            }
            this.keepGoing = false;
        }

        /*
		 * Write a String to the Client output stream
         */
        private boolean writeMsg(String msg) {
            // if Client is still connected send the message to it
            if (!socket.isConnected()) {
                close();
                return false;
            }
            //                sOutput.writeObject(msg);
            DisplayMsgOnGui(msg);
            broadcast(msg);
            return true;
        }
        
        private boolean writeFile(File f) {
            // if Client is still connected send the message to it
//            if (!socket.isConnected()) {
//                close();
//                return false;
//            }
//            //                sOutput.writeObject(msg);
//            DisplayMsgOnGui(f);
//            broadcast(f);
            return true;
        }
        
    }
}
    



