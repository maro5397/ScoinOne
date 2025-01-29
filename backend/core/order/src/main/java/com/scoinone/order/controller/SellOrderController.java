package com.scoinone.order.controller;

import com.scoinone.order.dto.request.order.CreateSellOrderRequestDto;
import com.scoinone.order.dto.response.order.CancelSellOrderResponseDto;
import com.scoinone.order.dto.response.order.CreateSellOrderResponseDto;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.mapper.OrderMapper;
import com.scoinone.order.service.SellOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sell")
@RequiredArgsConstructor
public class SellOrderController {
    private final SellOrderService sellOrderService;

    @PostMapping("/{virtualAssetId}")
    public ResponseEntity<CreateSellOrderResponseDto> createSellOrder(
            @PathVariable("virtualAssetId") String virtualAssetId,
            @RequestBody CreateSellOrderRequestDto requestDto,
            @RequestHeader(value = "UserId") String userId
    ) {
        SellOrderEntity sellOrder = sellOrderService.createSellOrder(
                virtualAssetId,
                requestDto.getQuantity(),
                requestDto.getPrice(),
                userId
        );
        // Kafka send
        return new ResponseEntity<>(
                OrderMapper.INSTANCE.sellOrderToCreateSellOrderResponseDto(sellOrder),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<CancelSellOrderResponseDto> deleteSellOrder(
            @PathVariable("orderId") Long orderId,
            @RequestHeader(value = "UserId") String userId
    ) {
        SellOrderEntity sellOrder = sellOrderService.cancelSellOrder(orderId, userId);
        // Kafka send
        return new ResponseEntity<>(
                OrderMapper.INSTANCE.sellOrderToCancelSellOrderResponseDto(sellOrder),
                HttpStatus.OK
        );
    }
}
