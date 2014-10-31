/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package publish;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import message.Message;

/**
 *
 * @author emmanuelsantana
 */
public class Publisher extends Thread implements IPublisher {

    private Socket socket = null;
    private ObjectOutputStream oos = null;
    private String topic;

    public Publisher(String host, int port, String topic) throws UnknownHostException, IOException {
        this.topic = topic;
        this.socket = new Socket(host, port);
        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
    }

    @Override
    public void close() {
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException ex) {
            System.err.println("Failed to close socket: " + ex.getMessage());
        }
    }

    @Override
    public void publish(String messageStr) {
        Message message = new Message(topic, messageStr);
        try {
            this.oos.writeObject(message);
        } catch (IOException ex) {
            System.err.println("Failet to publish: " + ex.getMessage());
        }
        close();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
