package kr.eddi.demo.domain.chat.service;

import jakarta.websocket.Session;

import java.io.IOException;

public interface ChatService {
    void onOpen(Session session, String ticker) throws IOException;

    void onClose(Session session, String ticker) throws IOException;

    void onMessage(String content, Session session, String ticker) throws IOException;
}
