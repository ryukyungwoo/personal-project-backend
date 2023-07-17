package kr.eddi.demo.domain.stock.service;

import kr.eddi.demo.domain.stock.controller.form.request.StockDataSaveRequestForm;
import kr.eddi.demo.domain.stock.controller.form.response.StockNameResponseForm;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{

    final private StockRepository stockRepository;

    final private RestTemplate restTemplate;

    @Override
    public void save(String requestSaveUrl) {

        ResponseEntity<StockDataSaveRequestForm> response = restTemplate.getForEntity(requestSaveUrl, StockDataSaveRequestForm.class);

        log.info("response" + response);

        List<String> tickerList = response.getBody().getTicker();
        List<String> nameList = response.getBody().getStockName();

        if (tickerList.size() != nameList.size()) {
            log.info("티커와 이름의 길이가 다릅니다");
        }

        List<Stock> stockList = new ArrayList<>();

        for (int i = 0; i < tickerList.size(); i++) {
            Stock stock = new Stock(tickerList.get(i), nameList.get(i));
            stockList.add(stock);
        }

        stockRepository.saveAll(stockList);
    }

    @Override
    public List<Stock> list() {

        return stockRepository.findAll(Sort.by(Sort.Direction.DESC, "ticker"));
    }

    @Override
    public List<Stock> getStockList(String requestSaveUrl) {
        ResponseEntity<StockDataSaveRequestForm> response = restTemplate.getForEntity(requestSaveUrl, StockDataSaveRequestForm.class);

        log.info("response" + response);

        List<String> tickerList = response.getBody().getTicker();
        List<String> nameList = response.getBody().getStockName();

        if (tickerList.size() != nameList.size()) {
            log.info("티커와 이름의 길이가 다릅니다");
            throw new IllegalArgumentException("Ticker and name lists have different sizes.");
        }

        List<Stock> stockList = new ArrayList<>();

        for (int i = 0; i < tickerList.size(); i++) {
            Stock stock = new Stock();
            stock.setTicker(tickerList.get(i));
            stock.setStockName(nameList.get(i));
            stockList.add(stock);
        }

        return stockList;
    }

    @Override
    public StockNameResponseForm getStockName(String ticker) {
        Optional<Stock> maybeStock = stockRepository.findByTicker(ticker);
        if(maybeStock.isEmpty()) {
            log.info("잘못된 ticker 이거나 없는 주식 입니다");
            return null;
        }
        Stock stock = maybeStock.get();
        StockNameResponseForm responseForm = StockNameResponseForm.builder()
                                                .stockName(stock.getStockName())
                                                .ticker(stock.getTicker())
                                                .build();
        return responseForm;
    }
}
