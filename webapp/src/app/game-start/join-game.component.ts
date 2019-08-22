import {Component} from '@angular/core';
import {PlayerResourceService} from "./player-resource.service";
import {Player} from "./player";
import {Router} from '@angular/router';

@Component({
  selector: 'join-game',
  templateUrl: './join-game.component.html'
})
export class JoinGameComponent {
  playerName: string;
  gameId: string;

  constructor(private playerResourceService: PlayerResourceService, private router: Router) { }

  joinGame() {
    let player = new Player();
    player.name = this.playerName;

    this.playerResourceService.joinGame(this.gameId, player)
      .subscribe(player => this.router.navigate(['waiting-area'], {queryParams: {playerId: player.id, gameId: this.gameId}}));
  }
}
