package kr.eddi.demo.domain.stock.controller.form.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.eddi.demo.domain.stock.service.request.OpinionDataSaveRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpinionDataSaveRequestForm {
    @JsonProperty("total_sentiment_score")
    private int totalSentimentScore;
    @JsonProperty("total_positive_count")
    private int positiveCount;
    @JsonProperty("total_negative_count")
    private int negativeCount;
    @JsonProperty("total_neutral_count")
    private int naturalCount;

    public OpinionDataSaveRequest toOpinionDataSaveRequest() {
        return new OpinionDataSaveRequest(totalSentimentScore, positiveCount, negativeCount, naturalCount);
    }
}
