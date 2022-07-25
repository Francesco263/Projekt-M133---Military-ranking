package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.Vehicle;
import ch.bzz.militaryranking.model.Weapon;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

/**
 * vehicle service
 */
@Path("vehicle")
public class VehicleService {

    private static int cntr = DataHandler.getVehicleCount();
    /**
     * lists all vehicles from vehicles.json
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listVehicle(
            @CookieParam("userRole") String userRole
    ){
        int httpStatus = 200;
        List<Vehicle> vehicleList = null;
        if (DataHandler.authenticate(userRole, 2)){
            vehicleList = DataHandler.readAllVehicles();
        }
        else {
            httpStatus = 403;
        }

        return Response
                .status(httpStatus)
                .entity(vehicleList)
                .build();
    }

    /**
     * lists the corresponding vehicle to given id
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readVehicle(
            @CookieParam("userRole") String userRole,
            @QueryParam("vehicleID") String vehicleID
    ){
        int httpStatus = 200;
        Vehicle vehicle = null;
        if (DataHandler.authenticate(userRole, 2)){
            vehicle = DataHandler.readVehicleByID(vehicleID);
            if (vehicle == null){
                httpStatus = 404;
            }
        }
        else{
            httpStatus = 403;
        }

        return Response
                .status(httpStatus)
                .entity(vehicle)
                .build();
    }

    /**
     * sorts the vehicle list by vehicleName or battlePoints or quantity
     */
    @GET
    @Path("sortList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortWeapon(
            @CookieParam("userRole") String userRole,
            @QueryParam("sortBy") String sort
    ){
        if (DataHandler.authenticate(userRole, 2)){
            List<Vehicle> vehicleList = DataHandler.readAllVehicles();
            if(sort != null && sort.equals("vehicleName")){
                Collections.sort(vehicleList, new Comparator<Vehicle>() {
                    @Override
                    public int compare(Vehicle vehicle, Vehicle t1) {
                        return vehicle.getVehicleName().compareTo(t1.getVehicleName());
                    }
                });
            }
            else if (sort!= null && sort.equals("battlepoints")){
                Collections.sort(vehicleList, new Comparator<Vehicle>() {
                    @Override
                    public int compare(Vehicle vehicle, Vehicle t1) {
                        return vehicle.getBattlepoints()-(t1.getBattlepoints());
                    }
                });
            }
            else if (sort!= null && sort.equals("quantity")){
                Collections.sort(vehicleList, new Comparator<Vehicle>() {
                    @Override
                    public int compare(Vehicle vehicle, Vehicle t1) {
                        return vehicle.getQuantity()-(t1.getQuantity());
                    }
                });
            }
            if (sort.contains("vehicleName") || sort.contains("battlepoints") || sort.contains("quantity")){
                return Response
                        .status(200)
                        .entity(vehicleList)
                        .build();
            }
            else{
                return Response
                        .status(404)
                        .entity(null)
                        .build();
            }
        }
        return Response
                .status(403)
                .entity("")
                .build();
    }

    /**
     * Inserts a new vehicle
     * @param vehicle the vehicle
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertVehicle(
            @CookieParam("userRole") String userRole,
            @Valid @BeanParam Vehicle vehicle
    ){
        int httpStatus = 200;
        if (DataHandler.authenticate(userRole, 1)){
            vehicle.setVehicleID(++cntr);
            if (getWeaponsFromID(vehicle) != null){
                vehicle.setWeapons(getWeaponsFromID(vehicle));
                DataHandler.insertVehicle(vehicle);
            }
            else{
                httpStatus = 400;
            }
        }
        else{
            httpStatus = 403;
        }

        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a vehicle identified by its id
     * @param vehicleID the key
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteVehicle(
            @CookieParam("userRole") String userRole,
            @QueryParam("vehicleID") String vehicleID
    ){
        int httpStatus = 200;
        if (DataHandler.authenticate(userRole, 1)){
            if (!DataHandler.deleteVehicle(vehicleID)){
                httpStatus = 410;
            }
            DataHandler.updateCountry();
        }
        else{
            httpStatus = 403;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a new vehicle
     * @param vehicle the vehicle
     * @return Response
     */
    @POST
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateVehicle(
            @CookieParam("userRole") String userRole,
            @Valid @BeanParam Vehicle vehicle
    ){
        int httpStatus = 200;
        if (DataHandler.authenticate(userRole, 1)){
            Vehicle oldVehicle = DataHandler.readVehicleByID(Integer.toString(vehicle.getVehicleID()));
            if (oldVehicle != null && getWeaponsFromID(vehicle) != null){
                oldVehicle.setVehicleName(vehicle.getVehicleName());
                oldVehicle.setQuantity(vehicle.getQuantity());
                oldVehicle.setBattlepoints(vehicle.getBattlepoints());
                oldVehicle.setRegistrationDate(vehicle.getRegistrationDate());
                oldVehicle.setWeapons(getWeaponsFromID(vehicle));
                DataHandler.updateVehicle();
            }
            else if (oldVehicle == null){
                httpStatus = 404;
            }
            else{
                httpStatus = 400;
            }
        }
        else{
            httpStatus = 403;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * return list of weapon object from weaponIds
     * @param vehicle
     * @return
     */
    public Vector<Weapon> getWeaponsFromID(Vehicle vehicle){
        String[] weaponIDs = vehicle.getWeaponIDs().split("\\s+");
        Vector<Weapon> weapons = new Vector<Weapon>();
        for (int i = 0; i < weaponIDs.length; i++){
            if (DataHandler.readWeaponByID(weaponIDs[i]) == null){
                return null;
            }
            Weapon weapon = new Weapon();
            weapon.setWeaponID(Integer.parseInt(weaponIDs[i]));
            weapons.add(weapon);
        }
        return weapons;
    }

}
