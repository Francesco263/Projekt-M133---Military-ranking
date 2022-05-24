package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.Vehicle;
import ch.bzz.militaryranking.model.Weapon;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
        List<Vehicle> vehicleList = DataHandler.getInstance().readAllVehicles();
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
        Vehicle vehicle = DataHandler.getInstance().readVehicleByName(fahrzeugName);
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
        List<Vehicle> vehicleList = DataHandler.getInstance().readAllVehicles();
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
}
