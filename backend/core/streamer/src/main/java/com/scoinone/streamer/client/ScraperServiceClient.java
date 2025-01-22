package com.scoinone.streamer.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "scraper")
public interface ScraperServiceClient {
}
