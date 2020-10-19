package me.kec.mp.bare;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MessageConsumer {

    @Incoming("IncomingQueue1")
    public void consume1(String message) {
        System.out.println("ms1: " + message);
    }

    @Incoming("IncomingQueue2")
    public void consume2(String message) {
        System.out.println("ms2: " + message);
    }

}