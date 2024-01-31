package net.bot.crypto.application.crypto.service;

import lombok.RequiredArgsConstructor;
import net.bot.crypto.application.domain.dto.response.MarketList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoService {

    private final UpbitFeignClient upbitClient;

    public List<MarketList> getMarketAll() {
        return upbitClient.getMarketAll(true);
    }

}
