package message;

import broker.Subscription;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author emmanuelsantana
 */
public class Message {

    private String ipSource;
    private int portSource;
    private MessageType messageType;
    private Subscription subscription;
    private String topic;
    private String message;

    public Message(String topic, String message) {
        this.topic = topic;
        this.message = message;
        this.messageType = MessageType.PUBLISH;
    }

    public Message(Subscription subscription, MessageType messageType) {
        this.subscription = subscription;
        this.messageType = messageType;
    }

    public String getIpSource() {
        return ipSource;
    }

    public void setIpSource(String ipSource) {
        this.ipSource = ipSource;
    }

    public int getPortSource() {
        return portSource;
    }

    public void setPortSource(int portSource) {
        this.portSource = portSource;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public enum MessageType {

        BROKER,
        PUBLISH,
        SUBSCRIBE
    }

}
