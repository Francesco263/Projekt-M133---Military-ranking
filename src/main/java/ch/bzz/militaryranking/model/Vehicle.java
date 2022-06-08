package ch.bzz.militaryranking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.Vector;

public class Vehicle {

    private int vehicleID;

    @JsonIgnore
    private Vector<Weapon> weapons;

    @FormParam("registrationDate")
    @NotEmpty
    @Pattern(regexp = "^\\d{2}\\.\\d{2}\\.\\d{4}$")
    private String registrationDate;

    @FormParam("vehicleName")
    @NotEmpty
    @Size(min=5, max=40)
    private String vehicleName;

    @FormParam("quantity")
    @NotNull
    //@Range(min=5, max=40)
    private int quantity;

    @FormParam("battlepoints")
    @NotNull
    //@Range(min=5, max=40)
    private int battlepoints;


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
     * gets weapons
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


    /**
     * gets vehicleID
     * @return vehicleID
     */
    public int getVehicleID() {
        return vehicleID;
    }

    /**
     * sets vehicleID
     * @param vehicleID
     */
    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    /**
     * gets registration date
     * @return
     */
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * sets registration date
     * @param registrationDate
     */
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
