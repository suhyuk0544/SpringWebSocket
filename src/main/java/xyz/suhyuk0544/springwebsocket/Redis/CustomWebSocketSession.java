package xyz.suhyuk0544.springwebsocket.Redis;


import jakarta.websocket.Session;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.adapter.StandardWebSocketSession;
import org.springframework.web.socket.*;
import org.springframework.web.socket.adapter.AbstractWebSocketSession;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public class CustomWebSocketSession extends StandardWebSocketSession implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    public CustomWebSocketSession(Session session, HandshakeInfo info, DataBufferFactory factory) {
        super(session, info, factory);
    }

    public CustomWebSocketSession(Session session, HandshakeInfo info, DataBufferFactory factory, Sinks.Empty<Void> completionSink) {
        super(session, info, factory, completionSink);
    }
}
