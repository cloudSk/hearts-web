import { Component, OnInit } from '@angular/core';
import {PlayerResourceService} from "./player-resource.service";
import {Player} from "./player";

@Component({
  selector: 'player-list',
  templateUrl: './player-list.component.html',
  styleUrls: ['./player-list.component.css']
})
export class PlayerListComponent implements OnInit {
  playerList: Player[];

  constructor(private playerResourceService: PlayerResourceService) { }

  ngOnInit() {
    this.playerResourceService.findAll().subscribe(
      players => this.playerList = players
    );
  }
}
