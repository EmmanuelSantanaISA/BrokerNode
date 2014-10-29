/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emmanuelsantana
 */
public class BrokerNode extends Thread {

    private String ipAddress;
    private int port;
    private ServerSocket serverSocket;
    private List<BrokerNode> neighbors;
    private List<Subscription> subscriptions;

    public BrokerNode(String ipAddress, int port) throws IOException {
        this.ipAddress = ipAddress;
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.neighbors = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        this.start();
    }

    public void addNeighbor(BrokerNode brokerNeighbor) {
        neighbors.add(brokerNeighbor);
    }

    public boolean addSubscription(Subscription sub) {
        if (subscriptions.contains(sub)) {
            System.out.println("Esta subscripcion ya existe: " + sub.toString());
            return false;
        } else {
            subscriptions.add(sub);
            System.out.println("Subscripcion agregada: " + sub.toString());
            return true;
        }
    }

    public List<Subscription> getSubscriptionByTopic(String topic) {
        List<Subscription> foundSubscriptions = new ArrayList<>();
        for (Subscription foundSubscription : foundSubscriptions) {
            if (foundSubscription.getTopic().equals(topic)) {
                foundSubscriptions.add(foundSubscription);
            }
        }
        return foundSubscriptions;
    }

    @Override
    public void run() {
        while (true) {
            Socket client;
            try {
                client = this.serverSocket.accept();
                BrokerNodeHandler brokerHandler = new BrokerNodeHandler(client, this);
            } catch (IOException ex) {
                Logger.getLogger(BrokerNode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<BrokerNode> getNeighbors() {
        return neighbors;
    }

    public int getPort() {
        return port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

}
