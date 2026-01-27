package cat.itacademy.blackjack.player.domain.port.out.query;

public record PlayerSummary(String domainId,
                            String name,
                            int score,
                            int totalGames
) {
}
