package cat.itacademy.blackjack.game.infrastructure.in.web.controller;
import cat.itacademy.blackjack.game.domain.port.in.CreateGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.DeleteGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.GetGameUseCase;
import cat.itacademy.blackjack.game.domain.port.in.PlayMoveUseCase;
import cat.itacademy.blackjack.game.infrastructure.in.web.dto.CreateGameRequest;
import cat.itacademy.blackjack.game.infrastructure.in.web.dto.GameResponse;
import cat.itacademy.blackjack.game.infrastructure.in.web.dto.PlayRequest;
import cat.itacademy.blackjack.game.infrastructure.in.web.mapper.GameApiMapper;
import cat.itacademy.blackjack.game.infrastructure.in.web.mapper.PlayApiMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Tag(name = "Game", description = "Blackjack game operations")
public class GameController {


    private final CreateGameUseCase createGame;
    private final GetGameUseCase getGame;
    private final PlayMoveUseCase playMove;
    private final DeleteGameUseCase deleteGame;

    @PostMapping("/new")
    @Operation(summary = "Create a new Blackjack game")
    public Mono<ResponseEntity<GameResponse>> create(@RequestBody CreateGameRequest request) {
        return createGame.create(request.playerId())
                .map(GameApiMapper::fromDomain)
                .map(body -> ResponseEntity.status(HttpStatus.CREATED).body(body));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get game details")
    public Mono<ResponseEntity<GameResponse>> get(@PathVariable String id) {
        return getGame.getById(id)
                .map(GameApiMapper::fromDomain)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{id}/play")
    @Operation(summary = "Play a move in the game")
    public Mono<ResponseEntity<GameResponse>> play(@PathVariable String id,
                                                   @RequestBody PlayRequest request) {
        return playMove.play(id, PlayApiMapper.toDomain(request))
                .map(GameApiMapper::fromDomain)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete a game")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return deleteGame.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

}
