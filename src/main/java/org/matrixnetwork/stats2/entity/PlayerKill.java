package org.matrixnetwork.stats2.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PlayerKill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String killerUUID;
    private String killedUUID;

    public PlayerKill(String killerUUID, String killedUUID) {
        this.killerUUID = killerUUID;
        this.killedUUID = killedUUID;
    }

    public PlayerKill() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKillerUUID() {
        return killerUUID;
    }

    public void setKillerUUID(String killerUUID) {
        this.killerUUID = killerUUID;
    }

    public String getKilledUUID() {
        return killedUUID;
    }

    public void setKilledUUID(String killedUUID) {
        this.killedUUID = killedUUID;
    }
}
