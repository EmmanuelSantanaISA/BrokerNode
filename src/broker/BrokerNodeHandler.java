/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broker;

import message.Message;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Subscription;

/**
 *
 * @author emmanuelsantana
 */
class BrokerNodeHandler extends Thread {

    private BrokerNode parent;
    private Socket socket;
    private ObjectInputStream ois;
    private DataInputStream input;
    private DataOutputStream output;

    BrokerNodeHandler(Socket socket, BrokerNode parent) throws IOException {
        this.parent = parent;
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        initialize();
    }

    private void initialize() {
        this.start();
    }

    public void forwardMessage(Subscription subscription) {

        List<BrokerNode> neighbors = this.parent.getNeighbors();

        for (BrokerNode brokerNode : neighbors) {
            try {
                Socket fwSocket;
                fwSocket = new Socket(brokerNode.getIpAddress(), brokerNode.getPort());
                ObjectOutputStream dos;
                dos = new ObjectOutputStream(fwSocket.getOutputStream());
                dos.writeObject(subscription);
                dos.close();
                fwSocket.close();
            } catch (IOException ex) {
                System.out.println("forward:" + ex);
            }
        }

    }

    public void matchSubscriptions(Message message) {

        try {
            List<Subscription> subscriptions = this.parent.getSubscriptionByTopic(message.getTopic());
            for (Subscription subscription : subscriptions) {
                try {
                    System.out.println("Enviando mensaje a: " + subscription.getIpAddress());
                    Socket newSocket;
                    newSocket = new Socket(subscription.getIpAddress(), subscription.getPort());
                    ObjectOutputStream dos = new ObjectOutputStream(newSocket.getOutputStream());
                    dos.writeObject(message);
                    newSocket.close();
                } catch (IOException ex) {
                    System.out.println("Failed to deliver message:" + ex);
                }
            }
        } catch (Exception ex) {
            System.out.println("machSubscriptions:" + ex);
        }
    }

    public boolean addSubscription(Subscription subscription) {
        return this.parent.addSubscription(subscription);
    }

    @Override
    public void run() {
        boolean forward = false;
        Object message;
        Subscription subscription = null;
        try {
            message = this.ois.readObject();
            if (message instanceof Message) {
                Message msg = (Message) message;
                matchSubscriptions(msg);
            } else if (message instanceof Subscription) {
                subscription = (Subscription) message;
                System.out.println("Subscription from " + subscription.getIpAddress());
                forward = addSubscription(subscription);
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(BrokerNodeHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (forward) {
            String hostAddress;
            hostAddress = this.socket.getInetAddress().getHostAddress();
            subscription.setFromIpAddress(hostAddress);
            forwardMessage(subscription);
        }

    }

}
