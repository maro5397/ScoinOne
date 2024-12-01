package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.entity.BuyOrder;
import com.scoinone.core.entity.SellOrder;
import com.scoinone.core.entity.User;
import com.scoinone.core.entity.VirtualAsset;
import com.scoinone.core.repository.SellOrderRepository;
import com.scoinone.core.repository.VirtualAssetRepository;
import com.scoinone.core.service.TradeService;
import com.scoinone.core.service.impl.SellOrderServiceImpl;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SellOrderServiceImplTest {
    @InjectMocks
    private SellOrderServiceImpl sellOrderService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private SellOrderRepository sellOrderRepository;

    @Mock
    private VirtualAssetRepository virtualAssetRepository;

    @Mock
    private TradeService tradeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("판매 주문 생성 테스트")
    public void testCreateSellOrder() {
        Long assetId = 1L;
        BigDecimal quantity = BigDecimal.valueOf(2);
        BigDecimal price = BigDecimal.valueOf(300);
        VirtualAsset virtualAsset = VirtualAsset.builder().build();
        User user = User.builder().build();
        when(virtualAssetRepository.findById(assetId)).thenReturn(Optional.of(virtualAsset));

        sellOrderService.createSellOrder(assetId, quantity, price, user);

        ArgumentCaptor<SellOrder> sellOrderCaptor = ArgumentCaptor.forClass(SellOrder.class);
        verify(sellOrderRepository).save(sellOrderCaptor.capture());

        SellOrder sellOrder = sellOrderCaptor.getValue();

        assertSoftly(softly -> {
            softly.assertThat(sellOrder).isNotNull();
            verify(entityManager).persist(sellOrder);
            verify(tradeService).processSellOrderTrade(sellOrder);
            verify(sellOrderRepository).save(sellOrder);
        });
    }

    @Test
    @DisplayName("판매 주문 삭제 테스트")
    public void testDeleteSellOrder() {
        Long sellOrderId = 1L;
        Long sellerId = 1L;

        when(sellOrderRepository.deleteByIdAndSeller_Id(sellOrderId, sellerId)).thenReturn(1L);

        sellOrderService.deleteSellOrder(sellOrderId, sellerId);
        verify(sellOrderRepository).deleteByIdAndSeller_Id(sellOrderId, sellerId);
    }
}