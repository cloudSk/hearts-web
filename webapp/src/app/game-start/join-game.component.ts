import {Component} from '@angular/core';
import {PlayerResourceService} from '../shared/player-resource.service';
import {Player} from '../shared/player';
import {Router} from '@angular/router';

@Component({
  selector: 'app-join-game',
  templateUrl: './join-game.component.html'
})
export class JoinGameComponent {
  playerName: string;
  gameId: string;

  constructor(private playerResourceService: PlayerResourceService, private router: Router) { }

  joinGame() {
    const playerToJoin = new Player();
    playerToJoin.name = this.playerName;

    this.playerResourceService.joinGame(this.gameId, playerToJoin)
      .subscribe(player => this.router.navigate(['waiting-area'], {queryParams: {playerId: player.id, gameId: this.gameId}}));
  }
}
