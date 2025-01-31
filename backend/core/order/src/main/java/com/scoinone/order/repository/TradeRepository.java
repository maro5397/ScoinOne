package com.scoinone.order.repository;

import com.scoinone.order.entity.TradeEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long> {
    List<TradeEntity> findByVirtualAssetId(String virtualAssetId);

    Optional<TradeEntity> findFirstByVirtualAssetIdOrderByCreatedAtDesc(String virtualAssetId);

    @Query("""
        SELECT t
        FROM TradeEntity t
        WHERE t.buyOrder.buyerId = :userId
        OR t.sellOrder.sellerId = :userId
    """)
    List<TradeEntity> findTradesByUserId(String userId);

    @Query("""
        SELECT t
        FROM TradeEntity t
        WHERE (t.virtualAssetId = :virtualAssetId AND t.buyOrder.buyerId = :userId)
        OR (t.virtualAssetId = :virtualAssetId AND t.sellOrder.sellerId = :userId)
    """)
    List<TradeEntity> findTradesByUserIdAndVirtualAssetId(String userId, String virtualAssetId);

    @Query("""
        SELECT t FROM TradeEntity t
        WHERE t.virtualAssetId = :virtualAssetId
        AND (:start IS NULL OR t.createdAt >= :start)
        AND (:end IS NULL OR t.createdAt <= :end)
        ORDER BY t.createdAt ASC
    """)
    List<TradeEntity> findByVirtualAssetIdAndOptionalCreatedAt(
            String virtualAssetId,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("""
        SELECT COALESCE(SUM(CASE WHEN t.buyOrder IS NOT NULL THEN t.quantity ELSE 0 END), 0)
        / COALESCE(SUM(CASE WHEN t.sellOrder IS NOT NULL THEN t.quantity ELSE 0 END), 1)
        * 100
        FROM TradeEntity t
        WHERE t.virtualAssetId = :virtualAssetId
        AND DATE(t.createdAt) = CURRENT_DATE
    """)
    Optional<BigDecimal> findVolumePowerByVirtualAssetId(String virtualAssetId);

    @Query(
            value = """
                    SELECT t.price 
                    FROM trades t
                    WHERE t.virtual_asset_id = :virtualAssetId 
                    AND t.created_at >= NOW() - INTERVAL 1 DAY
                    ORDER BY t.created_at DESC
                    """,
            nativeQuery = true
    )
    Optional<BigDecimal> findPreviousCloseByVirtualAssetId(String virtualAssetId);

    @Query("""
        SELECT MAX(t.price)
        FROM TradeEntity t
        WHERE t.virtualAssetId = :virtualAssetId
        AND DATE(t.createdAt) = CURRENT_DATE
    """)
    Optional<BigDecimal> findHighPriceByVirtualAssetId(String virtualAssetId);

    @Query("""
        SELECT MIN(t.price)
        FROM TradeEntity t
        WHERE t.virtualAssetId = :virtualAssetId
        AND DATE(t.createdAt) = CURRENT_DATE
    """)
    Optional<BigDecimal> findLowPriceByVirtualAssetId(String virtualAssetId);

    @Query(
            value = """
                    SELECT COALESCE(SUM(t.quantity), 0)
                    FROM trades t
                    WHERE t.virtual_asset_id = :virtualAssetId
                    AND t.created_at >= NOW() - INTERVAL 24 HOUR
                    """,
            nativeQuery = true
    )
    Optional<BigDecimal> findTotalVolumeByVirtualAssetId(String virtualAssetId);

    @Query(
            value = """
                    SELECT COALESCE(SUM(t.price * t.quantity), 0)
                    FROM trades t
                    WHERE t.virtual_asset_id = :virtualAssetId
                    AND t.created_at >= NOW() - INTERVAL 24 HOUR
                    """,
            nativeQuery = true
    )
    Optional<BigDecimal> findTotalMoneyVolumeByVirtualAssetId(String virtualAssetId);
}
