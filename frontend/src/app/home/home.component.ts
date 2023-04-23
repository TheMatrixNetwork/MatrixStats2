import {Component, ElementRef, Inject, OnInit, Renderer2} from '@angular/core';
import {AuthService} from "../auth.service";
import {StatsService} from "../stats.service";
import {Router} from "@angular/router";
import {DOCUMENT} from "@angular/common";
import {PlayerStats} from "../models/player-stats";

declare const genSkin: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})

export class HomeComponent implements OnInit {

  constructor(private _authService: AuthService,
              private _statsService: StatsService) { }

  latestPlayerStats: PlayerStats = {
    exp: 0,
    foodLevel: 0,
    loc_x: 0,
    loc_y: 0,
    loc_z: 0,
    money: 0,
    health: 0,
    gamemode: "",
    lastDamageCause: "",
    remainingAir: 0,
    timeStamp: "",
    guildRank: 0,
    threatTier: 0,
    sfLevel: 0,
    prestige: 0
  }
  heartRows: number[] = []


  ngOnInit(): void {
    this._statsService.getSkinName().subscribe((skin: any) => {
      console.log(skin.skin);

      genSkin(skin.skin, 300, 500);
    });

    this._statsService.getLatestPlayerStats().subscribe(stats => {
      this.latestPlayerStats = stats;
      while(this.heartRows.length < stats.health) {
        this.heartRows.push(0)
      }
    })
  }

}
