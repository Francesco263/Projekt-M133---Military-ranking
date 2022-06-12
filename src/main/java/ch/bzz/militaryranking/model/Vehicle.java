package ch.bzz.militaryranking.model;

import ch.bzz.militaryranking.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.Vector;

public class Vehicle {

    private Vector<Weapon> weapons;

    @FormParam("vehicleID")
    private int vehicleID;

    @FormParam("registrationDate")
    @NotEmpty
    @Pattern(regexp = "^\\d{2}\\.\\d{2}\\.\\d{4}$")
    private String registrationDate;

    @FormParam("vehicleName")
    @NotEmpty
    @Size(min=2, max=40)
    private String vehicleName;

    @FormParam("quantity")
    @NotNull
    //@Range(min=5, max=40)
    private int quantity;

    @FormParam("battlepoints")
    @NotNull
    //@Range(min=5, max=40)
    private int battlepoints;

    @FormParam("weaponIDs")
    @JsonIgnore
    @NotEmpty
    private String weaponIDs;

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
        for (int i = 0; i < weapons.size(); i++){
            Weapon weapon = DataHandler.readWeaponByID(Integer.toString(weapons.get(i).getWeaponID()));
            weapons.get(i).setSecureCode(weapon.getSecureCode());
            weapons.get(i).setWeaponName(weapon.getWeaponName());
            weapons.get(i).setBattlepoints(weapon.getBattlepoints());
            battlepoints = battlepoints + (weapons.get(i).getBattlepoints() * quantity);
        };
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

    /**
     * gets weaponIDs
      * @return
     */
    public String getWeaponIDs() {
        return weaponIDs;
    }

    /**
     * sets weaponIDs
     * @param weaponIDs
     */
    public void setWeaponIDs(String weaponIDs) {
        this.weaponIDs = weaponIDs;
    }
}
