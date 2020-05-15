import {Component, OnInit, OnDestroy} from '@angular/core';
import {PlayerResourceService} from '../shared/player-resource.service';
import {Player} from '../shared/player';
import {ActivatedRoute, Router} from '@angular/router';
import {RoundResourceService} from '../shared/round-resource.service';
import {Round} from '../shared/round';

@Component({
  selector: 'app-waiting-area',
  templateUrl: './waiting-area.component.html'
})
export class WaitingAreaComponent implements OnInit, OnDestroy {
  player: Player = new Player();
  gameId: string;
  players: Player[] = [];

  constructor(private playerResourceService: PlayerResourceService, private roundResourceService: RoundResourceService,
              private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    const playerId = this.route.snapshot.queryParamMap.get('playerId');
    this.gameId = this.route.snapshot.queryParamMap.get('gameId');

    this.playerResourceService.findPlayer(this.gameId, playerId)
      .subscribe(player => this.player = player);
    this.playerResourceService.subscribeToPlayersInGame(this.gameId)
      .subscribe(players => this.players = players);
    this.roundResourceService.subscribeCurrentRound(this.gameId)
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
    this.playerResourceService.unsubscribe();
    this.roundResourceService.unsubscribe();
  }
}
