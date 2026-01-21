package cat.itacademy.blackjack.player.domain.model;

public class Player {

    private final PlayerId id;
    private final String name;
    private final int score;

    private Player(PlayerId id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public static Player create(String name) {
        return new Player(PlayerId.newId(), name, 0);
    }

    public Player rename(String newName) {
        return new Player(id, newName, score);
    }

    public Player addScore(int points) {
        return new Player(id, name, score + points);
    }

    public PlayerId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public int score() {
        return score;
    }

}
