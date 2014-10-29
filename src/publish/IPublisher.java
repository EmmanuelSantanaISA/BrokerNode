package publish;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author emmanuelsantana
 */
public interface IPublisher {

    void publish(String messageStr);

    void close();
}
