package ch.bzz.militaryranking.model;
import ch.bzz.militaryranking.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.Vector;

public class Country {

    private int militaryPower;
    private Vector<Vehicle> vehicles;

    @FormParam("countryID")
    private int countryID;

    @FormParam("name")
    @NotEmpty
    @Size(min=2, max=40)
    private String name;

    @FormParam("vehicleIDs")
    @JsonIgnore
    @NotEmpty
    private String vehicleIDs;

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
     * sets vehicles and its attributes
     * calculates the militaryPower based on the vehicles
     * @param vehicles
     */
    public void setVehicles(Vector<Vehicle> vehicles) {
        for (int i = 0; i < vehicles.size(); i++){
            Vehicle vehicle = DataHandler.readVehicleByID(Integer.toString(vehicles.get(i).getVehicleID()));
            vehicles.get(i).setRegistrationDate(vehicle.getRegistrationDate());
            vehicles.get(i).setVehicleName(vehicle.getVehicleName());
            vehicles.get(i).setQuantity(vehicle.getQuantity());
            vehicles.get(i).setBattlepoints(vehicle.getBattlepoints());
            militaryPower = militaryPower + (vehicles.get(i).getBattlepoints() * vehicles.get(i).getQuantity());
        }
        this.vehicles = vehicles;
    }

    /**
     * gets vehicleIds
     * @return
     */
    public String getVehicleIDs() {
        return vehicleIDs;
    }

    /**
     * sets vehicleIDs
     * @param vehicleIDs
     */
    public void setVehicleIDs(String vehicleIDs) {
        this.vehicleIDs = vehicleIDs;
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
