package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.Vehicle;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * vehicle service
 */
@Path("vehicle")
public class VehicleService {

    /**
     * lists all vehicles from vehicles.json
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listVehicle(){
        List<Vehicle> vehicleList = DataHandler.readAllVehicles();
        return Response
                .status(200)
                .entity(vehicleList)
                .build();
    }

    /**
     * lists the corresponding vehicle to given name
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readVehicle(
            @QueryParam("vehicleName") String fahrzeugName
    ){
        Vehicle vehicle = DataHandler.readVehicleByName(fahrzeugName);
        int httpStatus;
        if (vehicle == null){
            httpStatus = 404;
        }
        else{
            httpStatus = 200;
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
            @QueryParam("sortBy") String sort
    ){
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

    /**
     * Inserts a new vehicle
     * @param vehicleName the name
     * @param quantity the quantity
     * @param battlepoints the battlepoints of the unarmed vehicle
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertVehicle(
            @FormParam("vehicleName") String vehicleName,
            @FormParam("quantity") String quantity,
            @FormParam("battlepoints") String battlepoints
    ){
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName(vehicleName);
        vehicle.setQuantity(Integer.parseInt(quantity));
        vehicle.setBattlepoints(Integer.parseInt(battlepoints));

        DataHandler.insertVehicle(vehicle);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * deletes a vehicle identified by its name
     * @param vehicleName the key
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteVehicle(
            @QueryParam("vehicleName") String vehicleName
    ){
        int httpStatus = 200;
        if (!DataHandler.deleteVehicle(vehicleName)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a new vehicle
     * @param vehicleName the name
     * @param quantity the quantity
     * @param battlepoints the battlepoints
     * @return Response
     */
    @POST
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateVehicle(
            @FormParam("vehicleName") String vehicleName,
            @FormParam("quantity") String quantity,
            @FormParam("battlepoints") String battlepoints
    ){
        int httpStatus = 200;
        Vehicle vehicle = DataHandler.readVehicleByName(vehicleName);
        if (vehicle != null){
            vehicle.setVehicleName(vehicleName);
            vehicle.setQuantity(Integer.parseInt(quantity));
            vehicle.setBattlepoints(Integer.parseInt(battlepoints));

            DataHandler.updateVehicle();
        }
        else{
            httpStatus = 404;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}
