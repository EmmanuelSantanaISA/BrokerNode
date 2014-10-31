/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadbalancer;

import broker.BrokerNode;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import message.Subscription;

/**
 *
 * @author emmanuelsantana
 */
public class LoadBalancerHandler extends Thread {

    private Socket socket;
    private ObjectInputStream ois;
    private LoadBalancer loadBalancer;

    public LoadBalancerHandler(Socket socket, LoadBalancer loadBalancer) {
        this.socket = socket;
        this.loadBalancer = loadBalancer;
        initialize();
    }

    private void initialize() {
        this.start();
    }

    private void sendToBroker(Subscription subscription) {
        BrokerNode randomBroker = this.loadBalancer.getRandomBroker();
        System.out.println("Selected Broker: Ip Address- " + randomBroker.getIpAddress()+ " Port- " + randomBroker.getPort());
        try {
            try (Socket broker = new Socket(randomBroker.getIpAddress(), randomBroker.getPort())) {
                ObjectOutputStream dos = new ObjectOutputStream(broker.getOutputStream());
                dos.writeObject(subscription);
            }
        } catch (IOException ex) {
            System.err.println("Failed to asign broker:" + ex);
        }
    }

    @Override
    public void run() {
        try {
            Subscription subscription = (Subscription) this.ois.readObject();
            sendToBroker(subscription);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Thread.- Failed to read message: " + e);
        }
    }

}
