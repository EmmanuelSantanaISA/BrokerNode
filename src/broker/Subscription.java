/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package broker;

/**
 *
 * @author emmanuelsantana
 */
public class Subscription {

    private String ipAddress;
    private int port;
    private String topic;

    @Override
    public String toString() {
        return "Ip Address: " + ipAddress + "\nPort: " + port + "\nTopic: " + topic;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}
