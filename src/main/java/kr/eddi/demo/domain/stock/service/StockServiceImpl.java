package kr.eddi.demo.domain.stock.service;

import kr.eddi.demo.config.FastApiConfig;
import kr.eddi.demo.domain.stock.controller.form.request.OpinionDataSaveRequestForm;
import kr.eddi.demo.domain.stock.controller.form.request.StockDataSaveRequestForm;
import kr.eddi.demo.domain.stock.controller.form.request.StockOCVASaveRequestForm;
import kr.eddi.demo.domain.stock.controller.form.response.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{

    final private StockRepository stockRepository;
    final private StockOpinionRepository stockOpinionRepository;
    final private StockOCVARepository stockOCVARepository;

    final private RestTemplate restTemplate;
    final private FastApiConfig fastApiConfig;

    @Override
    public void save() {
        log.info("save start");
        try {
            Optional<Stock> findHomepageStock = stockRepository.findByTicker("1");
            if (findHomepageStock.isEmpty()) {
                stockRepository.save(new Stock("1", "HomePage"));
            }
            String requestSaveUrl = fastApiConfig.getFastApiAppUrl() + "/stock/save-data";
            ResponseEntity<StockDataSaveRequestForm> response = restTemplate.getForEntity(requestSaveUrl, StockDataSaveRequestForm.class);


            List<String> tickerList = response.getBody().getTicker();
            List<String> nameList = response.getBody().getStockName();

            if (tickerList.size() != nameList.size()) {
                log.info("티커와 이름의 길이가 다릅니다");
            }

            List<Stock> newStockList = new ArrayList<>();
            for (int i = 0; i < tickerList.size(); i++) {
                Stock stock = new Stock(tickerList.get(i), nameList.get(i));
                newStockList.add(stock);
            }

            List<Stock> existingStockList = stockRepository.findAll();

            Set<Stock> stockToSave = new HashSet<>();

            for (Stock newStock : newStockList) {
                boolean exists = false;
                for (Stock existingStock : existingStockList) {
                    if (Objects.equals(existingStock.getTicker(), newStock.getTicker())) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    stockToSave.add(newStock);
                }
            }

            if (!stockToSave.isEmpty()) {
                stockRepository.saveAll(stockToSave);
            }

        } catch (Exception e) {
            log.error("Error during stock initialization or save", e);
        }
        log.info("save end");

    }

    @Override
    public StockNameResponseForm getStockName(String ticker) {
        log.info("getStockName start");

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
        log.info("getStockName end");

        return responseForm;
    }

    @Override
    public void saveOpinion() {
        log.info("saveOpinion start");

        try {
            String requestSaveUrl = fastApiConfig.getFastApiAppUrl() + "/opinion-mining/";
            List<Stock> stockList = stockRepository.findAll();
            for (Stock stock : stockList) {
                if ("1".equals(stock.getTicker())) {
                    continue;
                }
                ResponseEntity<OpinionDataSaveRequestForm> response = restTemplate.getForEntity(requestSaveUrl + stock.getTicker(), OpinionDataSaveRequestForm.class);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("saveOpinion end");

    }


    @Override
    public void saveOCVAData() {
        log.info("saveOCVAData start");

        try {
            String requestSaveUrl = fastApiConfig.getFastApiAppUrl() + "/stock/list/";

            ResponseEntity<List<StockOCVASaveRequestForm>> responseForm = restTemplate.exchange(requestSaveUrl + "시가/False",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<StockOCVASaveRequestForm>>() {});

            List<StockOCVASaveRequestForm> stockForms = responseForm.getBody();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("saveOCVAData end");

    }

    @Override
    public List<StockOCVAResponseForm> list(String OCVA, String ascending, int pageNumber) {
        log.info("list start");

        final int PAGE_NUMBER = pageNumber - 1;
        final int PAGE_SIZE = 30;
        Sort sort;
        if ("asc".equals(ascending)){
            sort = Sort.by(Sort.Order.asc(OCVA));
        } else {
            sort = Sort.by(Sort.Order.desc(OCVA));
        }

        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE, sort);
        Page<StockOCVA> stockOCVAPage = stockOCVARepository.findAll(pageable);

        List<StockOCVAResponseForm> OCVAList = new ArrayList<>();

        for (StockOCVA stockOCVA : stockOCVAPage.getContent()) {
            StockOCVAResponseForm responseForm = new StockOCVAResponseForm().builder()
                    .ticker(stockOCVA.getTicker())
                    .stockName(stockOCVA.getStockName())
                    .open(stockOCVA.getOpen())
                    .close(stockOCVA.getClose())
                    .rangeValue(stockOCVA.getRangeValue())
                    .fluctuationRate(stockOCVA.getFluctuationRate())
                    .volume(stockOCVA.getVolume())
                    .amount(stockOCVA.getAmount())
                    .build();
            OCVAList.add(responseForm);
        }
        log.info("list end");

        return OCVAList;

    }

    @Override
    public List<StockOpinionResponseForm> opinionList(String sortItem, String ascending, int pageNumber) {
        log.info("opinionList start");

        final int PAGE_NUMBER = pageNumber - 1;
        final int PAGE_SIZE = 30;
        Sort sort;
        if ("asc".equals(ascending)){
            sort = Sort.by(Sort.Order.asc(sortItem));
        } else {
            sort = Sort.by(Sort.Order.desc(sortItem));
        }
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE, sort);
        Page<StockOpinion> stockOpinionPage = stockOpinionRepository.findAll(pageable);

//        long totalElements = stockOpinionRepository.count();

        List<StockOpinionResponseForm> opinionList = new ArrayList<>();

        for (StockOpinion stockOpinion : stockOpinionPage.getContent()) {
            Stock stock = stockRepository.findByTicker(stockOpinion.getId()).get();
            StockOpinionResponseForm responseForm = new StockOpinionResponseForm().builder()
                    .ticker(stock.getTicker())
                    .stockName(stock.getStockName())
                    .positiveCount(stockOpinion.getPositiveCount())
                    .negativeCount(stockOpinion.getNegativeCount())
                    .naturalCount(stockOpinion.getNaturalCount())
                    .totalSentimentScore(stockOpinion.getTotalSentimentScore())
//                    .totalPageNum((int) Math.ceil((double) totalElements / PAGE_SIZE))
                    .build();
            opinionList.add(responseForm);
        }
        log.info("opinionList end");

        return opinionList;
    }

    @Override
    public StockPageNumResponseForm stockPageNumResponse() {
        log.info("stockPageNumResponse start");
        final int PAGE_SIZE = 30;
        long totalElements = stockOCVARepository.count();
        StockPageNumResponseForm responseForm = new StockPageNumResponseForm();
        responseForm.setPageNum((int) Math.ceil((double) totalElements / PAGE_SIZE));
        log.info("stockPageNumResponse end");
        return responseForm;

    }

    @Override
    public OpinionPageNumResponseForm opinionPageNumResponse() {
        log.info("opinionPageNumResponse start");
        final int PAGE_SIZE = 30;
        long totalElements = stockOpinionRepository.count();
        OpinionPageNumResponseForm responseForm = new OpinionPageNumResponseForm();
        responseForm.setPageNum((int) Math.ceil((double) totalElements / PAGE_SIZE));
        log.info("opinionPageNumResponse start");

        return responseForm;
    }

}