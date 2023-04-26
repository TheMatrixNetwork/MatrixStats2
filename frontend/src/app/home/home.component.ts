import {Component, ElementRef, Inject, OnInit, Renderer2} from '@angular/core';
import {AuthService} from "../auth.service";
import {StatsService} from "../stats.service";
import {Router} from "@angular/router";
import {DOCUMENT, NgFor} from "@angular/common";
import {PlayerStats} from "../models/player-stats";
import { NgForm } from '@angular/forms';
import {PlayerKill} from "../models/player-kill";
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})

export class HomeComponent implements OnInit {
  playerName: string = "";
  searchPlayerName: string = "";
  matriDexPlayers: PlayerKill[] = [];
  playerNotInMatriDex: boolean = false;

  constructor(private _statsService: StatsService) { }


  ngOnInit(): void {
    this._statsService.getKilledPeople().subscribe(kills => {
      this.matriDexPlayers = kills;
    })
  }

  submit() {
    if(this.matriDexPlayers.map(kill => kill.killedUsername).indexOf(this.searchPlayerName) < 0) {
      this.playerNotInMatriDex = true;
    }
    else {
      this.playerNotInMatriDex = false;
      this.playerName = this.searchPlayerName;
    }
  }
}
