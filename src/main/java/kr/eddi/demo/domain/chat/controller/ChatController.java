package kr.eddi.demo.domain.chat.controller;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import kr.eddi.demo.config.ApplicationContextProvider;
import kr.eddi.demo.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@ServerEndpoint(value = "/chat/{ticker}")
@Controller
@Slf4j
public class ChatController {
    final private ChatService chatService;
    public ChatController() {
        this.chatService = ApplicationContextProvider.getApplicationContext().getBean(ChatService.class);
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("ticker") String ticker) throws IOException {
        chatService.onOpen(session,ticker);
    }
    @OnClose
    public void onClose(Session session, @PathParam("ticker") String ticker) throws IOException {
        chatService.onClose(session, ticker);
    }
    @OnMessage
    public void onMessage(String content, Session session, @PathParam("ticker") String ticker) throws IOException {
        chatService.onMessage(content, session, ticker);
    }
}
