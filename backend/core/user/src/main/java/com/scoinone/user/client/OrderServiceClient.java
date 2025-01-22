package com.scoinone.user.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order")
public interface OrderServiceClient {
}
