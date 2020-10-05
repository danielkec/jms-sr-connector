package me.kec.mp.bare;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class MessageConsumer {

    @Incoming("IncomingQueue")
    public void consume(String message) {
        System.out.println("Message: " + message);
    }

}