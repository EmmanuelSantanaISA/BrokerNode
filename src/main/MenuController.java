/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import broker.BrokerNode;
import java.awt.EventQueue;
import java.io.IOException;
import loadbalancer.LoadBalancer;
import message.Subscription;

/**
 *
 * @author emmanuelsantana
 */
public class MenuController {

    private MenuView view;
    LoadBalancer lb;

    public MenuController(MenuView view) {
        this.view = view;
    }

    public void initializeBrokers() {
        int loadBalancerPort = 7000;
        String ipAddress = "localhost";
        BrokerNode[] brokers = new BrokerNode[6];
        for (int i = 0; i < 6; i++) {
            BrokerNode broker = null;
            try {
                broker = new BrokerNode(ipAddress, loadBalancerPort + i);
                brokers[i] = broker;
            } catch (IOException ex) {
                System.err.println("Main: " + ex.getMessage());
            }
        }
        brokers[0].addNeighbor(brokers[1]);
        brokers[0].addNeighbor(brokers[3]);
        brokers[0].addNeighbor(brokers[4]);
        brokers[1].addNeighbor(brokers[0]);
        brokers[1].addNeighbor(brokers[2]);
        brokers[1].addNeighbor(brokers[4]);
        brokers[1].addNeighbor(brokers[5]);
        brokers[2].addNeighbor(brokers[1]);
        brokers[2].addNeighbor(brokers[5]);
        brokers[3].addNeighbor(brokers[0]);
        brokers[3].addNeighbor(brokers[4]);
        brokers[4].addNeighbor(brokers[3]);
        brokers[4].addNeighbor(brokers[0]);
        brokers[4].addNeighbor(brokers[1]);
        brokers[4].addNeighbor(brokers[5]);
        brokers[5].addNeighbor(brokers[2]);
        brokers[5].addNeighbor(brokers[1]);
        brokers[5].addNeighbor(brokers[4]);

        try {
            lb = new LoadBalancer(brokers, loadBalancerPort);
        } catch (IOException ex) {
            System.err.println("Error al cargar el balanceador: " + ex.getMessage());
        }
    }

    public void newPublisher() {

    }

    public void newSubscriber() {
        EventQueue.invokeLater(() -> {
            new SubscriberView().setVisible(true);
        });
    }
}
