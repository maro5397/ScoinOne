package com.scoinone.order.repository;

import com.scoinone.order.entity.TradeEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    List<TradeEntity> findTradesByUserId(@Param("userId") String userId);

    @Query("""
        SELECT t
        FROM TradeEntity t
        WHERE (t.virtualAssetId = :virtualAssetId AND t.buyOrder.buyerId = :userId)
        OR (t.virtualAssetId = :virtualAssetId AND t.sellOrder.sellerId = :userId)
    """)
    List<TradeEntity> findTradesByUserIdAndVirtualAssetId(
            @Param("userId") String userId,
            @Param("virtualAssetId") String virtualAssetId
    );

    @Query("""
        SELECT t FROM TradeEntity t
        WHERE t.virtualAssetId = :virtualAssetId
        AND (:startDate IS NULL OR t.createdAt >= :startDate)
        AND (:endDate IS NULL OR t.createdAt <= :endDate)
        ORDER BY t.createdAt ASC
    """)
    List<TradeEntity> findByVirtualAssetIdAndOptionalCreatedAt(
            @Param("virtualAssetId") String virtualAssetId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
        SELECT COALESCE(SUM(CASE WHEN t.buyOrder IS NOT NULL THEN t.quantity ELSE 0 END), 0)
        / COALESCE(SUM(CASE WHEN t.sellOrder IS NOT NULL THEN t.quantity ELSE 0 END), 1)
        * 100
        FROM TradeEntity t
        WHERE t.virtualAssetId = :virtualAssetId
        AND DATE(t.createdAt) = CURRENT_DATE
    """)
    Optional<BigDecimal> findVolumePowerByVirtualAssetId(@Param("virtualAssetId") String virtualAssetId);

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
    Optional<BigDecimal> findPreviousCloseByVirtualAssetId(@Param("virtualAssetId") String virtualAssetId);

    @Query("""
        SELECT MAX(t.price)
        FROM TradeEntity t
        WHERE t.virtualAssetId = :virtualAssetId
        AND DATE(t.createdAt) = CURRENT_DATE
    """)
    Optional<BigDecimal> findHighPriceByVirtualAssetId(@Param("virtualAssetId") String virtualAssetId);

    @Query("""
        SELECT MIN(t.price)
        FROM TradeEntity t
        WHERE t.virtualAssetId = :virtualAssetId
        AND DATE(t.createdAt) = CURRENT_DATE
    """)
    Optional<BigDecimal> findLowPriceByVirtualAssetId(@Param("virtualAssetId") String virtualAssetId);

    @Query(
            value = """
                    SELECT COALESCE(SUM(t.quantity), 0)
                    FROM trades t
                    WHERE t.virtual_asset_id = :virtualAssetId
                    AND t.created_at >= NOW() - INTERVAL 24 HOUR
                    """,
            nativeQuery = true
    )
    Optional<BigDecimal> findTotalVolumeByVirtualAssetId(@Param("virtualAssetId") String virtualAssetId);

    @Query(
            value = """
                    SELECT COALESCE(SUM(t.price * t.quantity), 0)
                    FROM trades t
                    WHERE t.virtual_asset_id = :virtualAssetId
                    AND t.created_at >= NOW() - INTERVAL 24 HOUR
                    """,
            nativeQuery = true
    )
    Optional<BigDecimal> findTotalMoneyVolumeByVirtualAssetId(@Param("virtualAssetId") String virtualAssetId);
}
