package kr.eddi.demo.domain.stock.controller.form.request;

import kr.eddi.demo.domain.stock.service.request.StockDataSaveRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockDataSaveRequestForm {
    private List<String> ticker;
    private List<String> stockName;

    public StockDataSaveRequest toStockDataSaveRequest () {
        return new StockDataSaveRequest (ticker, stockName);
    }
}
