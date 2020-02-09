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
import Main.PrivateMsg;

public class ClientSide {
    private int port = 5555;
    private Socket soc;
    private String server;
    private final String username;
    private String Uid;
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
    
    
    
    public Socket getSoc() {
		return soc;
	}

	public void setPort(int port) {
		this.port = port;
	}



	public void setServer(String server) {
		this.server = server;
	}



	public void setInFromServer(ObjectInputStream inFromServer) {
		InFromServer = inFromServer;
	}



	public void setOutToServer(ObjectOutputStream outToServer) {
		OutToServer = outToServer;
	}



	public void setKeepGoing(boolean keepGoing) {
		this.keepGoing = keepGoing;
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
            
            System.out.println("Streams Created ...");
//            send username to server
            OutToServer.writeObject(username);
            try {
//            	Get Uid from server
                Uid = (String) InFromServer.readObject();//
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
                guiC.displayMsg(ex.toString());
            }
//            Start the client listening Thread
            new ListenFromServer().start();
        } catch (IOException ex) {
            Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
            guiC.displayMsg(ex.toString() + "\n Server not online!");
            System.out.println(ex.toString());
        }
    }
    
//    Close Streams and the Socket
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
            if(!msg.equals("Disconnect") && !(msg.equals("Who is online?"))){
                msg = Uid + " "+username+" :" +msg;
            }
            this.OutToServer.writeObject(msg);
            System.out.println("Msg sent ...");
        } catch (IOException ex) {
            Logger.getLogger(ClientSide.class.getName()).log(Level.SEVERE, null, ex);
            guiC.setText(ex.toString());
        }
    }
    
    public void sendPrivateMsg(PrivateMsg pmsg) {
    	pmsg.setMsg(Uid + " "+username+" :" + pmsg.getMsg());
    	pmsg.setSenderUidUsername(Uid + " "+username);
    	try {
			this.OutToServer.writeObject(pmsg);
		} catch (IOException e) {
			e.printStackTrace();
			guiC.setText(e.toString());
		}
        System.out.println("Msg sent ...");
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
                    Object msg = InFromServer.readObject();
                    // if console mode print the message and add back the prompt
                    if(msg instanceof String) {
                    	if (guiC == null) {
                            System.out.println((String)msg);
                            System.out.print("> ");
                        } else if(msg.equals("Server Stopped!\n")){
                            String s = guiC.getText();
                            guiC.Disconnect();
                            guiC.setText(s+"\n"+"Server Stopped!");
                        }                    
                        else{
                            guiC.displayMsg((String)msg);
                        }
                    }
                    else if(msg instanceof PrivateMsg) {
                    	PrivateMsg pmsg = (PrivateMsg) msg;
                    	guiC.displayPrivateMsg(pmsg);
                    }
                } catch (IOException e) {
                	guiC.displayMsg(e.toString());
                    break;
                } // can't happen with a String object but need the catch anyhow
                catch (ClassNotFoundException e2) {
                }
            }
        }
    }
    
}
