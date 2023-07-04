package kr.eddi.demo.domain.stock.service.request;

import kr.eddi.demo.domain.stock.entity.Stock;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class StockDataSaveRequest {
    final private List<String> ticker;
    final private List<String> stockName;

}
