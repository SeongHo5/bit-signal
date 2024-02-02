package net.bot.crypto.config;

import net.bot.crypto.application.config.feign.FeignConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@ImportAutoConfiguration({
        FeignConfig.class,
        FeignAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class
})
class FeignTestContext {
}
