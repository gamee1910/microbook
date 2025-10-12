package com.game.microbook.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(ContainersConfiguration.class)
@SpringBootTest
class CatalogServiceApplicationTests {

    @Test
    void contextLoads() {}
}
