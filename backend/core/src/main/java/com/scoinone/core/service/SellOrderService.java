package com.scoinone.core.service;

import com.scoinone.core.entity.SellOrder;

import java.util.List;

// 거래 체결값을 구해서 그 값을 기준으로 위/아래 X개의 데이터를 리스트로 전달
// 매도 유저 ID를 대상으로 매도 주문 데이터를 리스트로 전달
public interface SellOrderService {

    SellOrder createSellOrder(SellOrder sellOrder);

    void deleteSellOrder(Long id);
}
