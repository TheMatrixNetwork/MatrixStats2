package org.matrixnetwork.stats2.handler;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.matrixnetwork.stats2.MatrixStats;
import org.matrixnetwork.stats2.entity.MatrixPlayer;
import org.matrixnetwork.stats2.entity.PlayerStats;
import org.matrixnetwork.stats2.manager.DataManager;

import java.time.LocalDateTime;

public class StatsHandler {
    private BukkitTask runnable;

    private static StatsHandler instance;

    private StatsHandler() {
    }

    public static StatsHandler getInstance() {
        if(instance == null)
            init();

        return instance;
    }

    public static void init() {
        instance = new StatsHandler();

        instance.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try (Session session = DataManager.getInstance().getSession()) {
                    Transaction t = session.beginTransaction();

                    for(Player p : MatrixStats.getPlugin().getServer().getOnlinePlayers()) {
                        MatrixPlayer player = DataManager.getInstance().getMatrixPlayerByProperty("uuid", p.getUniqueId().toString());

                        if(player == null) {
                            Session s = DataManager.getInstance().getSession();
                            Transaction tra = s.beginTransaction();

                            player = (MatrixPlayer) s.merge(new MatrixPlayer(p.getUniqueId().toString(), p.getName()));
                            tra.commit();
                        }

                        double balance = MatrixStats.getEcon().getBalance(p);
                        int prestige = MatrixStats.getElitePrestige(p.getName());
                        int guildRank = MatrixStats.getGuildRank(p.getName());
                        int sfLevel = MatrixStats.getSlimefunLevel(p.getName());
                        int threatTier = MatrixStats.getThreatTier(p.getName());
                            PlayerStats data = new PlayerStats(p.getExp(),
                                    p.getFoodLevel(),
                                    p.getLocation().getX(),
                                    p.getLocation().getY(),
                                    p.getLocation().getZ(),
                                    balance,
                                    p.getHealth(),
                                    p.getGameMode().toString(),
                                    p.getLastDamageCause() == null ? null : p.getLastDamageCause().getCause().toString(),
                                    p.getRemainingAir(),
                                    LocalDateTime.now(),
                                    guildRank,
                                    threatTier,
                                    sfLevel,
                                    prestige,
                                    player);
                            session.merge(data);
                    }

                    t.commit();

                }
            }
        }.runTaskTimer(MatrixStats.getPlugin(), 20*60*10, 20*60*10);
    }
}
