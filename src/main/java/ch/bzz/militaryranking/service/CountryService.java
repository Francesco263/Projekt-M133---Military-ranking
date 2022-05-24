package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.Country;
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
 * country service
 */
@Path("country")
public class CountryService {

    /**
     * lists all countries from countries.json
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listCountry(){
        List<Country> countryList = DataHandler.getInstance().readAllCountries();
        return Response
                .status(200)
                .entity(countryList)
                .build();
    }

    /**
     * lists the corresponding country to given name
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readCountry(
            @QueryParam("name") String name
    ){
        Country country = DataHandler.getInstance().readCountryByName(name);
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
        List<Country> countryList = DataHandler.getInstance().readAllCountries();
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
        return Response
                .status(200)
                .entity(countryList)
                .build();
    }
}
