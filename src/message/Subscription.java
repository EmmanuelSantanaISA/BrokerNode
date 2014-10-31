/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

/**
 *
 * @author emmanuelsantana
 */
public class Subscription {
    private String fromIpAddress;
    private String ipAddress;
    private int port;
    private String topic;
    private boolean unSubscribe;

    public String getFromIpAddress() {
        return fromIpAddress;
    }

    public void setFromIpAddress(String fromIpAddress) {
        this.fromIpAddress = fromIpAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isUnSubscribe() {
        return unSubscribe;
    }

    public void setUnSubscribe(boolean unSubscribe) {
        this.unSubscribe = unSubscribe;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
}
