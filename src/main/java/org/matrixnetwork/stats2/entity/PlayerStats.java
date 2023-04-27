package org.matrixnetwork.stats2.entity;


import org.json.simple.JSONObject;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PlayerStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;

    private float exp;
    private int foodLevel;
    private double loc_x;
    private double loc_y;
    private double loc_z;
    private double money;
    private double health;
    private String gamemode;
    private String lastDamageCause;
    private int remainingAir;
    private LocalDateTime timeStamp;
    private String guildRankName;
    private int threatTier;
    private String sfTitle;
    private int mcmmoPower;
    private String mageRank;
    private String skillClass;
    private int skillLevel;
    private String element;
    private int matrik;
    private int kills;


    @ManyToOne(fetch = FetchType.EAGER, targetEntity = MatrixPlayer.class)
    private MatrixPlayer matrixPlayer;

    public PlayerStats(float exp,
                       int kills,
                       int foodLevel,
                       double loc_x,
                       double loc_y,
                       double loc_z,
                       double money,
                       double health,
                       String gamemode,
                       String lastDamageCause,
                       int remainingAir,
                       LocalDateTime timeStamp,
                       String guildRankName,
                       int threatTier,
                       String sfTitle,
                       int mcmmoPower,
                       String mageRank,
                       String skillClass,
                       int skillLevel,
                       String element,
                       int matrik,
                       MatrixPlayer matrixPlayer) {
        this.exp = exp;
        this.kills = kills;
        this.foodLevel = foodLevel;
        this.loc_x = loc_x;
        this.loc_y = loc_y;
        this.loc_z = loc_z;
        this.money = money;
        this.health = health;
        this.gamemode = gamemode;
        this.lastDamageCause = lastDamageCause;
        this.remainingAir = remainingAir;
        this.timeStamp = timeStamp;
        this.setGuildRankName(guildRankName);
        this.setThreatTier(threatTier);
        this.setSfTitle(sfTitle);
        this.setMcmmoPower(mcmmoPower);
        this.setMageRank(mageRank);
        this.setSkillClass(skillClass);
        this.setSkillLevel(skillLevel);
        this.setElement(getElement());
        this.setMatrik(matrik);
        this.matrixPlayer = matrixPlayer;
    }

    public PlayerStats() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getExp() {
        return exp;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public double getLoc_x() {
        return loc_x;
    }

    public double getLoc_y() {
        return loc_y;
    }

    public double getLoc_z() {
        return loc_z;
    }

    public double getMoney() {
        return money;
    }

    public double getHealth() {
        return health;
    }

    public String getGamemode() {
        return gamemode;
    }

    public String getLastDamageCause() {
        return lastDamageCause;
    }

    public int getRemainingAir() {
        return remainingAir;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }


    public int getThreatTier() {
        return threatTier;
    }

    public void setThreatTier(int threatTier) {
        this.threatTier = threatTier;
    }

    public String getSfTitle() {
        return sfTitle;
    }

    public void setSfTitle(String sfLevel) {
        this.sfTitle = sfTitle;
    }

    public int getMcmmoPower() {
        return mcmmoPower;
    }

    public void setMcmmoPower(int mcmmoPower) {
        this.mcmmoPower = mcmmoPower;
    }

    public String getMageRank() {
        return mageRank;
    }

    public void setMageRank(String mageRank) {
        this.mageRank = mageRank;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getSkillClass() {
        return skillClass;
    }

    public void setSkillClass(String skillClass) {
        this.skillClass = skillClass;
    }

    public int getMatrik() {
        return matrik;
    }

    public void setMatrik(int matrik) {
        this.matrik = matrik;
    }

    public String getGuildRankName() {
        return guildRankName;
    }

    public void setGuildRankName(String guildRankName) {
        this.guildRankName = guildRankName;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }
}
