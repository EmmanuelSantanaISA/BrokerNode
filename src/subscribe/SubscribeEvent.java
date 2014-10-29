/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscribe;

import message.Message;

/**
 *
 * @author emmanuelsantana
 */
public interface SubscribeEvent {

    public void receiveMessage(Message message);
}
