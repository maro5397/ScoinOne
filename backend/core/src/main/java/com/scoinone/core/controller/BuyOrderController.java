package com.scoinone.core.controller;

import com.scoinone.core.auth.LoginUser;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.order.CreateBuyOrderRequestDto;
import com.scoinone.core.dto.response.order.CreateBuyOrderResponseDto;
import com.scoinone.core.dto.response.order.GetBuyOrdersResponseDto;
import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.mapper.OrderMapper;
import com.scoinone.core.service.BuyOrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buy")
@RequiredArgsConstructor
public class BuyOrderController {
    private final BuyOrderService buyOrderService;

    @PostMapping("/{virtualAssetId}")
    public ResponseEntity<CreateBuyOrderResponseDto> createBuyOrder(
            @PathVariable Long virtualAssetId,
            @RequestBody CreateBuyOrderRequestDto requestDto,
            @LoginUser User user
    ) {
        BuyOrder buyOrder = buyOrderService.createBuyOrder(
                virtualAssetId,
                requestDto.getQuantity(),
                requestDto.getPrice(),
                user
        );
        return new ResponseEntity<>(
                OrderMapper.INSTANCE.buyOrderToCreateBuyOrderResponseDto(buyOrder),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<GetBuyOrdersResponseDto> getBuyOrders(
            @LoginUser User user
    ) {
        List<BuyOrder> buyOrders = buyOrderService.getBuyOrders(user);
        return new ResponseEntity<>(OrderMapper.INSTANCE.listToGetBuyOrderListResponseDto(buyOrders), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<DeleteResponseDto> deleteBuyOrder(
            @PathVariable Long orderId
    ) {
        String result = buyOrderService.deleteBuyOrder(orderId);
        DeleteResponseDto deleteResponseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(deleteResponseDto, HttpStatus.OK);
    }
}
