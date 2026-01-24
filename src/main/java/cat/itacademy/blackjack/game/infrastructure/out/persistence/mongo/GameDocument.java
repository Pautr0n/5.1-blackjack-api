package cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "games")
public class GameDocument {

    @Id
    private String id;

    private String playerId;
    private List<CardDocument> deck;
    private List<CardDocument> playerHand;
    private List<CardDocument> dealerHand;
    private String status;

}
