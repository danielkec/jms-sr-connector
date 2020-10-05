package me.kec.mp.bare;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import io.helidon.common.reactive.BufferedEmittingPublisher;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.FlowAdapters;

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
}
