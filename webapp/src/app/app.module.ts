import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { FormsModule } from '@angular/forms';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {CreateGameComponent} from "./game-start/create-game.component";
import {WaitingAreaComponent} from "./game-start/waiting-area.component";
import {WelcomeScreenComponent} from "./welcome-screen.component";

const appRoutes: Routes = [
  { path: 'create-game', component: CreateGameComponent },
  { path: 'waiting-area', component: WaitingAreaComponent },
  { path: 'welcome-screen', component: WelcomeScreenComponent },
  { path: '', redirectTo: '/welcome-screen', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    CreateGameComponent,
    WaitingAreaComponent,
    WelcomeScreenComponent
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
