package cat.itacademy.blackjack.player.domain.port.out.query;

public record PlayerSummary(String id,
                            String name,
                            int score
) {
}
