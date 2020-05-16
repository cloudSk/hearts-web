import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {Round} from './round';
import {Card} from '../game-play/card';


@Injectable({
  providedIn: 'root'
})
export class RoundResourceService {

  constructor(private http: HttpClient) {
  }

  startRound(gameId: string): Observable<Round> {
    const url = `${environment.httpApiUrl}/games/${gameId}/rounds`;
    return this.http.post<Round>(url, null).pipe(
      tap((newRound: Round) => console.log(`Round with id=${newRound.id} created`))
    );
  }

  getRemainingCardsInRound(gameId: string, roundId: string): Observable<Card[]> {
    const url = `${environment.httpApiUrl}/games/${gameId}/rounds/${roundId}/cards`;
    return this.http.get<Card[]>(url);
  }
}
