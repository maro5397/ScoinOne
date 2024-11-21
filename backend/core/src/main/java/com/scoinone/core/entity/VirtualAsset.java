package com.scoinone.core.entity;

import com.scoinone.core.entity.base.CreatableEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "virtual_assets")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class VirtualAsset extends CreatableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String symbol;
    private String description;
}