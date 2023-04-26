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
    guildRankName: "",
    threatTier: 0,
    sfTitle: "",
    skillLevel: 0,
    mcmmoPower: 0,
    skillClass: "",
    mageRank: "",
    element: "",
    matrik: 0
  }
  heartHeartsPerRowArr: number[] = []
  heartRows: number[] = [];
  @Input() playerName: string = '';
  username: string = '';
  heartsInLastRow: number[] = [];

  HEALTH_HEARTS_PER_ROW = 10;

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
        genSkin(skin.skin, 300, 350);
      });

      this._statsService.getLatestPlayerStats().subscribe(stats => {
        this.latestPlayerStats = stats;
        this.calculateHealthRows(this.latestPlayerStats.health);
      })

      this.username = this._authService.getUsername()
    }
    else {
      genSkin(playerName, 300, 350);

      this._statsService.getMatrixDexPlayerStats(playerName).subscribe(stats => {
        this.latestPlayerStats = stats;
        this.calculateHealthRows(this.latestPlayerStats.health);
      })

      this.username = playerName
    }
  }

  private calculateHealthRows(health: number) {
    health = 25
    const amountFullRows = Math.floor(health / this.HEALTH_HEARTS_PER_ROW);
    const amountHearthsInLastRow = health-(amountFullRows*this.HEALTH_HEARTS_PER_ROW)
    while (this.heartHeartsPerRowArr.length < this.HEALTH_HEARTS_PER_ROW ) {
      this.heartHeartsPerRowArr.push(0)
    }
    this.heartRows = [];
    while (this.heartRows.length < amountFullRows ) {
      this.heartRows.push(0)
    }
    this.heartsInLastRow = [];
    while (this.heartsInLastRow.length < amountHearthsInLastRow ) {
      this.heartsInLastRow.push(0)
    }
  }

}
