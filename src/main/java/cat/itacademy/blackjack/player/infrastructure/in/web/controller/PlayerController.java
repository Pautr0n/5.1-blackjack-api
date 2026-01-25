package cat.itacademy.blackjack.player.infrastructure.in.web.controller;


import cat.itacademy.blackjack.player.domain.port.in.*;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.CreatePlayerRequest;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.PlayerResponse;
import cat.itacademy.blackjack.player.infrastructure.in.web.dto.RenamePlayerRequest;
import cat.itacademy.blackjack.player.infrastructure.in.web.mapper.PlayerApiMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
@Tag(name = "Player", description = "Player operations")
public class PlayerController {
    private final CreatePlayerUseCase createPlayer;
    private final RenamePlayerUseCase renamePlayer;
    private final GetPlayerUseCase getPlayer;
    private final FindAllPlayersUseCase findAllPlayers;
    private final SearchPlayerByNameUseCase searchPlayers;
    private final GetRankingUseCase getRanking;
    private final DeletePlayerUseCase deletePlayer;

    @PostMapping
    @Operation(summary = "Create a new player")
    public Mono<ResponseEntity<PlayerResponse>> create(@RequestBody CreatePlayerRequest request) {
        return createPlayer.create(request.name())
                .map(PlayerApiMapper::fromDomain)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Rename a player")
    public Mono<ResponseEntity<PlayerResponse>> rename(
            @PathVariable String id,
            @RequestBody RenamePlayerRequest request
    ) {
        return renamePlayer.rename(id, request.newName())
                .map(PlayerApiMapper::fromDomain)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a player by id")
    public Mono<ResponseEntity<PlayerResponse>> get(@PathVariable String id) {
        return getPlayer.getById(id)
                .map(PlayerApiMapper::fromDomain)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    @Operation(summary = "Get all players")
    public Flux<PlayerResponse> findAll() {
        return findAllPlayers.findAll()
                .map(PlayerApiMapper::fromDomain);
    }

    @GetMapping("/search")
    @Operation(summary = "Search players by name")
    public Flux<PlayerResponse> search(@RequestParam String name) {
        return searchPlayers.searchByName(name)
                .map(PlayerApiMapper::fromDomain);
    }

    @GetMapping("/ranking")
    @Operation(summary = "Get ranking of players")
    public Flux<PlayerResponse> ranking() {
        return getRanking.getRanking()
                .map(PlayerApiMapper::fromRanking);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a player")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return deletePlayer.deleteById(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

}
