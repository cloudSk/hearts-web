import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import * as SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';
import {Player} from './player';
import {environment} from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PlayerResourceService {
  private client: Client;

  constructor(private http: HttpClient) {
    this.client = this.initializeClient();
  }

  joinGame(gameId: string, player: Player): Observable<Player> {
    const url = `${environment.apiUrl}/games/${gameId}/players`;
    return this.http.post<Player>(url, player).pipe(
      tap((newPlayer: Player) => console.log(`Player with id=${newPlayer.id} joined the game`))
    );
  }

  findPlayer(gameId: string, playerId: string): Observable<Player> {
    const url = `${environment.apiUrl}/games/${gameId}/players/${playerId}`;
    return this.http.get<Player>(url);
  }

  allPlayersInGame(gameId: string): Observable<Player[]> {
    return new Observable<Player[]>(observer => {
      this.client.onConnect = () => {
        this.client.subscribe(`/topic/joinedPlayers/${gameId}`, (message => {
          const body = JSON.parse(message.body);
          observer.next(body.content);
        }));
      };
      this.client.activate();
    });
  }

  unsubscribe() {
    this.client.deactivate();
  }

  private initializeClient(): Client {
    const client = new Client();
    client.webSocketFactory = () => {
      return new SockJS(`${environment.apiUrl}/hearts-web-websocket`);
    };
    client.onStompError = frame => {
      console.error(`error body: ${frame.body}`);
      console.error(`error message header: ${frame.headers.message}`);
    };
    client.onWebSocketError = frame => {
      console.error(`error type: ${frame.type}`);
    };
    return client;
  }
}
