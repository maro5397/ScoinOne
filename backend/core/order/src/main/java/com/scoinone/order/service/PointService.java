package com.scoinone.order.service;

import com.scoinone.order.entity.PointEntity;

public interface PointService {
    PointEntity createPoint(String userId);

    PointEntity getPointByUserId(String userId);

    String deletePointByUserId(String userId);
}
