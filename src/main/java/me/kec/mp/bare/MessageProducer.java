package me.kec.mp.bare;

import io.helidon.common.reactive.BufferedEmittingPublisher;
import io.helidon.common.reactive.Multi;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.FlowAdapters;
import org.reactivestreams.Publisher;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MessageProducer {

    @Inject
    private BufferedEmittingPublisher<String> emitter;

    @Produces
    @ApplicationScoped
    @Named("outgoingEmitter")
    BufferedEmittingPublisher<String> emitter() {
        return BufferedEmittingPublisher.create();
    }

    @Outgoing("OutgoingQueue")
    public PublisherBuilder<Message<String>> produce() {
        return ReactiveStreams.fromPublisher(FlowAdapters.toPublisher(emitter))
                .map(Message::of);
    }

    @Outgoing("To-UDD")
    public Publisher<Message<String>> ping() {
        return FlowAdapters.toPublisher(
                Multi.interval(300, TimeUnit.MILLISECONDS, Executors.newScheduledThreadPool(2))
                        .map(i -> "ping-" + i)
                        .map(Message::of)
        );
    }
}
