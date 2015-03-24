/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.Client;

import chat.Internationalization;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Matthieu Blais
 */
public class MultiCastSender {

    private TextField txt;
    private Button btn;
    private String name;
    private SecureRandom random;
    private String ID;
    private String prev;
    private boolean leave = false;
    private String address;
    private int port;
    private boolean debug;

    public MultiCastSender(TextField text, Button btn, String address, int port,boolean b) {
        txt = text;
        random = new SecureRandom();
        debug =b;
        ID = SessionId();
        name = Internationalization.get("my.guest");
        this.address = address;
        this.btn = btn;
        this.port = port;
        connect();
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    byte[] outBuf;
                    DatagramSocket socket = null;
                    socket = new DatagramSocket();
                    DatagramPacket outPacket = null;
                    InetAddress addresse = InetAddress.getByName(address);
                    if (!txt.getText().equals("")) {
      //                  System.out.println("poulet");
                        String text = txt.getText();
                        if (!nick(text)) {
                            text = ID + "</>" + name + "> " + text;
                            outBuf = text.getBytes();
                            outPacket = new DatagramPacket(outBuf, outBuf.length, addresse, port);

                            socket.send(outPacket);
                        } else {
                            text = ID + ">/<" + name + "</>" + prev + " has changed his name in " + name;
                            outBuf = text.getBytes();
                            outPacket = new DatagramPacket(outBuf, outBuf.length, addresse, port);

                            socket.send(outPacket);
                        }
                    }                
                    socket.close();
                } catch (UnknownHostException ex) {
                    if(debug)
                    Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    if(debug)
                    Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }

    public boolean nick(String s) {
        String[] string = s.split(" ");
        if (string.length > 1) {
            if (string[0].equals("/nick")) {
                prev = name;
                name = string[1];
                return true;
            }
        }
        return false;
    }
    
     public void connect() {
        try {
            byte[] outBuf;
            DatagramSocket socket = null;
            socket = new DatagramSocket();
            DatagramPacket outPacket = null;
            InetAddress addresse = InetAddress.getByName(address);
          
                String text = ID + "~##~" + name + "</>" + Internationalization.get("my.welcome2") + " " + name + " " + Internationalization.get("my.onchat");
                    outBuf = text.getBytes();
                    outPacket = new DatagramPacket(outBuf, outBuf.length, addresse, port);

                    socket.send(outPacket);

        } catch (UnknownHostException ex) {
            Alert a = new Alert(AlertType.ERROR);
          a.setTitle("Error Dialog");
          a.setHeaderText("Look, an Error Dialog");
          a.setContentText(ex.getMessage());

          a.showAndWait();
            if(debug)
            Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Alert a = new Alert(AlertType.ERROR);
          a.setTitle("Error Dialog");
          a.setHeaderText("Look, an Error Dialog");
          a.setContentText(ex.getMessage());

          a.showAndWait();
            if(debug)
            Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void left() {
        try {
            byte[] outBuf;
            DatagramSocket socket = null;
            socket = new DatagramSocket();
            DatagramPacket outPacket = null;
            InetAddress addresse = InetAddress.getByName(address);
          
                String text = ID + "><<|>><" + name + "</>" + name +" " + Internationalization.get("my.hasleft");

                    outBuf = text.getBytes();
                    outPacket = new DatagramPacket(outBuf, outBuf.length, addresse, port);

                    socket.send(outPacket);

        } catch (UnknownHostException ex) {
            Alert a = new Alert(AlertType.ERROR);
          a.setTitle("Error Dialog");
          a.setHeaderText("Look, an Error Dialog");
          a.setContentText(ex.getMessage());

          a.showAndWait();
            if(debug)
            Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Alert a = new Alert(AlertType.ERROR);
          a.setTitle("Error Dialog");
          a.setHeaderText("Look, an Error Dialog");
          a.setContentText(ex.getMessage());

          a.showAndWait();
            if(debug)
            Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String SessionId() {
        return new BigInteger(130, random).toString(32);
    }

    public void newJoin(){
        try {
            byte[] outBuf;
            DatagramSocket socket = null;
            socket = new DatagramSocket();
            DatagramPacket outPacket = null;
            InetAddress addresse = InetAddress.getByName(address);
       //   System.out.println("yayayayayyayayayayaya");
                String text = ID + "~~" + name + "</>";

                    outBuf = text.getBytes();
                    outPacket = new DatagramPacket(outBuf, outBuf.length, addresse, port);

                    socket.send(outPacket);

        } catch (UnknownHostException ex) {
            Alert a = new Alert(AlertType.ERROR);
          a.setTitle("Error Dialog");
          a.setHeaderText("Look, an Error Dialog");
          a.setContentText(ex.getMessage());

          a.showAndWait();
          if(debug)
            Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Alert a = new Alert(AlertType.ERROR);
          a.setTitle("Error Dialog");
          a.setHeaderText("Look, an Error Dialog");
          a.setContentText(ex.getMessage());

          a.showAndWait();
          if(debug)
            Logger.getLogger(MultiCastSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
