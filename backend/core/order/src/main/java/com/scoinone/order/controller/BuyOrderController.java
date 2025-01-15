package com.scoinone.order.controller;

import com.scoinone.order.dto.common.DeleteResponseDto;
import com.scoinone.order.dto.request.order.CreateBuyOrderRequestDto;
import com.scoinone.order.dto.response.order.CreateBuyOrderResponseDto;
import com.scoinone.order.dto.response.order.GetBuyOrdersResponseDto;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.mapper.OrderMapper;
import com.scoinone.order.service.BuyOrderService;
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
        BuyOrderEntity buyOrderEntity = buyOrderService.createBuyOrder(
                virtualAssetId,
                requestDto.getQuantity(),
                requestDto.getPrice(),
                userId
        );
        return new ResponseEntity<>(
                OrderMapper.INSTANCE.buyOrderToCreateBuyOrderResponseDto(buyOrderEntity),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<GetBuyOrdersResponseDto> getBuyOrders(@RequestHeader(value = "UserId") String userId) {
        List<BuyOrderEntity> buyOrders = buyOrderService.getBuyOrderByUserId(userId);
        return new ResponseEntity<>(OrderMapper.INSTANCE.listToGetBuyOrdersResponseDto(buyOrders), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<DeleteResponseDto> deleteBuyOrder(
            @PathVariable("orderId") Long orderId,
            @RequestHeader(value = "UserId") String userId
    ) {
        String result = buyOrderService.deleteBuyOrder(orderId, userId);
        DeleteResponseDto deleteResponseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(deleteResponseDto, HttpStatus.OK);
    }
}
