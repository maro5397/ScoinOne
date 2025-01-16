package com.scoinone.user.controller;

import com.scoinone.user.dto.common.DeleteResponseDto;
import com.scoinone.user.dto.request.user.CreateUserRequestDto;
import com.scoinone.user.dto.request.user.UpdateUserRequestDto;
import com.scoinone.user.dto.response.notification.GetNotificationsResponseDto;
import com.scoinone.user.dto.response.user.CreateUserResponseDto;
import com.scoinone.user.dto.response.user.GetOwnedAssetsResponseDto;
import com.scoinone.user.dto.response.user.GetUserResponseDto;
import com.scoinone.user.dto.response.user.UpdateUserResponseDto;
import com.scoinone.user.entity.NotificationEntity;
import com.scoinone.user.entity.OwnedVirtualAssetEntity;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.mapper.NotificationMapper;
import com.scoinone.user.mapper.OwnedVirtualAssetMapper;
import com.scoinone.user.mapper.UserMapper;
import com.scoinone.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        UserEntity user = userService.createUser(
                requestDto.getEmail(),
                requestDto.getPassword(),
                requestDto.getUsername()
        );
        return new ResponseEntity<>(UserMapper.INSTANCE.userToCreateUserResponseDto(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GetUserResponseDto> getUser(@RequestHeader(value = "UserId") String userId) {
        UserEntity user = userService.getUser(userId);
        return new ResponseEntity<>(UserMapper.INSTANCE.userToGetUserResponseDto(user), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<UpdateUserResponseDto> updateUser(
            @Valid @RequestBody UpdateUserRequestDto requestDto,
            @RequestHeader(value = "UserId") String userId
    ) {
        UserEntity updatedUser = userService.updateUser(userId, requestDto.getUsername());
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUpdateUserResponseDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteResponseDto> deleteUser(@RequestHeader(value = "UserId") String userId) {
        String result = userService.deleteUser(userId);
        DeleteResponseDto response = new DeleteResponseDto(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/notification")
    public ResponseEntity<GetNotificationsResponseDto> getNotifications(
            @RequestHeader(value = "UserId") String userId
    ) {
        List<NotificationEntity> notificationsFromLast30Days =
                userService.getNotificationsFromLast30DaysByUserId(userId);
        return new ResponseEntity<>(
                NotificationMapper.INSTANCE.listToGetNotificationsResponseDto(notificationsFromLast30Days),
                HttpStatus.OK
        );
    }

    @GetMapping("/asset")
    public ResponseEntity<GetOwnedAssetsResponseDto> getVirtualAssets(@RequestHeader(value = "UserId") String userId) {
        List<OwnedVirtualAssetEntity> ownedVirtualAssets = userService.getOwnedVirtualAssetsByUserId(userId);
        return new ResponseEntity<>(
                OwnedVirtualAssetMapper.INSTANCE.listToGetOwnedAssetsResponseDto(ownedVirtualAssets),
                HttpStatus.OK
        );
    }
}
