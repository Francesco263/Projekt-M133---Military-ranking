package ch.bzz.militaryranking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

public class Vehicle {

    private String vehicleName;
    private int quantity;
    private int battlepoints;

    @JsonIgnore
    private Vector<Weapon> weapons;


    /**
     * gets name
     * @return vehicleName
     */
    public String getVehicleName() {
        return vehicleName;
    }

    /**
     * sets vehicle name
     * @param vehicleName
     */
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    /**
     * gets quantity
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * sets quantity
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * gets battle points
     * @return battlepoints
     */
    public int getBattlepoints() {
        return battlepoints;
    }

    /**
     * sets battle points
     * @param battlepoints
     */
    public void setBattlepoints(int battlepoints) {
        this.battlepoints = battlepoints;
    }

    /**
     * getss weapons
     * @return weapons
     */
    public Vector<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * sets weapons
     * @param weapons
     */
    public void setWeapons(Vector<Weapon> weapons) {
        this.weapons = weapons;
    }
}
