package xyz.suhyuk0544.springwebsocket.WebSocket.Strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Extension;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.adapter.standard.StandardWebSocketHandlerAdapter;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.adapter.standard.WebSocketToStandardExtensionAdapter;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.standard.AbstractStandardUpgradeStrategy;
import org.springframework.web.socket.server.standard.StandardWebSocketUpgradeStrategy;
import xyz.suhyuk0544.springwebsocket.WebSocket.Handlers.Adapter.CustomWebSocketHandlerAdapter;
import xyz.suhyuk0544.springwebsocket.WebSocket.Session.CustomWebSocketSession;

import java.net.InetSocketAddress;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class AbstractCustomUpgradeStrategy extends StandardWebSocketUpgradeStrategy {


    @Override
    public void upgrade(ServerHttpRequest request, ServerHttpResponse response, String selectedProtocol, List<WebSocketExtension> selectedExtensions, Principal user, WebSocketHandler wsHandler, Map<String, Object> attrs) throws HandshakeFailureException {

        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String,Object> pathParam = null;
        HttpHeaders headers = request.getHeaders();
        InetSocketAddress localAddr = null;
        try {
            localAddr = request.getLocalAddress();
//            pathParam = objectMapper.convertValue(request.getURI().getQuery(),Map.class);
        }
        catch (Exception ex) {
            // Ignore
        }
        InetSocketAddress remoteAddr = null;
        try {
            remoteAddr = request.getRemoteAddress();
        }
        catch (Exception ex) {
            // Ignore
        }


        StandardWebSocketSession session = new StandardWebSocketSession(headers, attrs, localAddr, remoteAddr, user);
        CustomWebSocketHandlerAdapter endpoint = new CustomWebSocketHandlerAdapter(wsHandler, session);

        List<Extension> extensions = new ArrayList<>();
        for (WebSocketExtension extension : selectedExtensions) {
            extensions.add(new WebSocketToStandardExtensionAdapter(extension));
        }

        upgradeInternal(request, response, selectedProtocol, extensions, endpoint);

    }
}
