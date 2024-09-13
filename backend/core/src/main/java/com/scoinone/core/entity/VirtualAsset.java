package com.scoinone.core.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "virtual_assets")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class VirtualAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long virtualAssetId;

    private String name;
    private String symbol;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
}