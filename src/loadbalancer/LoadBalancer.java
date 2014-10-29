/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loadbalancer;

import broker.BrokerNode;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;

/**
 *
 * @author emmanuelsantana
 */
public class LoadBalancer extends Thread {

    BrokerNode[] brokers;
    ServerSocket server;

    public LoadBalancer(BrokerNode[] brokers, int port) throws IOException {
        this.brokers = brokers;
        this.server = new ServerSocket(port);
        initialize();
    }

    private void initialize() {
        this.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket client = this.server.accept();
                LoadBalancerHandler handler = new LoadBalancerHandler(client, this);
            }
        } catch (Exception ex) {
            System.err.println("Thread.- Failed to accept connection: " + ex);
        }
    }

    public BrokerNode getRandomBroker() {
        int randNumber;
        Random randomGenerator = new Random();
        randNumber = randomGenerator.nextInt(brokers.length - 1);
        return brokers[randNumber];
    }

}
