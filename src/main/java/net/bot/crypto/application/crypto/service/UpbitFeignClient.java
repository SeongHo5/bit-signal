package net.bot.crypto.application.crypto.service;

import net.bot.crypto.application.domain.dto.MarketPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "UpbitCurrency", url = "https://api.upbit.com/v1")
public interface UpbitFeignClient {

    @GetMapping("/market/all")
    String getMarketAll(@RequestParam("isDetails") boolean isDetails);

    @GetMapping("/candles/minutes/{unit}")
    List<MarketPrice> getCandlesMinutes(
            @PathVariable("unit") int unit,
            @RequestParam("market") String market,
            @RequestParam("count") int count
    );

    @GetMapping("/ticker")
    String getTicker(@RequestParam("markets") String markets);

    @GetMapping("/orderbook")
    String getOrderBook(@RequestParam("markets") String markets);

}
