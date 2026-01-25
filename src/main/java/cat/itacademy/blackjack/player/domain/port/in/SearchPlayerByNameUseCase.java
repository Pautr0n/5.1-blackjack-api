package cat.itacademy.blackjack.player.domain.port.in;

import cat.itacademy.blackjack.player.domain.model.Player;
import cat.itacademy.blackjack.player.domain.port.out.query.PlayerSummary;
import reactor.core.publisher.Flux;

public interface SearchPlayerByNameUseCase {
    Flux<Player> searchByName(String partialName);
}
