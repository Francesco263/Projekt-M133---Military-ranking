package ch.bzz.militaryranking.data;

import ch.bzz.militaryranking.model.Vehicle;
import ch.bzz.militaryranking.model.Country;
import ch.bzz.militaryranking.model.Weapon;
import ch.bzz.militaryranking.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static DataHandler instance = null;
    private List<Country> countryList;
    private List<Vehicle> vehicleList;
    private List<Weapon> weaponList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setCountryLists(new ArrayList<>());
        readCountryJSON();

        setVehicleList(new ArrayList<>());
        readVehicleJSON();

        setWeaponList(new ArrayList<>());
        readWeaponJSON();

    }

    /**
     * gets the only instance of this class
     * @return
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }

    /**
     * reads all countries
     * @return list of countries
     */
    public List<Country> readAllCountries() {
        return getCountryList();
    }
    /**
     * reads all vehicles
     * @return list of vehicles
     */
    public List<Vehicle> readAllVehicles() {
        return getVehicleList();
    }
    /**
     * reads all weapons
     * @return list of weapons
     */
    public List<Weapon> readAllWeapons() {
        return getWeaponList();
    }

    /**
     * reads a vehicle by its name
     * @param vehicleName
     * @return vehicle (null=not found)
     */
    public Vehicle readVehicleByName(String vehicleName) {
        Vehicle vehicle = null;
        for (Vehicle entry : getVehicleList()) {
            if (entry.getVehicleName().equals(vehicleName)) {
                vehicle = entry;
            }
        }
        return vehicle;
    }
    /**
     * reads a weapon by its name
     * @param weaponName
     * @return weapon (null=not found)
     */
    public Weapon readWeaponByName(String weaponName) {
        Weapon weapon = null;
        for (Weapon entry : getWeaponList()) {
            if (entry.getWeaponName().equals(weaponName)) {
                weapon = entry;
            }
        }
        return weapon;
    }

    /**
     * reads a country by its name
     * @param name
     * @return country (null=not found)
     */
    public Country readCountryByName(String name) {
        Country country = null;
        for (Country entry : getCountryList()) {
            if (entry.getName().equals(name)) {
                country = entry;
            }
        }
        return country;
    }

    /**
     * reads the country from the JSON-file
     */
    private void readCountryJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("countryJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Country[] countries = objectMapper.readValue(jsonData, Country[].class);
            for (Country country : countries) {
                getCountryList().add(country);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the vehicle from the JSON-file
     */
    private void readVehicleJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("vehicleJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Vehicle[] vehicles = objectMapper.readValue(jsonData, Vehicle[].class);
            for (Vehicle vehicle : vehicles) {
                getVehicleList().add(vehicle);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the weapons from the JSON-file
     */
    private void readWeaponJSON() {
        try {
                String path = Config.getProperty("weaponJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Weapon[] weapons = objectMapper.readValue(jsonData, Weapon[].class);
            for (Weapon weapon : weapons) {
                getWeaponList().add(weapon);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * gets country list
     *
     * @return value of country
     */
    private List<Country> getCountryList() {
        return countryList;
    }

    /**
     * sets country list
     *
     * @param countryList the value to set
     */
    private void setCountryLists(List<Country> countryList) {
        this.countryList = countryList;
    }

    /**
     * gets vehicle list
     *
     * @return value of vehicle
     */
    private List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    /**
     * sets vehicle list
     *
     * @param vehicleList the value to set
     */
    private void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    /**
     * gets weapon list
     *
     * @return value of weapon
     */
    private List<Weapon> getWeaponList() {
        return weaponList;
    }

    /**
     * sets weapon list
     *
     * @param weaponList the value to set
     */
    private void setWeaponList(List<Weapon> weaponList) {
        this.weaponList = weaponList;
    }

}