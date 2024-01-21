package net.bot.crypto.application.slack.service;

import net.bot.crypto.application.config.feign.SlackFeignConfig;
import net.bot.crypto.application.domain.dto.SlackMessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "slack", url = "https://slack.com/api", configuration = SlackFeignConfig.class)
public interface SlackFeignClient {

    @PostMapping("/chat.postMessage")
    void postMessage(@RequestBody SlackMessageRequest request);

    @PostMapping("/conversations.join")
    void joinChannel(@RequestBody SlackMessageRequest request);

}
