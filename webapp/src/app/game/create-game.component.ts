import { Component } from '@angular/core';
import {GameResourceService} from "./game-resource.service";
import {PlayerResourceService} from "../player/player-resource.service";
import {Player} from "../player/player";

@Component({
  selector: 'create-game',
  templateUrl: './create-game.component.html'
})
export class CreateGameComponent {
  playerName: string;

  constructor(private gameResourceService: GameResourceService, private playerResourceService: PlayerResourceService) { }

  createGame() {
    let player = new Player();
    player.name = this.playerName;

    this.gameResourceService.createGame().subscribe(
      game => this.playerResourceService.joinGame(game.id, player).subscribe(
        player => console.log('created')
      )
    );
  }
}
