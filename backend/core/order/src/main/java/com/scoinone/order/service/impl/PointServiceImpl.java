package com.scoinone.order.service.impl;

import com.scoinone.order.entity.PointEntity;
import com.scoinone.order.repository.PointRepository;
import com.scoinone.order.service.PointService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PointServiceImpl implements PointService {
    private static final BigDecimal DEFAULT_POINT = BigDecimal.valueOf(1_000_000);

    private final PointRepository pointRepository;

    @Override
    public PointEntity createPoint(String userId) {
        PointEntity point = PointEntity.builder()
                .userId(userId)
                .balance(DEFAULT_POINT)
                .build();
        return pointRepository.save(point);
    }

    @Override
    public PointEntity getPointByUserId(String userId) {
        return pointRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("point not found with userId: " + userId));
    }

    @Override
    public String deletePointByUserId(String userId) {
        Long count = pointRepository.deleteByUserId(userId);
        if (count == 0) {
            throw new EntityNotFoundException("Point not found or you are not authorized to delete this Point");
        }
        return "Point deleted successfully";
    }
}
