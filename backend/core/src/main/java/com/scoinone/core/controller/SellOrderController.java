package com.scoinone.core.controller;

import com.scoinone.core.common.annotation.LoginUser;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.order.CreateSellOrderRequestDto;
import com.scoinone.core.dto.response.order.CreateSellOrderResponseDto;
import com.scoinone.core.dto.response.order.GetSellOrdersResponseDto;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.mapper.OrderMapper;
import com.scoinone.core.service.SellOrderService;
import com.scoinone.core.service.UserService;
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
@RequestMapping("/api/sell")
@RequiredArgsConstructor
public class SellOrderController {
    private final SellOrderService sellOrderService;
    private final UserService userService;

    @PostMapping("/{virtualAssetId}")
    public ResponseEntity<CreateSellOrderResponseDto> createSellOrder(
            @PathVariable("virtualAssetId") Long virtualAssetId,
            @RequestBody CreateSellOrderRequestDto requestDto,
            @LoginUser User user
    ) {
        SellOrder sellOrder = sellOrderService.createSellOrder(
                virtualAssetId,
                requestDto.getQuantity(),
                requestDto.getPrice(),
                user
        );
        return new ResponseEntity<>(
                OrderMapper.INSTANCE.sellOrderToCreateSellOrderResponseDto(sellOrder),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<GetSellOrdersResponseDto> getSellOrders(
            @LoginUser User user
    ) {
        List<SellOrder> SellOrders = userService.getSellOrderByUserId(user.getId());
        return new ResponseEntity<>(OrderMapper.INSTANCE.listToGetSellOrdersResponseDto(SellOrders), HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<DeleteResponseDto> deleteSellOrder(
            @LoginUser User user,
            @PathVariable("orderId") Long orderId
    ) {
        String result = sellOrderService.deleteSellOrder(orderId, user.getId());
        DeleteResponseDto deleteResponseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(deleteResponseDto, HttpStatus.OK);
    }
}
