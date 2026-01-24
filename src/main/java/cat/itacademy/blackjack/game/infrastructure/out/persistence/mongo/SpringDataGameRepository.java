package cat.itacademy.blackjack.game.infrastructure.out.persistence.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpringDataGameRepository  extends ReactiveMongoRepository<GameDocument, String> {
}
