package com.scoinone.order.controller;

import com.scoinone.order.dto.common.DeleteResponseDto;
import com.scoinone.order.dto.response.order.GetOrdersByUserIdResponseDto;
import com.scoinone.order.dto.response.order.GetOrdersResponseDto;
import com.scoinone.order.entity.base.OrderEntity;
import com.scoinone.order.mapper.OrderMapper;
import com.scoinone.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/all/{assetId}")
    public ResponseEntity<GetOrdersResponseDto> getOrdersByAssetId(@PathVariable("assetId") String assetId) {
        List<OrderEntity> orderByAssetId = orderService.getOrderByAssetId(assetId);
        return new ResponseEntity<>(
                OrderMapper.INSTANCE.listToGetOrdersResponseDto(orderByAssetId),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<GetOrdersByUserIdResponseDto> getOrdersByUserId(
            @RequestHeader(value = "UserId") String userId
    ) {
        List<OrderEntity> orderByUserId = orderService.getOrderByUserId(userId);
        return new ResponseEntity<>(
                OrderMapper.INSTANCE.listToGetOrdersByUserIdResponseDto(orderByUserId),
                HttpStatus.OK
        );
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<GetOrdersByUserIdResponseDto> getOrdersByUserIdAndAssetId(
            @PathVariable("assetId") String assetId,
            @RequestHeader(value = "UserId") String userId
    ) {
        List<OrderEntity> orderByUserIdAndAssetId = orderService.getOrderByUserIdAndAssetId(userId, assetId);
        return new ResponseEntity<>(
                OrderMapper.INSTANCE.listToGetOrdersByUserIdResponseDto(orderByUserIdAndAssetId),
                HttpStatus.OK
        );
    }

    @PatchMapping
    public ResponseEntity<DeleteResponseDto> deleteAllOrderByUserId(@RequestParam("userId") String userId) {
        String result = orderService.cancelAllOrders(userId);
        DeleteResponseDto responseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
