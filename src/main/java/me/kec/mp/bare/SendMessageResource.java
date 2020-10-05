
package me.kec.mp.bare;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.helidon.common.reactive.BufferedEmittingPublisher;

@Path("/send")
public class SendMessageResource {

    BufferedEmittingPublisher<String> emitter;

    @Inject
    public SendMessageResource(final BufferedEmittingPublisher<String> emitter) {
        this.emitter = emitter;
    }

    @GET
    @Path("/{msg}")
    @Produces(MediaType.APPLICATION_JSON)
    public String send(@PathParam("msg") String msg) {
        emitter.emit(msg);
        return "SENT";
    }
}
