package cat.itacademy.blackjack.player.infrastructure.in.web.mapper;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.PlayerRankingResponse;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerRankingEntry;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerSummary;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.PlayerResponse;

public class PlayerApiMapper {

    public static PlayerResponse fromDomain(Player player) {
        return new PlayerResponse(
                player.id().value(),
                player.name(),
                player.score(),
                player.totalGames()
        );
    }

    public static PlayerRankingResponse fromRanking(PlayerRankingEntry entry) {
        return new PlayerRankingResponse(
                entry.id(),
                entry.name(),
                entry.score(),
                entry.totalGames(),
                entry.winRatio()
        );
    }

    public static PlayerResponse fromSummary(PlayerSummary summary) {
        return new PlayerResponse(
                summary.domainId(),
                summary.name(),
                summary.score(),
                summary.totalGames()
        );
    }

}
