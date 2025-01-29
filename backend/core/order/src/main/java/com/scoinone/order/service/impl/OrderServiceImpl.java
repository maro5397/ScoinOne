package com.scoinone.order.service.impl;

import com.scoinone.order.common.status.OrderStatus;
import com.scoinone.order.entity.BuyOrderEntity;
import com.scoinone.order.entity.SellOrderEntity;
import com.scoinone.order.entity.base.OrderEntity;
import com.scoinone.order.repository.BuyOrderRepository;
import com.scoinone.order.repository.SellOrderRepository;
import com.scoinone.order.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final SellOrderRepository sellOrderRepository;
    private final BuyOrderRepository buyOrderRepository;

    @Override
    public List<OrderEntity> getOrders() {
        List<SellOrderEntity> sellOrders = sellOrderRepository.findAll();
        List<BuyOrderEntity> buyOrders = buyOrderRepository.findAll();
        return convertToOrderEntities(buyOrders, sellOrders);
    }

    @Override
    public OrderEntity getOrderById(Long id) {
        BuyOrderEntity buyOrder = buyOrderRepository.findById(id).orElse(null);
        if (buyOrder != null) return buyOrder;

        SellOrderEntity sellOrder = sellOrderRepository.findById(id).orElse(null);
        if (sellOrder != null) return sellOrder;

        throw new EntityNotFoundException("Order not found with id: " + id);
    }

    @Override
    public List<OrderEntity> getOrderByUserId(String userId) {
        List<BuyOrderEntity> buyOrders = buyOrderRepository.findByBuyerIdAndStatus(userId, OrderStatus.PENDING);
        List<SellOrderEntity> sellOrders = sellOrderRepository.findBySellerIdAndStatus(userId, OrderStatus.PENDING);
        return convertToOrderEntities(buyOrders, sellOrders);
    }

    @Override
    public List<OrderEntity> getOrderByAssetId(String assetId) {
        List<BuyOrderEntity> buyOrders = buyOrderRepository.findByVirtualAssetIdAndStatus(assetId, OrderStatus.PENDING);
        List<SellOrderEntity> sellOrders = sellOrderRepository.findByVirtualAssetIdAndStatus(
                assetId,
                OrderStatus.PENDING
        );
        return convertToOrderEntities(buyOrders, sellOrders);
    }

    @Override
    public List<OrderEntity> getOrderByUserIdAndAssetId(String userId, String assetId) {
        List<BuyOrderEntity> buyOrders = buyOrderRepository.findByBuyerIdAndVirtualAssetIdAndStatus(
                userId,
                assetId,
                OrderStatus.PENDING
        );
        List<SellOrderEntity> sellOrders = sellOrderRepository.findBySellerIdAndVirtualAssetIdAndStatus(
                userId,
                assetId,
                OrderStatus.PENDING
        );
        return convertToOrderEntities(buyOrders, sellOrders);
    }

    @Override
    public String cancelAllOrders(String userId) {
        List<BuyOrderEntity> buyOrders = buyOrderRepository.findByBuyerIdAndStatus(userId, OrderStatus.PENDING);
        List<SellOrderEntity> sellOrders = sellOrderRepository.findBySellerIdAndStatus(userId, OrderStatus.PENDING);
        buyOrders.forEach(buyOrder -> buyOrder.setStatus(OrderStatus.CANCELED));
        sellOrders.forEach(sellOrder -> sellOrder.setStatus(OrderStatus.CANCELED));
        long count = buyOrders.size() + sellOrders.size();
        return "All of Orders(" + count + ") canceled successfully";
    }

    private List<OrderEntity> convertToOrderEntities(List<BuyOrderEntity> buyOrders, List<SellOrderEntity> sellOrders) {
        List<OrderEntity> orders = new ArrayList<>(buyOrders.stream()
                .map(buyOrder -> (OrderEntity) buyOrder)
                .toList());

        orders.addAll(sellOrders.stream()
                .map(sellOrder -> (OrderEntity) sellOrder)
                .toList());

        orders.sort(Comparator.comparing(OrderEntity::getCreatedAt));

        return orders;
    }
}
