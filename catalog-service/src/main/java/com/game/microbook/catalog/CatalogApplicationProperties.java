package com.game.microbook.catalog;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "catalog")
public record CatalogApplicationProperties(@DefaultValue("10") @Min(1) int pageSize) {}
