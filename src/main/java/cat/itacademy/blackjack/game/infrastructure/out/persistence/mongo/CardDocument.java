package cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo;

import cat.itacademy.blackjack.game.domain.model.Rank;
import cat.itacademy.blackjack.game.domain.model.Suit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDocument {
    private String rank;
    private String suit;
}
