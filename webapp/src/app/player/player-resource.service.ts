import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {Player} from "./player";
import {environment} from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class PlayerResourceService {
  private RESOURCE_URL = environment.apiUrl + '/players';

  constructor(private http: HttpClient) { }

  findAll() : Observable<Player[]> {
    return this.http.get<Player[]>(this.RESOURCE_URL).pipe(
      map((result:any) => {
        return result._embedded.playerDtoList
      })
    )
  }
}
