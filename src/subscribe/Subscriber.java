/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscribe;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import main.SubscriberView;
import message.Subscription;

/**
 *
 * @author emmanuelsantana
 */
public class Subscriber extends Thread implements ISubscriber {

    private Socket socket = null;
    private ServerSocket server = null;
    private ObjectOutputStream oos = null;
    private SubscribeEvent callback;
    private int lisenPort = 8888;
    private List<Subscription> subscriptions;
    private Subscription selectedSubscription;
    private SubscriberView view;

    public Subscriber(String host, int brokerPort, int listenPort, SubscribeEvent callback, SubscriberView view) throws Exception {
        this.view = view;
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

    @Override
    public void subscribe(Subscription subscription) {
        try {
            subscription.setPort(lisenPort);
            subscription.setFromIpAddress(socket.getInetAddress().getHostAddress());
            subscription.setIpAddress(socket.getInetAddress().getHostAddress());
            this.oos.writeObject(subscription);
            if (this.socket != null) {
                this.socket.close();
            }
            subscriptions.add(subscription);
            
        } catch (IOException ex) {
            System.err.println("Failed to subscribe: " + ex.getMessage());
        }
    }

    @Override
    public void unSubscribe(Subscription subscription) {
        try {
            Subscription message = new Subscription();
            message.setPort(lisenPort);
            message.setFromIpAddress(socket.getInetAddress().getHostAddress());
            message.setIpAddress(socket.getInetAddress().getHostAddress());
            message.setUnSubscribe(true);
            this.oos.writeObject(message);
            if (this.socket != null) {
                this.socket.close();
            }
            subscriptions.remove(subscription);
            view.updateSubscriptions(subscriptions);
        } catch (IOException ex) {
            System.err.println("Failed to subscribe: " + ex.getMessage());
        }
    }

    public void selectedSubscriptionChanged(Subscription selectedSubscription) {
        if (selectedSubscription != null) {
            this.selectedSubscription = selectedSubscription;
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
