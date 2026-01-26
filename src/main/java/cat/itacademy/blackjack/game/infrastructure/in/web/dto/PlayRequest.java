package cat.itacademy.blackjack.game.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Request to play a move")
public record PlayRequest(
        @NotBlank(message = "Move type cannot be blank")
        @Pattern(regexp = "HIT|STAND", message = "Move type must be either HIT or STAND")
        @Schema(example = "HIT", allowableValues = {"HIT", "STAND"}, description = "The move to play")
        String moveType) {
}
