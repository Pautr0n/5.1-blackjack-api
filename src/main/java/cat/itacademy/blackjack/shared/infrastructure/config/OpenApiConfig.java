package cat.itacademy.blackjack.shared.infrastructure.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI blackjackApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blackjack API")
                        .description("API for Blackjack game and player management")
                        .version("1.0.0"));
    }

}
