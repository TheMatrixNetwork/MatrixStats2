package org.matrixnetwork.stats2.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.matrixnetwork.stats2.entity.MatrixPlayer;
import org.matrixnetwork.stats2.manager.DataManager;

public class StatsListener  implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        MatrixPlayer player = DataManager.getInstance()
                .getMatrixPlayerByProperty("uuid", p.getUniqueId().toString());

        if(player == null) {
            Session s = DataManager.getInstance().getSession();
            Transaction t = s.beginTransaction();
            s.merge(new MatrixPlayer(p.getUniqueId().toString(), p.getName()));
            t.commit();
        }
    }
}
