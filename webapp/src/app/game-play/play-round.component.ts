import {Component, OnInit, OnDestroy} from '@angular/core';
import {PlayerResourceService} from '../shared/player-resource.service';
import {Player} from '../shared/player';
import {ActivatedRoute} from '@angular/router';
import {RoundResourceService} from '../shared/round-resource.service';
import {Card} from './card';

@Component({
  selector: 'app-play-round',
  templateUrl: './play-round.component.html'
})
export class PlayRoundComponent implements OnInit, OnDestroy {
  player: Player = new Player();
  gameId: string;
  roundId: string;
  players: Player[] = [];
  currentHand: Card[] = [];

  constructor(private playerResourceService: PlayerResourceService, private roundResourceService: RoundResourceService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    const playerId = this.route.snapshot.queryParamMap.get('playerId');
    this.gameId = this.route.snapshot.queryParamMap.get('gameId');
    this.roundId = this.route.snapshot.queryParamMap.get('roundId');

    this.playerResourceService.findPlayer(this.gameId, playerId)
      .subscribe(player => this.player = player);
    this.playerResourceService.subscribeToPlayersInGame(this.gameId)
      .subscribe(players => this.players = players);
    this.roundResourceService.getRemainingCardsInRound(this.gameId, this.roundId)
      .subscribe(cards => this.currentHand = cards);
  }

  ngOnDestroy(): void {
    this.playerResourceService.unsubscribe();
    this.roundResourceService.unsubscribe();
  }

  cardSelected(): boolean {
    return false;
  }

  playCard() {
    if (!this.cardSelected()) {
      console.log('No card selected');
      return;
    }
  }
}
