package kz.sdu.controller;

import kz.sdu.service.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LostItemController {
    private final LostItemService service;

    @Autowired
    public LostItemController(LostItemService service) {
        this.service = service;
    }

    
}
