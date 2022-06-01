package ch.bzz.militaryranking.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

public class Country {

    private String name;
    private int militaryPower;

    @JsonIgnore
    private Vector<Vehicle> vehicles;

    /**
     * gets name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets military power
     * @return militaryPower
     */
    public int getMilitaryPower() {
        return militaryPower;
    }

    /**
     * gets vehicle
     * @return vehicle
     */
    public Vector<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets military power
     * @param militaryPower
     */
    public void setMilitaryPower(int militaryPower) {
        this.militaryPower = militaryPower;
    }

    /**
     * sets vehicles
     * @param vehicles
     */
    public void setVehicles(Vector<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
