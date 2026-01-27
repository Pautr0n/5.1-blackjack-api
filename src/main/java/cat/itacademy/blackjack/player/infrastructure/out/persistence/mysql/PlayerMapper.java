package cat.itacademy.blackjack.player.infrastructure.out.persistence.mysql;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.model.PlayerId;

public class PlayerMapper {

    public static PlayerEntity toEntity(Player player) {
        return new PlayerEntity(
                null,
                player.id().value(),
                player.name(),
                player.score(),
                player.totalGames()
        );
    }


    public static Player toDomain(PlayerEntity entity) {
        return Player.restore(
                new PlayerId(entity.getDomainId()),
                entity.getName(),
                entity.getScore(),
                entity.getTotalGames()
        );
    }

}
