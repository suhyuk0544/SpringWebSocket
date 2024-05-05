package xyz.suhyuk0544.springwebsocket.WebSocket.Session;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Extension;
import jakarta.websocket.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.*;
import org.springframework.web.socket.adapter.AbstractWebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardToWebSocketExtensionAdapter;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CustomWebSocketSession extends StandardWebSocketSession {

    private Map<String,String> pathParameters;

    public CustomWebSocketSession(HttpHeaders headers, Map<String, Object> attributes, InetSocketAddress localAddress, InetSocketAddress remoteAddress) {
        super(headers, attributes, localAddress, remoteAddress);
    }

    public CustomWebSocketSession(HttpHeaders headers, Map<String, Object> attributes, InetSocketAddress localAddress, InetSocketAddress remoteAddress, Principal user) {
        super(headers, attributes, localAddress, remoteAddress, user);
    }

    public Map<String,String> getPathParameters(){
        return this.pathParameters;
    }

    @Override
    public void initializeNativeSession(Session session) {
        super.initializeNativeSession(session);
        this.pathParameters = session.getPathParameters();
    }

    @Override
    public String toString() {
        return "CustomWebSocketSession{" +
                "pathParameters=" + pathParameters +
                '}';
    }
}
