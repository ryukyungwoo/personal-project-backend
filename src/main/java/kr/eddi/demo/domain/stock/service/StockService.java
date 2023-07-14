package kr.eddi.demo.domain.stock.service;

import kr.eddi.demo.domain.stock.controller.form.response.StockNameResponseForm;
import kr.eddi.demo.domain.stock.entity.Stock;

import java.util.List;

public interface StockService {
    void save(String requestSaveUrl);

    List<Stock> list();

    List<Stock> getStockList(String requestSaveUrl);

    StockNameResponseForm getStockName(String ticker);
}
