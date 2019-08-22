import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { FormsModule } from '@angular/forms';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {PlayerListComponent} from './player/player-list.component';
import {CreateGameComponent} from "./game-start/create-game.component";
import {WaitingAreaComponent} from "./game-start/waiting-area.component";

const appRoutes: Routes = [
  { path: 'create-game', component: CreateGameComponent },
  { path: 'waiting-area', component: WaitingAreaComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    PlayerListComponent,
    CreateGameComponent,
    WaitingAreaComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, { useHash: true }),
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
