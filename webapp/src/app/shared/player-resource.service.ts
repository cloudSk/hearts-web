import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {Player} from './player';
import {environment} from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PlayerResourceService {

  constructor(private http: HttpClient) {
  }

  joinGame(gameId: string, player: Player): Observable<Player> {
    const url = `${environment.httpApiUrl}/games/${gameId}/players`;
    return this.http.post<Player>(url, player).pipe(
      tap((newPlayer: Player) => console.log(`Player with id=${newPlayer.id} joined the game`))
    );
  }

  findPlayer(gameId: string, playerId: string): Observable<Player> {
    const url = `${environment.httpApiUrl}/games/${gameId}/players/${playerId}`;
    return this.http.get<Player>(url);
  }
}
