package org.matrixnetwork.stats2.pojo;

import org.matrixnetwork.stats2.entity.PlayerKill;
import org.matrixnetwork.stats2.entity.PlayerStats;

import java.time.format.DateTimeFormatter;

public record PlayerKillDTO(
        String killerUUID,
        String killedUsername
) {
    public static PlayerKillDTO from(PlayerKill kill) {
        return new PlayerKillDTO(
                kill.getKiller().getUUID(),
                kill.getKilledUsername()
        );
    }
}
