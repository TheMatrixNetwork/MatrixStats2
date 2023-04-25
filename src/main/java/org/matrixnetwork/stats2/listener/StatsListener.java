package org.matrixnetwork.stats2.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.matrixnetwork.stats2.entity.MatrixPlayer;
import org.matrixnetwork.stats2.entity.PlayerKill;
import org.matrixnetwork.stats2.manager.DataManager;

public class StatsListener implements Listener {
    private DataManager dataManager = DataManager.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        try(Session s = dataManager.getSession();) {
            MatrixPlayer player = DataManager.getInstance()
                    .getMatrixPlayerByProperty("uuid", p.getUniqueId().toString(), s);

            if (player == null) {
                Transaction t = s.beginTransaction();
                s.merge(new MatrixPlayer(p.getUniqueId().toString(), p.getName()));
                t.commit();
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(event.getEntity().getKiller() != null) {
            Player p = event.getPlayer();
            Player killerP =  p.getKiller();

            try(Session s = dataManager.getSession()) {
                Transaction t = s.beginTransaction();
                MatrixPlayer killer = dataManager
                        .getMatrixPlayerByProperty("uuid", killerP.getUniqueId().toString(), s);
                s.merge(new PlayerKill(killer,
                        p.getUniqueId().toString(), p.getName()));
                t.commit();
            }
        }
    }
}
