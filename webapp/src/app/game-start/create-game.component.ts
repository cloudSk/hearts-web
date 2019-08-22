import {Component} from '@angular/core';
import {GameResourceService} from "./game-resource.service";
import {PlayerResourceService} from "./player-resource.service";
import {Player} from "./player";
import {Router} from '@angular/router';

@Component({
  selector: 'create-game',
  templateUrl: './create-game.component.html'
})
export class CreateGameComponent {
  playerName: string;

  constructor(
    private gameResourceService: GameResourceService,
    private playerResourceService: PlayerResourceService,
    private router: Router
  ) { }

  createGame() {
    let player = new Player();
    player.name = this.playerName;

    this.gameResourceService.createGame().subscribe(
      game => this.playerResourceService.joinGame(game.id, player).subscribe(
        player => this.router.navigate(['waiting-area'], {queryParams: {playerId: player.id, gameId: game.id}})
      )
    );
  }
}
