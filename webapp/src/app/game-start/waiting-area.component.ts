import {Component, OnDestroy, OnInit} from '@angular/core';
import {PlayerResourceService} from '../shared/player-resource.service';
import {Player} from '../shared/player';
import {ActivatedRoute, Router} from '@angular/router';
import {Round} from '../shared/round';
import {WebsocketService} from '../shared/websocket.service';
import {RoundResourceService} from '../shared/round-resource.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-waiting-area',
  templateUrl: './waiting-area.component.html'
})
export class WaitingAreaComponent implements OnInit, OnDestroy {
  player: Player = new Player();
  gameId: string;
  players: Player[] = [];
  playersSubscription: Subscription;
  roundSubscription: Subscription;

  constructor(private playerResourceService: PlayerResourceService, private websocketService: WebsocketService,
              private roundResourceService: RoundResourceService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    const playerId = this.route.snapshot.queryParamMap.get('playerId');
    this.gameId = this.route.snapshot.queryParamMap.get('gameId');

    this.playerResourceService.findPlayer(this.gameId, playerId)
      .subscribe(player => this.player = player);
    this.playersSubscription = this.websocketService.watchPlayersInGame(this.gameId)
      .subscribe(players => this.players = players);
    this.roundSubscription = this.websocketService.watchCurrentRound(this.gameId)
      .subscribe(round => this.navigateToPlayRound(round));
  }

  ngOnDestroy(): void {
    this.unsubscribeWebSockets();
  }

  waitingOnPlayers(): boolean {
    return this.players.length < 4;
  }

  startGame() {
    this.roundResourceService.startRound(this.gameId)
      .subscribe(round => this.navigateToPlayRound(round));
  }

  private navigateToPlayRound(round: Round) {
    this.unsubscribeWebSockets();
    this.router.navigate(['play-round'], {queryParams: {playerId: this.player.id, gameId: this.gameId,
        roundId: round.id
    }});
  }

  private unsubscribeWebSockets() {
    this.playersSubscription.unsubscribe();
    this.roundSubscription.unsubscribe();
  }
}
