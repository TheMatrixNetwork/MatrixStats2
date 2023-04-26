import {Component, EventEmitter, OnInit, Output, Input, OnChanges, SimpleChanges} from '@angular/core';
import {PlayerStats} from "../../models/player-stats";
import {AuthService} from "../../auth.service";
import {StatsService} from "../../stats.service";

declare const genSkin: any;

@Component({
  selector: 'app-player-status',
  templateUrl: './player-status.component.html',
  styleUrls: ['./player-status.component.sass']
})
export class PlayerStatusComponent implements OnInit, OnChanges {
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
  @Input() playerName: string = '';

  constructor(private _authService: AuthService,
              private _statsService: StatsService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes["playerName"]) {
    this.updateData(this.playerName);
    }
  }

  ngOnInit(): void {
  }

  updateData(playerName: string) {
    if(playerName == '') {
      this._statsService.getSkinName().subscribe((skin: any) => {
        genSkin(skin.skin, 300, 500);
      });

      this._statsService.getLatestPlayerStats().subscribe(stats => {
        this.latestPlayerStats = stats;
        while(this.heartRows.length < stats.health) {
          this.heartRows.push(0)
        }
      })
    }
    else {
      genSkin(playerName, 300, 500);

      this._statsService.getMatrixDexPlayerStats(playerName).subscribe(stats => {
        this.latestPlayerStats = stats;
        while(this.heartRows.length < stats.health) {
          this.heartRows.push(0)
        }
      })
    }
  }

}
