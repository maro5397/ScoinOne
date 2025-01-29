package com.scoinone.order.controller;

import com.scoinone.order.dto.common.DeleteResponseDto;
import com.scoinone.order.dto.response.point.CreatePointResponseDto;
import com.scoinone.order.dto.response.point.GetPointResponseDto;
import com.scoinone.order.entity.PointEntity;
import com.scoinone.order.mapper.PointMapper;
import com.scoinone.order.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping()
    public ResponseEntity<CreatePointResponseDto> createPoint(@RequestHeader(value = "UserId") String userId) {
        PointEntity point = pointService.createPoint(userId);
        return new ResponseEntity<>(
                PointMapper.INSTANCE.pointToCreatePointResponseDto(point),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<GetPointResponseDto> getPoint(@RequestHeader(value = "UserId") String userId) {
        PointEntity pointByUserId = pointService.getPointByUserId(userId);
        return new ResponseEntity<>(PointMapper.INSTANCE.pointToGetPointResponseDto(pointByUserId), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteResponseDto> deletePoint(@RequestParam("userId") String userId) {
        String result = pointService.deletePointByUserId(userId);
        DeleteResponseDto response = new DeleteResponseDto(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
