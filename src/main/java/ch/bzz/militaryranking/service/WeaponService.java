package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
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
 * weapon service
 */
@Path("weapon")
public class WeaponService {


    /**
     * lists all weapons from weapons.json
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listWeapon(){
        List<Weapon> weaponList = DataHandler.getInstance().readAllWeapons();
        return Response
                .status(200)
                .entity(weaponList)
                .build();
    }

    /**
     * lists the corresponding weapon to given name
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readWeapon(
            @QueryParam("weaponName") String weaponName
    ){
        Weapon weapon = DataHandler.getInstance().readWeaponByName(weaponName);
        int httpStatus;

        if (weapon == null){
            httpStatus = 404;
        }
        else{
            httpStatus = 200;
        }
        return Response
                .status(httpStatus)
                .entity(weapon)
                .build();
    }

    /**
     * sorts the weapon list by battlepoints or weaponName
     */
    @GET
    @Path("sortList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortWeapon(
            @QueryParam("sortBy") String sort
    ){
        List<Weapon> weaponList = DataHandler.getInstance().readAllWeapons();
        if(sort != null && sort.equals("battlepoints")){
            Collections.sort(weaponList, new Comparator<Weapon>() {
                @Override
                public int compare(Weapon weapon, Weapon t1) {
                    return weapon.getBattlepoints()-(t1.getBattlepoints());
                }
            });
        }
        else if (sort!= null && sort.equals("weaponName")){
            Collections.sort(weaponList, new Comparator<Weapon>() {
                @Override
                public int compare(Weapon weapon, Weapon t1) {
                    return weapon.getWeaponName().compareTo(t1.getWeaponName());
                }
            });
        }
        return Response
                .status(200)
                .entity(weaponList)
                .build();
    }

}
