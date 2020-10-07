package me.kec.mp.bare;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

@ApplicationScoped
public class ConnectionFactoryBean {

    // Defines the JNDI context factory.
    public final static String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public static String url = "t3://localhost:7001";
    public static String JMS_FACTORY = "jms/TestConnectionFactory";

    @Produces
    ConnectionFactory factory() throws NamingException {
        return (ConnectionFactory) getInitialContext().lookup(JMS_FACTORY);
    }

    public InitialContext getInitialContext()
            throws NamingException {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, url);
        return new InitialContext(env);
    }
}