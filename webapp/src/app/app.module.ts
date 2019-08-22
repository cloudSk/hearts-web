import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { FormsModule } from '@angular/forms';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {PlayerListComponent} from './player/player-list.component';
import {CreateGameComponent} from "./game/create-game.component";

const appRoutes: Routes = [
  { path: 'create-game', component: CreateGameComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    PlayerListComponent,
    CreateGameComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
