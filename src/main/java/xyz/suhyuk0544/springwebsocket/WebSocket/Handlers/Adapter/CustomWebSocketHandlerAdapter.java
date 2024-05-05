package xyz.suhyuk0544.springwebsocket.WebSocket.Handlers.Adapter;

import jakarta.websocket.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.socket.*;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.adapter.standard.WebSocketToStandardExtensionAdapter;
import org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator;
import xyz.suhyuk0544.springwebsocket.Redis.Room.RoomService;
import xyz.suhyuk0544.springwebsocket.WebSocket.Session.CustomWebSocketSession;

import java.nio.ByteBuffer;
import java.util.HashMap;

@Slf4j
public class CustomWebSocketHandlerAdapter extends Endpoint {

    private final Log logger = LogFactory.getLog(CustomWebSocketHandlerAdapter.class);

    private final WebSocketHandler handler;

    private final StandardWebSocketSession wsSession;

    public CustomWebSocketHandlerAdapter(WebSocketHandler handler, StandardWebSocketSession wsSession) {
        Assert.notNull(handler, "WebSocketHandler must not be null");
        Assert.notNull(wsSession, "WebSocketSession must not be null");
        this.handler = handler;
        this.wsSession = wsSession;
    }

    @Override
    public void onOpen(final Session session, EndpointConfig config) {
        this.wsSession.initializeNativeSession(session);

        if (this.handler.supportsPartialMessages()) {
            session.addMessageHandler(new MessageHandler.Partial<String>() {
                @Override
                public void onMessage(String message, boolean isLast) {
                    handleTextMessage(session, message, isLast);
                }
            });
            session.addMessageHandler(new MessageHandler.Partial<ByteBuffer>() {
                @Override
                public void onMessage(ByteBuffer message, boolean isLast) {
                    handleBinaryMessage(session, message, isLast);
                }
            });
        }
        else {
            session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    handleTextMessage(session, message, true);
                }
            });
            session.addMessageHandler(new MessageHandler.Whole<ByteBuffer>() {
                @Override
                public void onMessage(ByteBuffer message) {
                    handleBinaryMessage(session, message, true);
                }
            });
        }
        session.addMessageHandler(new MessageHandler.Whole<jakarta.websocket.PongMessage>() {
            @Override
            public void onMessage(jakarta.websocket.PongMessage message) {
                handlePongMessage(session, message.getApplicationData());
            }
        });
        try {
//            roomService.findRoomById(session.getPathParameters().get("roomId"));
            this.handler.afterConnectionEstablished(this.wsSession);
        }
        catch (Exception ex) {
            ExceptionWebSocketHandlerDecorator.tryCloseWithError(this.wsSession, ex, logger);
        }
    }

    private void handleTextMessage(Session session, String payload, boolean isLast) {
        TextMessage textMessage = new TextMessage(payload, isLast);

        try {
            this.handler.handleMessage(this.wsSession, textMessage);
        }
        catch (Exception ex) {
            ExceptionWebSocketHandlerDecorator.tryCloseWithError(this.wsSession, ex, logger);
        }
    }

    private void handleBinaryMessage(Session session, ByteBuffer payload, boolean isLast) {
        BinaryMessage binaryMessage = new BinaryMessage(payload, isLast);
        try {
            this.handler.handleMessage(this.wsSession, binaryMessage);
        }
        catch (Exception ex) {
            ExceptionWebSocketHandlerDecorator.tryCloseWithError(this.wsSession, ex, logger);
        }
    }

    private void handlePongMessage(Session session, ByteBuffer payload) {
        PongMessage pongMessage = new PongMessage(payload);
        try {
            this.handler.handleMessage(this.wsSession, pongMessage);
        }
        catch (Exception ex) {
            ExceptionWebSocketHandlerDecorator.tryCloseWithError(this.wsSession, ex, logger);
        }
    }

    @Override
    public void onClose(Session session, CloseReason reason) {
        CloseStatus closeStatus = new CloseStatus(reason.getCloseCode().getCode(), reason.getReasonPhrase());
        try {
            this.handler.afterConnectionClosed(this.wsSession, closeStatus);
        }
        catch (Exception ex) {
            if (logger.isWarnEnabled()) {
                logger.warn("Unhandled on-close exception for " + this.wsSession, ex);
            }
        }
    }

    @Override
    public void onError(Session session, Throwable exception) {
        try {
            this.handler.handleTransportError(this.wsSession, exception);
        }
        catch (Exception ex) {
            ExceptionWebSocketHandlerDecorator.tryCloseWithError(this.wsSession, ex, logger);
        }
    }
}
