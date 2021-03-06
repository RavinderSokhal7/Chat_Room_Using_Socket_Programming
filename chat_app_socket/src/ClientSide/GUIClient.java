/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.border.TitledBorder;

import Main.PrivateMsg;

import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author Ravinder's PC
 */
public class GUIClient extends javax.swing.JFrame {
    String server;
    private final String username;
    int port;
    ClientSide cs = null;
    
    /**
     * Creates new form ChatScreen
     * @param server
     * @param port
     * @param s
     */
    
    public GUIClient(String server, int port, String s) {
        this.server = server;
        this.port = port;
        this.username = s;
        initComponents();
        Connect.setEnabled(true);
        Disconnect.setEnabled(false);
        Online.setEnabled(false);
        AttachFile.setEnabled(false);
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public void sendMsg(){
    	if(!this.writeMessageTA.getText().equals("")) {
            cs.sendMsg(this.writeMessageTA.getText());
            this.writeMessageTA.setText("");
    	}
    }
    
    public void sendPrivateMsg() {
    	if(!this.PCwriteMessageTA.getText().equals("")) {
    		String ToUid = PCip.getText().trim();
        	String ToUsername = PCusername.getText().trim();
        	if(ToUid.equals("")||ToUsername.equals("")) {
        		displayPrivateMsg(null);
        	}  
        	else {
            	PrivateMsg pmsg = new PrivateMsg();
            	pmsg.setMsg(this.PCwriteMessageTA.getText().trim());
            	pmsg.setRecUidUsername(ToUid+" "+ToUsername);
            	cs.sendPrivateMsg(pmsg);
                this.PCwriteMessageTA.setText("");
        	}
    	}
    }
    public void setText(String s){
        this.TextArea.setText(s);
    }
    
    public String getText(){
        return this.TextArea.getText();
    }
    
    public void displayMsg(String s){
        String p = this.getText();
        this.setText(p+"\n"+s);
    }
    
    public void displayPrivateMsg(PrivateMsg pmsg) {
    	if(pmsg == null) {
    		String p = this.PCtextArea.getText();
        	this.PCtextArea.setText(p+"\nSet IP and Username of reciever!");    		
    	}
    	else {
    		String p = this.PCtextArea.getText();
        	this.PCtextArea.setText(p+"\n"+pmsg.getMsg());
    	}
    }
    
    public void Disconnect(){
        cs.sendMsg("Disconnect");
        cs.close();
        Connect.setEnabled(true);
        Disconnect.setEnabled(false);
        Online.setEnabled(false);
        cs = null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        writeMessageTA = new javax.swing.JTextArea();
        Send = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        userNameLabel = new javax.swing.JLabel();
        Connect = new javax.swing.JButton();
        Disconnect = new javax.swing.JButton();
        Online = new javax.swing.JButton();
        portTF = new javax.swing.JTextField();
        userNameLabel1 = new javax.swing.JLabel();
        serverTF = new javax.swing.JTextField();
        AttachFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(new TitledBorder(null, "Chat Room", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        TextArea.setEditable(false);
        TextArea.setBackground(new java.awt.Color(0, 102, 102));
        TextArea.setColumns(20);
        TextArea.setForeground(new java.awt.Color(240, 240, 240));
        TextArea.setRows(5);
        TextArea.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(TextArea);

        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
        				.addComponent(jButton1, Alignment.TRAILING))
        			.addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
        			.addContainerGap())
        );
        jPanel1.setLayout(jPanel1Layout);

        writeMessageTA.setColumns(20);
        writeMessageTA.setRows(5);
        jScrollPane1.setViewportView(writeMessageTA);

        Send.setText("Send");
        Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102), 2), username));

        userNameLabel.setText("Port :");

        Connect.setText("Connect");
        Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectActionPerformed(evt);
            }
        });

        Disconnect.setText("Disconnect");
        Disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DisconnectActionPerformed(evt);
            }
        });

        Online.setText("Who is online");
        Online.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnlineActionPerformed(evt);
            }
        });

        portTF.setText("5555");

        userNameLabel1.setText("Server ip:");

        serverTF.setText("localhost");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(portTF, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(userNameLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(serverTF, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Connect, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Disconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Online, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(userNameLabel1)
                        .addComponent(serverTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Connect, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Disconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Online, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(userNameLabel)
                            .addComponent(portTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        AttachFile.setText("Send File");
        AttachFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttachFileActionPerformed(evt);
            }
        });
        
        panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Private Chats", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        
        scrollPane = new JScrollPane();
        
        PCClear = new JButton();
        PCClear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		PCtextArea.setText("");
        	}
        });
        PCClear.setText("Clear");
        
        JLabel lblNewLabel = new JLabel("Send to:");
        
        JLabel lblNewLabel_1 = new JLabel("IP :");
        
        JLabel lblNewLabel_2 = new JLabel("Username :");
        
        PCip = new JTextField();
        PCip.setText("/");
        PCip.setColumns(10);
        
        PCusername = new JTextField();
        PCusername.setColumns(10);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
        				.addComponent(PCClear, Alignment.TRAILING)
        				.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblNewLabel_1)
        					.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
        					.addComponent(PCip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblNewLabel_2)
        					.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
        					.addComponent(PCusername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addComponent(PCClear, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(lblNewLabel_1))
        				.addComponent(PCip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_2)
        				.addComponent(PCusername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(15, Short.MAX_VALUE))
        );
        
        PCtextArea = new JTextArea();
        PCtextArea.setSelectedTextColor(Color.BLACK);
        PCtextArea.setRows(5);
        PCtextArea.setForeground(SystemColor.menu);
        PCtextArea.setEditable(false);
        PCtextArea.setColumns(20);
        PCtextArea.setBackground(new Color(0, 102, 102));
        scrollPane.setViewportView(PCtextArea);
        panel.setLayout(gl_panel);
        
        scrollPane_1 = new JScrollPane();
        
        PCSend = new JButton();
        PCSend.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		sendPrivateMsg();
        	}
        });
        PCSend.setText("Send");
        
        PCAttachFile = new JButton();
        PCAttachFile.setText("Send File");
        PCAttachFile.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(AttachFile)
        							.addPreferredGap(ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
        							.addComponent(Send, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
        						.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        						.addGroup(layout.createParallelGroup(Alignment.LEADING)
        							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)
        							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(PCAttachFile, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(PCSend, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)))))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        				.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createParallelGroup(Alignment.LEADING)
        					.addGroup(layout.createSequentialGroup()
        						.addComponent(PCSend, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
        					.addComponent(PCAttachFile, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createParallelGroup(Alignment.LEADING)
        					.addComponent(Send, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        					.addComponent(AttachFile, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        
        PCwriteMessageTA = new JTextArea();
        PCwriteMessageTA.setRows(5);
        PCwriteMessageTA.setColumns(20);
        scrollPane_1.setViewportView(PCwriteMessageTA);
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectActionPerformed
        // Conect to server
        server = serverTF.getText();
        port = Integer.parseInt(portTF.getText());
        if(cs == null || cs.getSoc()==null){
            cs = new ClientSide(server, port, this, username);
            System.out.println("ClientSide reached ...");
            cs.StartClient();
            System.out.println("ClientSide Started ...");
            if(cs.getSoc()!= null) {
            Connect.setEnabled(false);
            Disconnect.setEnabled(true);
            Online.setEnabled(true);
            }
        }
    }//GEN-LAST:event_ConnectActionPerformed

    private void SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendActionPerformed
        // TODO add your handling code here:
        this.sendMsg();
    }//GEN-LAST:event_SendActionPerformed

    private void OnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnlineActionPerformed
        // Who is online
        cs.sendMsg("Who is online?");
    }//GEN-LAST:event_OnlineActionPerformed

    private void DisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DisconnectActionPerformed
        // TODO add your handling code here:
        Disconnect();
    }//GEN-LAST:event_DisconnectActionPerformed

    private void AttachFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttachFileActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        cs.sendFile(f);
    }//GEN-LAST:event_AttachFileActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUIClient("localhost", 5555, "Spydee").setVisible(true);///Default values
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AttachFile;
    private javax.swing.JButton Connect;
    private javax.swing.JButton Disconnect;
    private javax.swing.JButton Online;
    private javax.swing.JButton Send;
    private javax.swing.JTextArea TextArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField portTF;
    private javax.swing.JTextField serverTF;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JLabel userNameLabel1;
    private javax.swing.JTextArea writeMessageTA;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JButton PCClear;
    private JTextArea PCtextArea;
    private JTextField PCip;
    private JTextField PCusername;
    private JScrollPane scrollPane_1;
    private JButton PCSend;
    private JButton PCAttachFile;
    private JTextArea PCwriteMessageTA;
}
