package cat.itacademy.blackjack.player.infrastructure.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PlayerRankingResponse(@Schema(example = "123") String domainId,
                                    @Schema(example = "Pau") String name,
                                    @Schema(example = "2") int score,
                                    @Schema(example = "3") int totalGames,
                                    @Schema(example = "0.66") double winRatio
) {
}
