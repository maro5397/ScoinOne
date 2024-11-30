package com.scoinone.core.controller;

import com.scoinone.core.auth.LoginUser;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.user.CreateUserRequestDto;
import com.scoinone.core.dto.request.user.UpdateUserRequestDto;
import com.scoinone.core.dto.response.notification.GetNotificationsResponseDto;
import com.scoinone.core.dto.response.post.GetPostsResponseDto;
import com.scoinone.core.dto.response.trade.GetTradesResponseDto;
import com.scoinone.core.dto.response.user.CreateUserResponseDto;
import com.scoinone.core.dto.response.user.GetOwnedAssetsResponseDto;
import com.scoinone.core.dto.response.user.GetUserResponseDto;
import com.scoinone.core.dto.response.user.UpdateUserResponseDto;
import com.scoinone.core.entity.Notification;
import com.scoinone.core.entity.OwnedVirtualAsset;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.Trade;
import com.scoinone.core.entity.User;
import com.scoinone.core.mapper.NotificationMapper;
import com.scoinone.core.mapper.OwnedVirtualAssetMapper;
import com.scoinone.core.mapper.PostMapper;
import com.scoinone.core.mapper.TradeMapper;
import com.scoinone.core.mapper.UserMapper;
import com.scoinone.core.service.UserService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        User user = userService.createUser(requestDto.getEmail(), requestDto.getPassword(), requestDto.getUsername());
        return ResponseEntity.ok(UserMapper.INSTANCE.userToCreateUserResponseDto(user));
    }

    @GetMapping
    public ResponseEntity<GetUserResponseDto> getUser(@Valid @LoginUser User user) {
        return ResponseEntity.ok(UserMapper.INSTANCE.userToGetUserResponseDto(user));
    }

    @PatchMapping
    public ResponseEntity<UpdateUserResponseDto> updateUser(
            @Valid @RequestBody UpdateUserRequestDto requestDto,
            @Valid @LoginUser User user
    ) {
        User updatedUser = userService.updateUser(user.getId(), requestDto.getUsername());
        return ResponseEntity.ok(UserMapper.INSTANCE.userToUpdateUserResponseDto(updatedUser));
    }

    @DeleteMapping
    public ResponseEntity<DeleteResponseDto> deleteUser(@Valid @LoginUser User user) {
        String result = userService.deleteUser(user.getId());
        DeleteResponseDto response = new DeleteResponseDto(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notification")
    public ResponseEntity<GetNotificationsResponseDto> getNotifications(@Valid @LoginUser User user) {
        List<Notification> notificationsFromLast30Days = userService.getNotificationsFromLast30DaysByUserId(
                user.getId()
        );
        return new ResponseEntity<>(
                NotificationMapper.INSTANCE.listToGetNotificationListResponseDto(notificationsFromLast30Days),
                HttpStatus.OK
        );
    }

    @GetMapping("/trade")
    public ResponseEntity<GetTradesResponseDto> getTrades(@Valid @LoginUser User user) {
        List<Trade> tradeByUserId = userService.getTradeByUserId(user.getId());
        return new ResponseEntity<>(
                TradeMapper.INSTANCE.listToGetTradeListResponseDto(tradeByUserId),
                HttpStatus.OK
        );
    }

    @GetMapping("/question")
    public ResponseEntity<GetPostsResponseDto> getQuestionPosts(@Valid @LoginUser User user) {
        List<Post> questionsByUserId = userService.getQuestionsByUserId(user.getId());
        return new ResponseEntity<>(
                PostMapper.INSTANCE.listToGetPostListResponseDto(questionsByUserId),
                HttpStatus.OK
        );
    }

    @GetMapping("/asset")
    public ResponseEntity<GetOwnedAssetsResponseDto> getVirtualAssets(@Valid @LoginUser User user) {
        List<OwnedVirtualAsset> ownedVirtualAssets = userService.getOwnedVirtualAssetsByUserId(user.getId());
        return new ResponseEntity<>(
                OwnedVirtualAssetMapper.INSTANCE.listToGetOwnedAssetListResponseDto(ownedVirtualAssets),
                HttpStatus.OK
        );
    }
}
