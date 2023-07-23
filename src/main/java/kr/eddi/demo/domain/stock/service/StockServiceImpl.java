package kr.eddi.demo.domain.stock.service;

import kr.eddi.demo.domain.stock.controller.form.request.OpinionDataSaveRequestForm;
import kr.eddi.demo.domain.stock.controller.form.request.StockDataSaveRequestForm;
import kr.eddi.demo.domain.stock.controller.form.request.StockOCVASaveRequestForm;
import kr.eddi.demo.domain.stock.controller.form.response.StockNameResponseForm;
import kr.eddi.demo.domain.stock.entity.Stock;
import kr.eddi.demo.domain.stock.entity.StockOCVA;
import kr.eddi.demo.domain.stock.entity.StockOpinion;
import kr.eddi.demo.domain.stock.repository.StockOCVARepository;
import kr.eddi.demo.domain.stock.repository.StockOpinionRepository;
import kr.eddi.demo.domain.stock.repository.StockRepository;
import kr.eddi.demo.domain.stock.service.request.StockOCVASaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
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
    final private StockOpinionRepository stockOpinionRepository;
    final private StockOCVARepository stockOCVARepository;

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

    @Override
    public void getOpinionTest() {
        String requestSaveUrl = "http://localhost:8000/opinion-mining/";
        ResponseEntity<OpinionDataSaveRequestForm> response = restTemplate.getForEntity(requestSaveUrl + 950210, OpinionDataSaveRequestForm.class);

        Optional<Stock> maybeStock = stockRepository.findByTicker("950210");

        if (maybeStock.isEmpty()) {
            log.info("잘못된 ticker 이거나 없는 주식입니다");
        }

        Stock stock = maybeStock.get();
        StockOpinion receivedStockOpinion = response.getBody().toOpinionDataSaveRequest().toStockOpinionMining();

        StockOpinion stockOpinion = new StockOpinion().builder()
                                        .totalSentimentScore(receivedStockOpinion.getTotalSentimentScore())
                                        .positiveCount(receivedStockOpinion.getPositiveCount())
                                        .negativeCount(receivedStockOpinion.getNegativeCount())
                                        .naturalCount(receivedStockOpinion.getNaturalCount())
                                        .build();

        stockOpinion.setStock(stock);
        stockOpinion.setId(stock.getTicker());
        stockOpinionRepository.save(stockOpinion);
    }

    @Override
    public void getOCVAData() {
        String requestSaveUrl = "http://localhost:8000/stock/list/";

        // ResponseEntity의 타입을 'List<StockOCVASaveRequest>'로 변경하십시오.
        ResponseEntity<List<StockOCVASaveRequestForm>> responseForm = restTemplate.exchange(requestSaveUrl + "시가/False",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<StockOCVASaveRequestForm>>() {});

        List<StockOCVASaveRequestForm> stockForms = responseForm.getBody();

        // 리스트를 반복 처리하십시오.
        for (StockOCVASaveRequestForm stockForm : stockForms) {
            StockOCVA receivedStockOCVA = stockForm.toStockOCVASaveRequest().toStockOCVA();
            String stockName = stockRepository.findByTicker(receivedStockOCVA.getTicker()).get().getStockName();

            StockOCVA stockOCVA = new StockOCVA().builder()
                    .ticker(receivedStockOCVA.getTicker())
                    .stockName(stockName)
                    .open(receivedStockOCVA.getOpen())
                    .close(receivedStockOCVA.getClose())
                    .rangeValue(receivedStockOCVA.getRangeValue())
                    .fluctuationRate(receivedStockOCVA.getFluctuationRate())
                    .amount(receivedStockOCVA.getAmount())
                    .volume(receivedStockOCVA.getVolume())
                    .build();

            stockOCVARepository.save(stockOCVA);
        }
    }

}