package kr.eddi.demo.domain.chat.service;

import jakarta.websocket.Session;
import kr.eddi.demo.domain.account.entity.Account;
import kr.eddi.demo.domain.account.repository.AccountRepository;
import kr.eddi.demo.domain.chat.entity.Message;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import kr.eddi.demo.util.jwt.JwtUtils;
import kr.eddi.demo.util.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{


        final static private Map<String, Set<Session>> ROOMS = Collections.synchronizedMap(new HashMap<>());
        final private RedisService redisService;
        final private JwtUtils jwtUtils;
        final private AccountRepository accountRepository;
        final private StockRepository stockRepository;

        @Override
        public void onOpen(Session session, String ticker) throws IOException {
            Map<String, List<String>> requestParams = session.getRequestParameterMap();

            String clientIpAddress = requestParams.get("clientIp").get(0);
            String accessToken = requestParams.get("accessToken").get(0);

            if (!accessToken.equals("undefined")) {
                String getEmail = jwtUtils.getEmail(accessToken);
                Optional<Account> maybeAccount = accountRepository.findByEmailWithLazy(getEmail);
                if (maybeAccount.isEmpty()) {
                    return;
                }
                Account account = maybeAccount.get();
                String nickname = account.getAccountNickname().getNickname();
                session.getUserProperties().put("nickname", nickname);
                session.getUserProperties().put("email", getEmail);
            } else {
                session.getUserProperties().put("clientIp", clientIpAddress);

            }

            Set<Session> clients = ROOMS.computeIfAbsent(ticker, key -> Collections.synchronizedSet(new HashSet<>()));
            clients.add(session);

            for (Session client : clients) {
                Map<String, Object> userProperties = session.getUserProperties();
                if (userProperties.get("nickname") != null) {
                    client.getBasicRemote().sendText(userProperties.get("nickname")+ "님이 입장하셨습니다");
                }else {

                    client.getBasicRemote().sendText("익명" + getFirstThreeSegments((String) userProperties.get("clientIp"))  + "님이 입장하셨습니다");                }
            }
        }

        @Override
        public void onClose(Session session, String ticker) throws IOException {
            Set<Session> clients = ROOMS.get(ticker);
            if (clients != null) {
                clients.remove(session);
                for (Session client : clients) {
                    Map<String, Object> userProperties = session.getUserProperties();
                    if (userProperties.get("nickname") != null) {
                        client.getBasicRemote().sendText(userProperties.get("nickname")+ "님이 하셨습니다");
                    }else {
                        client.getBasicRemote().sendText("익명" + getFirstThreeSegments((String) userProperties.get("clientIp"))  + "님이 퇴장하셨습니다");
                    }
                }
            }
        }

        @Override
        public void onMessage(String content, Session session, String ticker) throws IOException {
            if (content == null) {
                return;
            }
            Set<Session> clients = ROOMS.get(ticker);
            if (clients != null) {

                Optional<Stock> maybeStock = stockRepository.findByTicker(ticker);
                if (maybeStock.isEmpty()){
                    return;
                }
                Stock stock = maybeStock.get();

                Map<String, Object> userProperties = session.getUserProperties();
                if (userProperties.get("nickname") != null) {
                    userProperties.get("email");
                    Optional<Account> maybeAccount = accountRepository.findByEmailWithLazy((String) userProperties.get("email"));
                    if (maybeAccount.isEmpty()) {
                        return;
                    }
                    Account account = maybeAccount.get();
                    Message message = new Message();
                    message.setAccount(account);
                    message.setStock(stock);
                    message.setContent(content);

                    for (Session client : clients) {
                        client.getBasicRemote().sendText(account.getAccountNickname().getNickname() + ": " + content);
                    }
                    redisService.saveMessageInRedis(message, ticker);
                }else {
                    Message message = new Message();
                    message.setClientIp((String) userProperties.get("clientIp"));
                    message.setStock(stock);
                    message.setContent(content);

                    for (Session client : clients) {
                        client.getBasicRemote().sendText( "익명" + getFirstThreeSegments((String) userProperties.get("clientIp"))  + ": " + content);
                    }
                    redisService.saveMessageInRedis(message, ticker);
                }


            }

        }
    public String getFirstThreeSegments(String ip) {
        if (ip.contains(".")) {
            // IPv4
            String[] parts = ip.split("\\.");
            return String.join(".", Arrays.copyOfRange(parts, 0, 2));
        } else {
            // IPv6
            String[] segments = ip.split(":");
            return String.join(":", Arrays.copyOfRange(segments, 0, 3));
        }
    }
}
