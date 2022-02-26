package kz.sdu.bot.handler;

import kz.sdu.service.MarketPlaceBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketPlaceBotCommandHandler {

    private final MarketPlaceBotService service;

    @Autowired
    public MarketPlaceBotCommandHandler(MarketPlaceBotService service) {
        this.service = service;
    }

}
