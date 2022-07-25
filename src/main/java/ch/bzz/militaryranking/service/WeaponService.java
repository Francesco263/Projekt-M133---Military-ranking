package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.Weapon;
import javax.validation.Valid;
import javax.ws.rs.*;
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

    private static int cntr = DataHandler.getWeaponCount();
    /**
     * lists all weapons from weapons.json
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listWeapon(
            @CookieParam("userRole") String userRole
    ){
        int httpStatus = 200;
        List<Weapon> weaponList = null;

        if (DataHandler.authenticate(userRole, 2)){
            weaponList = DataHandler.readAllWeapons();
        }
        else{
            httpStatus = 403;
        }
        return Response
                .status(httpStatus)
                .entity(weaponList)
                .build();
    }

    /**
     * lists the corresponding weapon to given id
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readWeapon(
            @CookieParam("userRole") String userRole,
            @QueryParam("weaponID") String weaponID
    ){
        int httpStatus = 200;
        Weapon weapon = null;

        if (DataHandler.authenticate(userRole, 2)){
            weapon = DataHandler.readWeaponByID(weaponID);
            if (weapon == null){
                httpStatus = 404;
            }
        }
        else{
            httpStatus = 403;
        }

        return Response
                .status(httpStatus)
                .entity(weapon)
                .build();
    }

    /**
     * sorts the weapon list by battlePoints or weaponName
     */
    @GET
    @Path("sortList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortWeapon(
            @CookieParam("userRole") String userRole,
            @QueryParam("sortBy") String sort
    ){
        if (DataHandler.authenticate(userRole, 2)){
            List<Weapon> weaponList = DataHandler.readAllWeapons();
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
            if (sort.contains("weaponName") || sort.contains("battlepoints")){
                return Response
                        .status(200)
                        .entity(weaponList)
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
     * Inserts a new weapon
     * @param weapon the weapon
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertWeapon(
            @CookieParam("userRole") String userRole,
            @Valid @BeanParam Weapon weapon
    ){
        int httpStatus = 200;
        if (DataHandler.authenticate(userRole, 1)){
            weapon.setWeaponID(++cntr);
            DataHandler.insertWeapon(weapon);
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
     * deletes a weapon identified by its id
     * @param weaponID the id
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteWeapon(
            @CookieParam("userRole") String userRole,
            @QueryParam("weaponID") String weaponID
    ){
        int httpStatus = 200;
        if (DataHandler.authenticate(userRole, 1)){
            if (!DataHandler.deleteWeapon(weaponID)){
                httpStatus = 410;
            }
            DataHandler.updateVehicle();
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
     * updates a weapon
     * @param weapon the weapon
     * @return Response
     */
    @POST
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateWeapon(
            @CookieParam("userRole") String userRole,
            @Valid @BeanParam Weapon weapon
    ){
        int httpStatus = 200;
        if (DataHandler.authenticate(userRole, 1)){
            Weapon oldWeapon = DataHandler.readWeaponByID(Integer.toString(weapon.getWeaponID()));
            if (oldWeapon != null){
                oldWeapon.setSecureCode(weapon.getSecureCode());
                oldWeapon.setWeaponName(weapon.getWeaponName());
                oldWeapon.setBattlepoints(weapon.getBattlepoints());

                DataHandler.updateWeapon();
            }
            else{
                httpStatus = 404;
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

}
