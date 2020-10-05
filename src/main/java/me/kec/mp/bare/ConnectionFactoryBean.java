package me.kec.mp.bare;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.jms.ConnectionFactory;

import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;

@ApplicationScoped
public class ConnectionFactoryBean {
    @Produces
    ConnectionFactory factory() {
        return new ActiveMQJMSConnectionFactory(
                "tcp://localhost:61616", "kec", "kec");
    }
}