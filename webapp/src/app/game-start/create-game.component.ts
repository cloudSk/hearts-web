import {Component} from '@angular/core';
import {GameResourceService} from './game-resource.service';
import {PlayerResourceService} from '../shared/player-resource.service';
import {Player} from '../shared/player';
import {Router} from '@angular/router';

@Component({
  selector: 'app-create-game',
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
    const playerToCreate = new Player();
    playerToCreate.name = this.playerName;

    this.gameResourceService.createGame().subscribe(
      game => this.playerResourceService.joinGame(game.id, playerToCreate).subscribe(
        player => this.router.navigate(['waiting-area'], {queryParams: {playerId: player.id, gameId: game.id}})
      )
    );
  }
}
