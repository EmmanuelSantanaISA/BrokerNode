/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscribe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import message.Message;

/**
 *
 * @author emmanuelsantana
 */
class SubscriberHandler extends Thread {

    private Socket socket;
    private ObjectInputStream ois;
    private Subscriber parent;

    public SubscriberHandler(Socket socket, Subscriber parent) throws IOException {
        this.socket = socket;
        this.parent = parent;
        this.ois = new ObjectInputStream(socket.getInputStream());
        initialize();
    }

    private void initialize() {
        this.start();
    }

    @Override
    public void run() {
        try {
            Message message = (Message) this.ois.readObject();
            this.parent.getCallback().receiveMessage(message);
            System.out.println(message.getMessage());
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Thread.- Failed to receive message: " + ex);
        }
    }
}
