package org.matrixnetwork.stats2.pojo;

import org.matrixnetwork.stats2.entity.PlayerStats;

import java.time.format.DateTimeFormatter;

public record PlayerStatsDTO(
        float exp,
        int foodLevel,
        double loc_x,
        double loc_y,
        double loc_z,
        double money,
        double health,
        String gamemode,
        String lastDamageCause,
        int remainingAir,
        String timeStamp,
        int guildRank,
        int threatTier,
        int sfLevel,
        int prestige
) {
    public static PlayerStatsDTO from(PlayerStats stats) {
        return new PlayerStatsDTO(
                stats.getExp(),
                stats.getFoodLevel(),
                stats.getLoc_x(),
                stats.getLoc_y(),
                stats.getLoc_z(),
                stats.getMoney(),
                stats.getHealth(),
                stats.getGamemode(),
                stats.getLastDamageCause(),
                stats.getRemainingAir(),
                stats.getTimeStamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm")),
                stats.getGuildRank(),
                stats.getThreatTier(),
                stats.getSfLevel(),
                stats.getPrestige()
        );
    }
}
