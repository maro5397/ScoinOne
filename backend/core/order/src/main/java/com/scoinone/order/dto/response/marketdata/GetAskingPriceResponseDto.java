package com.scoinone.order.dto.response.marketdata;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAskingPriceResponseDto {
    private BigDecimal volumePower; // 당일 누적 매수 수량 / 당일 누적 매도 수량 x 100
    private BigDecimal previousClose; // 전일 종가
    private BigDecimal highPrice; // 당일 고가
    private BigDecimal lowPrice; // 당일 저가
    private BigDecimal totalVolume; // 최근 24시간 동안의 거래량
    private BigDecimal totalMoneyVolume; // 최근 24시간 동안의 거래대금
}
