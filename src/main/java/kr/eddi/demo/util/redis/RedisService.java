package kr.eddi.demo.util.redis;

import kr.eddi.demo.domain.chat.entity.Message;

import java.util.List;

public interface RedisService {
    void setKeyAndValue(String token, Long accountId);
    Long getValueByKey(String token);
    void deleteByKey(String token);
    void saveMessageToBuffer(String messageId, Message message);
    List<Message> getMessagesFromBuffer(String messageId);
    int getBufferSize(String messageId);
    void clearMessageBuffer(String messageId);
}
