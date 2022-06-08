package ch.bzz.militaryranking.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.Vector;

public class Country {

    private int countryID;
    private int militaryPower;
    @JsonIgnore
    private Vector<Vehicle> vehicles;

    @FormParam("name")
    @NotEmpty
    @Size(min=5, max=40)
    private String name;

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

    /**
     * gets countryID
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * sets CountryID
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}
