package com.scoinone.order.controller;

import com.scoinone.order.dto.request.order.CreateBuyOrderRequestDto;
import com.scoinone.order.dto.response.order.CancelBuyOrderResponseDto;
import com.scoinone.order.dto.response.order.CreateBuyOrderResponseDto;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.mapper.OrderMapper;
import com.scoinone.order.service.BuyOrderService;
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
@RequestMapping("/api/buy")
@RequiredArgsConstructor
public class BuyOrderController {
    private final BuyOrderService buyOrderService;

    @PostMapping("/{virtualAssetId}")
    public ResponseEntity<CreateBuyOrderResponseDto> createBuyOrder(
            @PathVariable("virtualAssetId") String virtualAssetId,
            @RequestBody CreateBuyOrderRequestDto requestDto,
            @RequestHeader(value = "UserId") String userId
    ) {
        BuyOrderEntity buyOrder = buyOrderService.createBuyOrder(
                virtualAssetId,
                requestDto.getQuantity(),
                requestDto.getPrice(),
                userId
        );
        // Kafka send
        return new ResponseEntity<>(
                OrderMapper.INSTANCE.buyOrderToCreateBuyOrderResponseDto(buyOrder),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<CancelBuyOrderResponseDto> cancelBuyOrder(
            @PathVariable("orderId") Long orderId,
            @RequestHeader(value = "UserId") String userId
    ) {
        BuyOrderEntity buyOrder = buyOrderService.cancelBuyOrder(orderId, userId);
        // Kafka send
        return new ResponseEntity<>(OrderMapper.INSTANCE.buyOrderToCancelBuyOrderResponseDto(buyOrder), HttpStatus.OK);
    }
}
