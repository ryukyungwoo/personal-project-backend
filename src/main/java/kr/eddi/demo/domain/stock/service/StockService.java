package kr.eddi.demo.domain.stock.service;

import kr.eddi.demo.domain.stock.controller.form.response.*;
import kr.eddi.demo.domain.stock.entity.Stock;

import java.util.List;

public interface StockService {
    void save();

    StockNameResponseForm getStockName(String ticker);

    void saveOpinion();

    void saveOCVAData();

    List<StockOCVAResponseForm> list(String OCVA, String ascending, int pageNumber);

    List<StockOpinionResponseForm> opinionList(String sortItem, String ascending, int pageNumber);

    StockPageNumResponseForm stockPageNumResponse();

    OpinionPageNumResponseForm opinionPageNumResponse();
}
