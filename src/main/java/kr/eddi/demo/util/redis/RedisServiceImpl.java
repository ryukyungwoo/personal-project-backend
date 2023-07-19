package kr.eddi.demo.util.redis;

import kr.eddi.demo.domain.chat.entity.Message;
import kr.eddi.demo.domain.chat.repository.MessageRepository;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{
    final private StringRedisTemplate redisTemplate;
    final private RedisTemplate<String, Object> redisTemplateObject;
    final private MessageRepository messageRepository;
    final private StockRepository stockRepository;
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


    public void saveMessageInRedis(Message message, String ticker) {
        Optional<Stock> maybeStock = stockRepository.findByTicker(ticker);
        log.info("maybeStock: " + maybeStock.get());
        if (maybeStock.isEmpty()){
            return;
        }
        Stock stock = maybeStock.get();
        message.setStock(stock);
        String messageKey = "message";
        ListOperations<String, Object> listOperations = redisTemplateObject.opsForList();
        listOperations.rightPush(messageKey, message);
        log.info(("listOperation: " + listOperations.size(messageKey)));
        final int maxSize = 10;
        Long currentSize = listOperations.size(messageKey);
        if (currentSize != null && currentSize >= maxSize) {
            List<Object> messagesToSave = listOperations.range(messageKey, 0, maxSize - 1);
            if (messagesToSave != null) {
                messageRepository.saveAll(messagesToSave.stream().map(obj -> (Message)obj).collect(Collectors.toList()));
                listOperations.trim(messageKey, maxSize, -1);
            }
        }
    }


    public Boolean isRefreshTokenExists(String token) {
        return getValueByKey(token) != null;
    }
}
