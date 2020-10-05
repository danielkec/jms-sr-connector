package me.kec.mp.bare;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

import io.smallrye.reactive.messaging.jms.JmsConnector;

// Workaround for missing bean.xml in smallrye jms connector jar
// needs service locator META-INF/services/javax.enterprise.inject.spi.Extension
public class SmallryeJMSCompatibilityExtension implements Extension {
    void readAllConfigurations(final @Observes BeforeBeanDiscovery bdd) {
        bdd.addAnnotatedType(JmsConnector.class, "smallrye.jms-connector");
    }
}
