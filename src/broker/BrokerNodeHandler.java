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

    public void forwardMessage(Message message) {

        List<BrokerNode> neighbors = this.parent.getNeighbors();

        for (BrokerNode brokerNode : neighbors) {
            try {
                Socket fwSocket = new Socket(brokerNode.getIpAddress(), brokerNode.getPort());
                ObjectOutputStream dos = new ObjectOutputStream(fwSocket.getOutputStream());
                dos.writeObject(message);
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
                    System.out.println("Enviando mensaje a: " + subscription.toString());
                    Socket socket = new Socket(subscription.getIpAddress(), subscription.getPort());
                    ObjectOutputStream dos = new ObjectOutputStream(socket.getOutputStream());
                    dos.writeObject(message);
                    socket.close();
                } catch (IOException ex) {
                    System.out.println("Failed to deliver message:" + ex);
                }
            }
        } catch (Exception ex) {
            System.out.println("machSubscriptions:" + ex);
        }
    }

    public boolean addSubscription(Message message) {
        return this.parent.addSubscription(message.getSubscription());
    }

    @Override
    public void run() {
        try {
            boolean forward = false;
            Message message = (Message) this.ois.readObject();
            switch (message.getMessageType()) {
                case BROKER:

                    break;
                case PUBLISH:
                    matchSubscriptions(message);
                    break;
                case SUBSCRIBE:
                    System.out.println("Subscription from " + message.getIpSource());
                    forward = addSubscription(message);
                    break;
            }
            if (forward) {
                message.setIpSource(this.socket.getInetAddress().getHostAddress());
                forwardMessage(message);
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(BrokerNodeHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
