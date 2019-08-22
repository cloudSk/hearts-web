import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import {Player} from "./player";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class PlayerResourceService {
  constructor(private http: HttpClient) { }

  joinGame(gameId: string, player: Player): Observable<Player> {
    let url = `${environment.apiUrl}/games/${gameId}/players`;
    return this.http.post<Player>(url, player).pipe(
      tap((newPlayer: Player) => console.log(`Player with id=${newPlayer.id} joined the game`))
    );
  }

  findAllPlayersInGame(gameId: string) : Observable<Player[]> {
    let url = `${environment.apiUrl}/games/${gameId}/players`;
    return this.http.get<Player[]>(url).pipe(
      map((result:any) => {
        if (result._embedded == null) {
          return [];
        }

        return result._embedded.playerDtoList
      })
    )
  }

  findPlayer(gameId: string, playerId: string) : Observable<Player> {
    let url = `${environment.apiUrl}/games/${gameId}/players/${playerId}`;
    return this.http.get<Player>(url);
  }
}
