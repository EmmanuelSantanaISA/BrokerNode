package message;



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

    private String sourceIpAddress;
    private int sourcePort;
    private MessageType messageType;
    private String topic;
    private String message;

    public Message(String topic, String message) {
        this.topic = topic;
        this.message = message;
        this.messageType = MessageType.PUBLISH;
    }

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getIpSource() {
        return sourceIpAddress;
    }

    public void setIpSource(String ipSource) {
        this.sourceIpAddress = ipSource;
    }

    public int getPortSource() {
        return sourcePort;
    }

    public void setPortSource(int portSource) {
        this.sourcePort = portSource;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
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
