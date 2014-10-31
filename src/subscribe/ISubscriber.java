/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscribe;

import message.Subscription;

/**
 *
 * @author emmanuelsantana
 */
public interface ISubscriber {

    public void subscribe(Subscription subscription);
    public void unSubscribe(Subscription subscription);
}
