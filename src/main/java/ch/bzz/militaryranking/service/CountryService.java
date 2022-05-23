package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.Country;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        return Response
                .status(200)
                .entity(country)
                .build();
    }
}
