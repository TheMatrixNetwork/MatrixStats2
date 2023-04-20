package org.matrixnetwork.stats2.rest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.matrixnetwork.stats2.MatrixStats;
import org.matrixnetwork.stats2.entity.Token;
import org.matrixnetwork.stats2.util.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/skin")
public class SkinResource {
    private JSONParser parser = new JSONParser();

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getSkin(@HeaderParam("Authorization") String tokenStr) {
        if(tokenStr == null) {
            return Response.status(400).build();
        }

        String username = Auth.getInstance().verifyToken(tokenStr);

        if(username != null) {
            JSONObject retObj = new JSONObject();
            retObj.put("skin", MatrixStats.getSkinsRestorerAPI().getSkinName(username));
            return Response.ok(retObj.toJSONString()).build();
        }
        else {
            return Response.status(403).build();
        }
    }

    @OPTIONS
    @Produces(MediaType.APPLICATION_JSON)
    public Response optionsForBookResource() {
        return Response.status(200)
                .header("Allow","POST")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Content-Length", "0")
                .build();
    }
}
