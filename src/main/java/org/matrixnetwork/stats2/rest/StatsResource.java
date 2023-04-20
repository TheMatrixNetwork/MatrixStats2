package org.matrixnetwork.stats2.rest;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.matrixnetwork.stats2.MatrixStats;
import org.matrixnetwork.stats2.entity.MatrixPlayer;
import org.matrixnetwork.stats2.manager.DataManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/org/matrixnetwork/stats2")
public class StatsResource {

    @Path("/{username}")
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public Response meet(@PathParam("username") String username) {
        if(MatrixStats.getPlugin().getServer().getPlayer(username) != null) {
            Player p = MatrixStats.getPlugin().getServer().getPlayer(username);
            MatrixPlayer mP = DataManager.getInstance().getMatrixPlayerByProperty("uuid", p.getUniqueId().toString());

            if(mP == null) {
                Session s = DataManager.getInstance().getSession();
                Transaction t = s.beginTransaction();

                mP = (MatrixPlayer) s.merge(new MatrixPlayer(p.getUniqueId().toString(), p.getName()));
                t.commit();
            }

            return Response.ok(mP.toJson().toJSONString()).build();
        }
        else {
            return Response.status(404).build();
        }
    }
}
