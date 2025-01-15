package com.scoinone.order.controller;

import com.scoinone.order.dto.common.DeleteResponseDto;
import com.scoinone.order.dto.request.order.CreateSellOrderRequestDto;
import com.scoinone.order.dto.response.order.CreateSellOrderResponseDto;
import com.scoinone.order.dto.response.order.GetSellOrdersResponseDto;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.mapper.OrderMapper;
import com.scoinone.order.service.SellOrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        return new ResponseEntity<>(
                OrderMapper.INSTANCE.sellOrderToCreateSellOrderResponseDto(sellOrder),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<GetSellOrdersResponseDto> getSellOrders(@RequestHeader(value = "UserId") String userId) {
        List<SellOrderEntity> SellOrders = sellOrderService.getSellOrderByUserId(userId);
        return new ResponseEntity<>(OrderMapper.INSTANCE.listToGetSellOrdersResponseDto(SellOrders), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<DeleteResponseDto> deleteSellOrder(
            @PathVariable("orderId") Long orderId,
            @RequestHeader(value = "UserId") String userId
    ) {
        String result = sellOrderService.deleteSellOrder(orderId, userId);
        DeleteResponseDto deleteResponseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(deleteResponseDto, HttpStatus.OK);
    }
}
