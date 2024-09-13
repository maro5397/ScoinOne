package com.scoinone.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "owned_virtual_assets")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class OwnedVirtualAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownedVirtualAssetId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "virtual_asset_id")
    private VirtualAsset virtualAsset;

    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;
}