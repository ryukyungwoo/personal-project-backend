package kr.eddi.demo.domain.stock.service;

import kr.eddi.demo.domain.stock.controller.form.StockDataSaveRequestForm;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
            Stock stock = new Stock();
            stock.setTicker(tickerList.get(i));
            stock.setStockName(nameList.get(i));
            stockList.add(stock);
        }

        stockRepository.saveAll(stockList);
    }

    @Override
    public List<Stock> list() {
        return stockRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
