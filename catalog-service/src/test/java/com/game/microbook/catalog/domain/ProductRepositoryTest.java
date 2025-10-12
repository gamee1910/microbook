package com.game.microbook.catalog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.game.microbook.catalog.IntegrationTestConfiguration;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

/**
 * There are two main approaches for writing repository integration tests
 * using {@code @DataJpaTest} with Testcontainers and a real database (PostgreSQL):
 *
 * <h3>Approach 1: Using {@code @DataJpaTest} with In-line Properties</h3>
 * This approach is often the simplest when you only need to define the Testcontainers connection once.
 * * <ol>
 * <li>Use the {@code @DataJpaTest} annotation.</li>
 * <li>Define necessary properties directly within the annotation to configure the database:
 * <ul>
 * <li>{@code "spring.test.database.replace=none"}: **Disables** Spring Boot's default behavior
 * of replacing the production {@code DataSource} with an in-memory database (like H2).</li>
 * <li>{@code "spring.datasource.url=jdbc:tc:postgresql:15-alpine:///db"}: Uses the
 * **Testcontainers JDBC URL** to dynamically start a Docker container (PostgreSQL 15-alpine)
 * and connect to it.</li>
 * </ul>
 * </li>
 * <li>Use the {@code @Sql} annotation on test methods to load necessary data (e.g., from SQL scripts).</li>
 * </ol>
 * * <pre>{@code
 * @DataJpaTest(
 * properties = {
 * "spring.test.database.replace=none",
 * "spring.datasource.url=jdbc:tc:postgresql:15-alpine:///db"
 * }
 * )
 * class ProductRepositoryTest {
 * // ... test methods
 * }
 * }</pre>
 * * <h3>Approach 2: Using {@code @Import} a Configuration Class (The Spring Way)</h3>
 * This approach is preferred for **reusability** and **cleaner separation** of configuration logic.
 * * <ol>
 * <li>Create a dedicated {@code @TestConfiguration} class (e.g., {@code IntegrationTestConfiguration.class})
 * to manage the Testcontainers lifecycle (e.g., using {@code @Testcontainers} and {@code @Container})
 * and set dynamic properties.</li>
 * <li>Use the {@code @Import(IntegrationTestConfiguration.class)} annotation on your test class.</li>
 * <li>The configuration class handles the {@code spring.datasource.url} dynamically, so you **do not** * need to define the {@code spring.datasource.url} property in the {@code @DataJpaTest} annotation
 * (thus, you may comment it out if it was defined in a properties file, or simply omit it here).
 * </ol>
 */
@Sql("/test-data.sql")
@DataJpaTest(properties = {"spring.test.database.replace=none"})
@Import(IntegrationTestConfiguration.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(15);
    }

    @Test
    void shouldGetProductByCode() {
        ProductEntity product = productRepository.findByCode("P110").orElseThrow();
        assertThat(product).isNotNull();
        assertThat(product.getCode()).isEqualTo("P110");
        assertThat(product.getName()).isEqualTo("A Thousand Splendid Suns");
        assertThat(product.getDescription())
                .isEqualTo(
                        "A Thousand Splendid Suns is a breathtaking story set against the volatile events of Afghanistan's last thirty years—from the Soviet invasion to the reign of the Taliban to post-Taliban rebuilding—that puts the violence, fear, hope, and faith of this country in intimate, human terms.");
        assertThat(product.getImageUrl()).isEqualTo("https://images.gr-assets.com/books/1345958969l/128029.jpg");
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("15.50"));
    }

    @Test
    void shouldReturnProductNotFoundException() {
        assertThat(productRepository.findByCode("invalid_product_code")).isEmpty();
    }
}
