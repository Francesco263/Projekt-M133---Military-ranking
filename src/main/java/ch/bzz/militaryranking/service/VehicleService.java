package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.Vehicle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
}
