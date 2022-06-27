package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.Country;
import ch.bzz.militaryranking.model.Vehicle;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

/**
 * country service
 */
@Path("country")
public class CountryService {

    private static int cntr = DataHandler.getCountryCount();
    /**
     * lists all countries from countries.json
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCountry(
            @CookieParam("userRole") String userRole
    ){
        List<Country> countryList = null;
        int httpStatus;
        if (DataHandler.authenticate(userRole, 2)){
            httpStatus = 200;
            countryList = DataHandler.readAllCountries();
        }
        else{
            httpStatus =  403;
        }
        return Response
                .status(httpStatus)
                .entity(countryList)
                .build();
    }

    /**
     * lists the corresponding country to given id
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readCountry(
            @CookieParam("userRole") String userRole,
            @QueryParam("countryID") String countryID
    ){
        int httpStatus;
        Country country = new Country();
        if (DataHandler.authenticate(userRole, 2)){
            httpStatus = 200;
             country = DataHandler.readCountryByID(countryID);
            if (country == null){
                httpStatus = 404;
            }
        }
        else{
            httpStatus =  403;
        }

        return Response
                .status(httpStatus)
                .entity(country)
                .build();
    }

    /**
     * sorts the country list by name or militaryPower
     */
    @GET
    @Path("sortList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortWeapon(
            @CookieParam("userRole") String userRole,
            @QueryParam("sortBy") String sort
    ){
        if (DataHandler.authenticate(userRole, 2)){
            List<Country> countryList = DataHandler.readAllCountries();
            if(sort != null && sort.equals("name")){
                Collections.sort(countryList, new Comparator<Country>() {
                    @Override
                    public int compare(Country country, Country t1) {
                        return country.getName().compareTo(t1.getName());
                    }
                });
            }
            else if (sort!= null && sort.equals("militaryPower")){
                Collections.sort(countryList, new Comparator<Country>() {
                    @Override
                    public int compare(Country country, Country t1) {
                        return country.getMilitaryPower()-(t1.getMilitaryPower());
                    }
                });
            }
            if (sort.contains("militaryPower") || sort.contains("name")){
                return Response
                        .status(200)
                        .entity(countryList)
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
     * Inserts a new Country
     * @param country the country to be inserted
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertCountry(
            @CookieParam("userRole") String userRole,
            @Valid @BeanParam Country country
    ){
        int httpStatus = 200;
        if (DataHandler.authenticate(userRole, 1)){
            country.setCountryID(++cntr);
            if (getVehiclesFromID(country) != null){
                country.setVehicles(getVehiclesFromID(country));
                DataHandler.insertCountry(country);
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
     * deletes a country identified by its id
     * @param countryID the key
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteCountry(
            @CookieParam("userRole") String userRole,
            @QueryParam("countryID") String countryID
    ){
        int httpStatus = 200;
        if (DataHandler.authenticate(userRole, 1)){
            if (!DataHandler.deleteCountry(countryID)){
                httpStatus = 410;
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
     * updates a country
     * @param country the id
     * @return Response
     */
    @POST
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateCountry(
            @CookieParam("userRole") String userRole,
            @Valid @BeanParam Country country
    ){
        int httpStatus = 200;
        if (DataHandler.authenticate(userRole, 1)){
            Country oldCountry = DataHandler.readCountryByID(Integer.toString(country.getCountryID()));
            if (oldCountry != null && getVehiclesFromID(country) != null){
                oldCountry.setName(country.getName());
                oldCountry.setVehicles(getVehiclesFromID(country));
                DataHandler.updateCountry();
            }
            else if (oldCountry == null){
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
     * returns country list from countryID
     * @param country
     * @return
     */
    public Vector<Vehicle> getVehiclesFromID(Country country){
        String[] vehicleIDs = country.getVehicleIDs().split("\\s+");
        Vector<Vehicle> vehicles = new Vector<Vehicle>();
        for (int i = 0; i < vehicleIDs.length; i++){
            if (DataHandler.readVehicleByID(vehicleIDs[i]) == null){
                return null;
            }
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleID(Integer.parseInt(vehicleIDs[i]));
            vehicles.add(vehicle);
        }
        return vehicles;
    }
}

