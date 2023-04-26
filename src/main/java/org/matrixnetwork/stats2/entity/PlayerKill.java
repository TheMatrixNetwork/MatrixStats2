package org.matrixnetwork.stats2.entity;

import org.matrixnetwork.stats2.MatrixStats;

import javax.persistence.*;

@Entity
public class PlayerKill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = MatrixPlayer.class)
    private MatrixPlayer killer;
    private String killedUUID;
    private String killedUsername;

    public PlayerKill(MatrixPlayer killer, String killedUUID, String killedUsername) {
        this.killer = killer;
        this.killedUUID = killedUUID;
        this.killedUsername = killedUsername;
    }

    public PlayerKill() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKilledUUID() {
        return killedUUID;
    }

    public void setKilledUUID(String killedUUID) {
        this.killedUUID = killedUUID;
    }

    public String getKilledUsername() {
        return killedUsername;
    }

    public void setKilledUsername(String killedUsername) {
        this.killedUsername = killedUsername;
    }

    public MatrixPlayer getKiller() {
        return killer;
    }

    public void setKiller(MatrixPlayer killer) {
        this.killer = killer;
    }
}
