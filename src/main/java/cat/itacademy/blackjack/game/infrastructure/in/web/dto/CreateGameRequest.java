package cat.itacademy.blackjack.game.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to create a new game")
public record CreateGameRequest(
        @NotBlank(message = "Player ID cannot be blank")
        @Schema(example = "player-123", description = "The domain ID of the player")
        String playerId) {
}
