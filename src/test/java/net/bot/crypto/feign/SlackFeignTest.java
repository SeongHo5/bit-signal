package net.bot.crypto.feign;

import net.bot.crypto.application.slack.service.SlackFeignClient;
import net.bot.crypto.config.OpenFeignTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@OpenFeignTest
class SlackFeignTest {

    @Autowired
    private SlackFeignClient slackFeignClient;

    @Test
    void joinChennelTest() {
        // Given
        String channelId = "C01RZGZ3Z3Z";

        // When & Then
        assertDoesNotThrow(() -> slackFeignClient.joinChannel(channelId));
    }

}
