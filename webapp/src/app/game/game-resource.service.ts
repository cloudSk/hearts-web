import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {environment} from "../../environments/environment";
import {Game} from "./game";


@Injectable({
  providedIn: 'root'
})
export class GameResourceService {
  private RESOURCE_URL = `${environment.apiUrl}/games`;

  constructor(private http: HttpClient) { }

  createGame() : Observable<Game> {
    return this.http.post<Game>(this.RESOURCE_URL, {}).pipe(
      tap((newGame: Game) => console.log(`Game with id=${newGame.id} created`))
    );
  }
}
