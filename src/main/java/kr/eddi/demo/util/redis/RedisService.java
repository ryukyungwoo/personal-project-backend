package kr.eddi.demo.util.redis;

import kr.eddi.demo.domain.chat.entity.Message;

import java.util.List;

public interface RedisService {
    void setKeyAndValue(String token, Long accountId);
    Long getValueByKey(String token);
    void deleteByKey(String token);
    void saveMessageInRedis(Message message, String ticker);
}
