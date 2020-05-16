import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {CreateGameComponent} from './game-start/create-game.component';
import {WaitingAreaComponent} from './game-start/waiting-area.component';
import {WelcomeScreenComponent} from './welcome-screen.component';
import {JoinGameComponent} from './game-start/join-game.component';
import {PlayRoundComponent} from './game-play/play-round.component';
import {InjectableRxStompConfig, RxStompService, rxStompServiceFactory} from '@stomp/ng2-stompjs';
import {RxStompConfig} from './rx-stomp.config';

const appRoutes: Routes = [
  { path: 'welcome-screen', component: WelcomeScreenComponent },
  { path: 'create-game', component: CreateGameComponent },
  { path: 'join-game', component: JoinGameComponent},
  { path: 'waiting-area', component: WaitingAreaComponent },
  { path: 'play-round', component: PlayRoundComponent },
  { path: '', redirectTo: '/welcome-screen', pathMatch: 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    WelcomeScreenComponent,
    CreateGameComponent,
    JoinGameComponent,
    WaitingAreaComponent,
    PlayRoundComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, { useHash: true }),
    FormsModule
  ],
  providers: [
    {
      provide: InjectableRxStompConfig,
      useValue: RxStompConfig
    },
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory,
      deps: [InjectableRxStompConfig]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
