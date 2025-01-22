package com.scoinone.user.client;

import com.scoinone.user.dto.common.DeleteResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="post")
public interface PostServiceClient {
    @DeleteMapping("/api/post")
    DeleteResponseDto deleteAllPostByUserId(@RequestParam("userId") String userId);

    @DeleteMapping("/api/comment")
    DeleteResponseDto deleteAllCommentByUserId(@RequestParam("userId") String userId);
}
