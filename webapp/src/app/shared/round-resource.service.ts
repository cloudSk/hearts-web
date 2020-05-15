import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import * as SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs';
import {environment} from '../../environments/environment';
import {Round} from './round';
import {Card} from '../game-play/card';


@Injectable({
  providedIn: 'root'
})
export class RoundResourceService {
  private client: Client;

  constructor(private http: HttpClient) {
    this.client = this.initializeClient();
  }

  startRound(gameId: string): Observable<Round> {
    const url = `${environment.apiUrl}/games/${gameId}/rounds`;
    return this.http.post<Round>(url, null).pipe(
      tap((newRound: Round) => console.log(`Round with id=${newRound.id} created`))
    );
  }

  getRemainingCardsInRound(gameId: string, roundId: string): Observable<Card[]> {
    const url = `${environment.apiUrl}/games/${gameId}/rounds/${roundId}/cards`;
    return this.http.get<Card[]>(url);
  }

  subscribeCurrentRound(gameId: string): Observable<Round> {
    return new Observable<Round>(observer => {
      this.client.onConnect = () => {
        this.client.subscribe(`/topic/activeRound/${gameId}`, (message => {
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
