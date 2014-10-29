/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscribe;

import broker.Subscription;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import message.Message;

/**
 *
 * @author emmanuelsantana
 */
public class Subscriber extends Thread {

    private Socket socket = null;
    private ServerSocket server = null;
    private ObjectOutputStream oos = null;
    private SubscribeEvent callback;
    private int lisenPort = 8888;

    public Subscriber(String host, int brokerPort, int listenPort, SubscribeEvent callback) throws Exception {
        this.socket = new Socket(host, brokerPort);
        this.server = new ServerSocket(listenPort);
        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        this.callback = callback;
        this.lisenPort = listenPort;
        initialize();
    }

    private void initialize() {
        this.start();
    }

    public void subscribe(Subscription subscription) {
        try {
            Message message = new Message(subscription, Message.MessageType.SUBSCRIBE);
            message.setPortSource(this.lisenPort);
            this.oos.writeObject(message);
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException ex) {
            System.err.println("Failed to subscribe: " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Client is waiting...");
            Socket client;
            try {
                client = this.server.accept();
                SubscriberHandler sHandler = new SubscriberHandler(client, this);
            } catch (IOException ex) {
                System.err.println("Thread.- Failed to accept connection: " + ex.getMessage());
            }

        }
    }

    public SubscribeEvent getCallback() {
        return callback;
    }

    public void setCallback(SubscribeEvent callback) {
        this.callback = callback;
    }

}
