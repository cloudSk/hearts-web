import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {Player} from "./player";


@Injectable({
  providedIn: 'root'
})
export class PlayerResourceService {
  private RESOURCE_URL = '/players';

  constructor(private http: HttpClient) { }

  findAll() : Observable<Player[]> {
    return this.http.get<Player[]>(this.RESOURCE_URL).pipe(
      map((result:any) => {
        return result._embedded.playerDtoList
      })
    )
  }
}
