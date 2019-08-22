import {Component, OnInit} from '@angular/core';
import {PlayerResourceService} from "../player/player-resource.service";
import {Player} from "../player/player";
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'waiting-area',
  templateUrl: './waiting-area.component.html'
})
export class WaitingAreaComponent implements OnInit {
  player: Player = new Player();
  gameId: string;
  players: Player[] = [];

  constructor(private playerResourceService: PlayerResourceService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    let playerId = this.route.snapshot.queryParamMap.get('playerId');
    this.gameId = this.route.snapshot.queryParamMap.get('gameId');

    this.playerResourceService.findPlayer(this.gameId, playerId)
      .subscribe(player => this.player = player);
    this.playerResourceService.findAllPlayersInGame(this.gameId)
      .subscribe(players => this.players = players);
  }

  waitingOnPlayers(): boolean {
    return this.players.length < 4;
  }

  startGame() {
  }
}
