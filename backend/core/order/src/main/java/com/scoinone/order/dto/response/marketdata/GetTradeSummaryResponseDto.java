package com.scoinone.order.dto.response.marketdata;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTradeSummaryResponseDto {
    private String virtualAssetId;
    private String name;
    private String symbol;
    private SummaryDto summary;

    public GetTradeSummaryResponseDto() {
        this.summary = new SummaryDto();
    }

    @Getter
    @Setter
    public static class SummaryDto {
        private BigDecimal price;
        private BigDecimal priceChangeRate;
        private BigDecimal totalMoneyVolume;
    }
}
