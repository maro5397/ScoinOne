package com.scoinone.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "owned_virtual_assets")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}