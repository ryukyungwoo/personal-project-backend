package kr.eddi.demo.util.redis;

import kr.eddi.demo.domain.chat.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{
    final private StringRedisTemplate redisTemplate;

    @Override
    public void setKeyAndValue(String token, Long accountId) {
        String accountIdToString = String.valueOf(accountId);
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(token, accountIdToString, Duration.ofMinutes(100));
    }

    @Override
    public Long getValueByKey(String token) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        String tmpAccountId = value.get(token);
        Long accountId;

        if (tmpAccountId == null) {
            accountId = null;
        } else {
            accountId = Long.parseLong(tmpAccountId);
        }

        return accountId;
    }

    @Override
    public void deleteByKey(String token) {
        redisTemplate.delete(token);
    }
    @Override
    public void saveMessageToBuffer(String messageId, Message message) {
        // Redis에 메시지를 저장합니다.
        redisTemplate.opsForList().rightPush(messageId, message);
    }

    @Override
    public List<Message> getMessagesFromBuffer(String messageId) {
        // Redis에서 버퍼로부터 메시지들을 가져옵니다.
        List<Message> messages = new ArrayList<>();
        Long size = redisTemplate.opsForList().size(messageId);
        for (int i = 0; i < size; i++) {
            messages.add(redisTemplate.opsForList().index(messageId, i));
        }
        return messages;
    }

    @Override
    public int getBufferSize(String messageId) {
        // Redis의 버퍼 크기를 반환합니다.
        Long size = redisTemplate.opsForList().size(messageId);
        return size.intValue();
    }

    @Override
    public void clearMessageBuffer(String messageId) {
        // Redis의 버퍼를 비웁니다.
        redisTemplate.delete(messageId);
    }

    public Boolean isRefreshTokenExists(String token) {
        return getValueByKey(token) != null;
    }
}
