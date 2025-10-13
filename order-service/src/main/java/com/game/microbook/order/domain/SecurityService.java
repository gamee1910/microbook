package com.game.microbook.order.domain;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getLoginUser() {
        return "game";
    }
}
