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

import Main.PrivateMsg;
/**
 *
 * @author Ravinder's PC
 */
public class ServerSide {
    private ArrayList<ClientThread> clist;// For list of client serving sockets
    ServerSocket ssoc;
    Socket soc;
    int port = 5555; //default value of port
    ObjectInputStream InFromClient;
    ObjectOutputStream OutFromServer;
    private GUIServer guiS;
    static int uniqueId = 0; // unique to maintain clist using Uid
    
    ServerSide(int port, GUIServer guis){
        this.port = port;
        this.guiS = guis;
        this.clist = new ArrayList<ClientThread>();
    }
    
    public void DisplayMsgOnGui(String msg){
        guiS.displayMsg(msg);
    }
    
    public void StartServerSide() {
        
        try {
            ssoc = new ServerSocket(port);
            DisplayMsgOnGui("Waiting For Clients ...");
            while(true){
                soc = ssoc.accept();
                System.out.println("Connection Established!");
            ClientThread t = new ClientThread(soc); // Creating A client thread for this client and adding to the list
            clist.add(t);
            t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
            DisplayMsgOnGui(ex.toString());
        }
        
    }
    
    public void broadcast(String msg){
    	// Using for each loop to send msg to every client    	
        clist.forEach((x) -> {
            try {
                x.sOutput.writeObject((Object)msg);
            } catch (IOException ex) {
                Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
                DisplayMsgOnGui(ex.toString());
            }
        });
    }
    
    public void sendPrivateMsg(PrivateMsg pmsg) {
    	ClientThread sender, reciever;
    	sender = reciever = null;
    	for(ClientThread x : clist) {
    		String s = x.Uid + " " + x.username;
        	if((s.equals(pmsg.getRecUidUsername()))) {
        		reciever = x;
        	}
        	if((s.equals(pmsg.getSenderUidUsername()))) {
        		sender = x;
        	}
    	}
    	if(reciever == null)  {
    		pmsg.setMsg(pmsg.getRecUidUsername()+" Not Online!");
    	}
    	else {
    		try {
				reciever.sOutput.writeObject((Object)pmsg);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	try {
			sender.sOutput.writeObject((Object)pmsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	public void StopServer(){
		// Notifying all clients and closing every client socket connection
        clist.forEach((x) -> {
            try {
                x.sOutput.writeObject((Object)"Server Stopped!\n");
                //x.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
                DisplayMsgOnGui(ex.toString() + "Server Side");
            }
        });
        DisplayMsgOnGui("Server Stopped!\n");
        try {
            ssoc.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
            DisplayMsgOnGui(ex.toString());
        }
    }
    
    public String whoisonline(){
//    	List every client's name on the gui from clist
        String s = "Online Users:\n";
//        for( ClientThread x : clist){
//            s += x.Uid + "\n";
//        }
        s = clist.stream().map((x) -> x.Uid +" "+ x.username +"\n").reduce(s, String::concat);// Same as above for loop
        return s; // Returning String
        
    }
    
    class ClientThread extends Thread {
        // the socket where to listen/talk with client from server
        
        int id;
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        String username;
        boolean keepGoing = true;
        String Uid;

        // Constructor
        ClientThread(Socket socket) {
            // a unique id
            id = ++uniqueId;
            this.socket = socket;
            /* Creating both Data Stream */
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                // create output first
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                System.out.println("Created ...");
                try {
                    // read the user name
                    username = (String) sInput.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServerSide.class.getName()).log(Level.SEVERE, null, ex);
                    DisplayMsgOnGui(ex.toString());
                }
                Uid = socket.getInetAddress().toString();//id +"."+ username;// Unique Id + username, because username can be same
                
                DisplayMsgOnGui(username + " just connected." + Uid);
                
                sOutput.writeObject((Object)Uid);// Sent to client to set its UID
            }catch (IOException e) {
            	DisplayMsgOnGui(e.toString());
                return;
            }
        }

        // what will run forever
        public void run() {
            // to loop until Disconnected
            
            while (keepGoing) {
                // read a String (which is an object)
                try {
                    Object obj =  sInput.readObject();
                    if(obj instanceof File){
//                    	For file object
                        File f = (File) obj;
                        writeFile(f);
                    }
                    else if(obj instanceof PrivateMsg) {
                    	writePrivateMsg((PrivateMsg)obj);
                    }
                    else{
//                    	For string message
                        String cm = (String) obj;
                        System.out.println("String Reieved "+cm);
                        if(cm.equals("Who is online?")){
//                        	Functions from client 
                            String s = whoisonline();
                            sOutput.writeObject(s);
                        }
                        else if(cm.equalsIgnoreCase("Disconnect")){
//                        	Client trying to disconnect
                            this.ClientDisconnected(this.Uid + this.username);
                        }
                        else
                        writeMsg(cm);
//                        sOutput.writeObject(cm);
                    }
                    
                } catch (IOException e) {
                    DisplayMsgOnGui(username + " Exception reading Streams: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                	DisplayMsgOnGui(e2.toString());
                    break;
                }
            }
            // remove this from the cList containing the list of the
            // connected Clients
//            ClientDisconnected(Uid);
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
            DisplayMsgOnGui(Uid+" "+username+" Disconnected!");
            clist.remove(this);
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
        
        private boolean writePrivateMsg(PrivateMsg pmsg) {
            // if Client is still connected send the message to it
            if (!socket.isConnected()) {
                close();
                return false;
            }
            //                sOutput.writeObject(msg);
            //DisplayMsgOnGui(pmsg);
            sendPrivateMsg(pmsg);
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

        private void ClientDisconnected(String uidUser) {
        	
            clist.forEach((ClientThread x) -> {
            String s =x.Uid + x.username;
            if(s.equals(uidUser)){
                clist.remove(x); // remove clist thread from clist
                x.close();
            }
        });
        }
        
    }
}
    



