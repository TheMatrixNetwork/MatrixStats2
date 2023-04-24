package org.matrixnetwork.stats2.handler;

import com.gmail.mrphpfan.mccombatlevel.McCombatLevel;
import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
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

import static com.gmail.mrphpfan.mccombatlevel.calculator.JavaScriptCalculator.*;
import static com.projectkorra.projectkorra.BendingPlayer.getBendingPlayer;

public class StatsHandler {
    private static StatsHandler instance;
    private BukkitTask runnable;

    private StatsHandler() {
    }

    public static StatsHandler getInstance() {
        if (instance == null)
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

                    for (Player p : MatrixStats.getPlugin().getServer().getOnlinePlayers()) {
                        MatrixPlayer player = DataManager.getInstance().getMatrixPlayerByProperty("uuid", p.getUniqueId().toString());

                        if (player == null) {
                            Session s = DataManager.getInstance().getSession();

                            player = (MatrixPlayer) s.merge(new MatrixPlayer(p.getUniqueId().toString(), p.getName()));
                        }

                        double balance = MatrixStats.getEcon().getBalance(p);
                        String guildRank = getGuildRankName(p.getName());
                        String sfTitle = getSlimefunTitle(p.getName());
                        int threatTier = getThreatTier(p.getName());
                        String mageRank = "Beginner"; //placeholder for now
                        String skillClass = getSkillClass(p.getName());
                        int skillLevel = getSkillLevel(p.getName());
                        int mcmmoPower = (int) getMcMMOLvl(p);
                        String element = "";
                        BendingPlayer bPlayer = getBendingPlayer(p);
                        for (Element e : bPlayer.getElements()) {
                            element = e.getColor() + e.getName() + ", ";
                        }
                        element = bPlayer.getElements().size() == 0 ? "None" : element;
                        int matrik = McCombatLevel.inst().getPlayerLevels().get(p.getName());
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
                                sfTitle,
                                mcmmoPower,
                                mageRank,
                                skillClass,
                                skillLevel,
                                element,
                                matrik,
                                player);
                        session.merge(data);
                    }

                    t.commit();

                }
            }
        }.runTaskTimer(MatrixStats.getPlugin(), 20 * 60, 20 * 60);
    }
}
