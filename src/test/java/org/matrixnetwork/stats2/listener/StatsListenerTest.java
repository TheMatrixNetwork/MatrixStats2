package org.matrixnetwork.stats2.listener;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.matrixnetwork.stats2.TestBase;
import org.matrixnetwork.stats2.entity.MatrixPlayer;
import org.matrixnetwork.stats2.entity.PlayerKill;
import org.matrixnetwork.stats2.manager.DataManager;

import javax.transaction.Transactional;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StatsListenerTest extends TestBase {
    @Test
    @Transactional
    void testPlayerKillsPersistence() {
        Random rand = new Random();
        PlayerMock killed = server.addPlayer("Player0-" + System.currentTimeMillis());
        PlayerMock killer = server.addPlayer("Player1-" + System.currentTimeMillis());

        killed.setKiller(killer);
        killed.setHealth(0);

        try (Session session = DataManager.getInstance().getSession()) {
            MatrixPlayer killerPlayer = DataManager.getInstance().getMatrixPlayerByProperty("uuid", killer.getUniqueId().toString(), session);

            server.getPluginManager().assertEventFired(PlayerDeathEvent.class);
            assertTrue(
                    killerPlayer.getKills()
                            .stream().map(PlayerKill::getKilledUUID).toList()
                            .contains(killed.getUniqueId().toString()));
            assertTrue(
                    killerPlayer.getKills()
                            .stream().map(PlayerKill::getKilledUsername).toList()
                            .contains(killed.getName()));

        }
    }

}