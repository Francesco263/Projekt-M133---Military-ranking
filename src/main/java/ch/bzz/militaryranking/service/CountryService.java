package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.Country;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    public Response listCountry(){
        List<Country> countryList = DataHandler.readAllCountries();
        return Response
                .status(200)
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
            @QueryParam("countryID") String countryID
    ){
        Country country = DataHandler.readCountryByID(countryID);
        int httpStatus;
        if (country == null){
            httpStatus = 404;
        }
        else{
            httpStatus = 200;
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
            @QueryParam("sortBy") String sort
    ){
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

    /**
     * Inserts a new Country
     * @param country the country to be inserted
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertCountry(
            @Valid @BeanParam Country country
    ){
        DataHandler.insertCountry(country);
        return Response
                .status(200)
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
            @QueryParam("countryID") String countryID
    ){
        int httpStatus = 200;
        if (!DataHandler.deleteCountry(countryID)){
            httpStatus = 410;
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
            @Valid @BeanParam Country country
    ){
        int httpStatus = 200;
        Country oldCountry = DataHandler.readCountryByID(Integer.toString(country.getCountryID()));
        if (oldCountry != null){
            oldCountry.setName(country.getName());
            DataHandler.updateCountry();
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
