import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Player} from './player';
import {RxStompService} from '@stomp/ng2-stompjs';
import {Round} from './round';


@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  constructor(private rxStompService: RxStompService) {
  }

  watchPlayersInGame(gameId: string): Observable<Player[]> {
    return this.rxStompService.watch(`/topic/joinedPlayers/${gameId}`)
      .pipe(map(message => JSON.parse(message.body).content));
  }

  watchCurrentRound(gameId: string): Observable<Round> {
    return this.rxStompService.watch(`/topic/activeRound/${gameId}`)
      .pipe(map(message => JSON.parse(message.body).content));
  }
}
