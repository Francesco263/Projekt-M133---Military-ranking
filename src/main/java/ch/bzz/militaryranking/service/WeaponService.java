package ch.bzz.militaryranking.service;

import ch.bzz.militaryranking.data.DataHandler;
import ch.bzz.militaryranking.model.Weapon;
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


    /**
     * lists all weapons from weapons.json
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listWeapon(){
        List<Weapon> weaponList = DataHandler.readAllWeapons();
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
        Weapon weapon = DataHandler.readWeaponByName(weaponName);
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

    /**
     * Inserts a new weapon
     * @param weaponName the name
     * @param battlepoints the battlepoints
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertWeapon(
            @FormParam("weaponName") String weaponName,
            @FormParam("battlepoints") String battlepoints
    ){
        Weapon weapon = new Weapon();
        weapon.setWeaponName(weaponName);
        weapon.setBattlepoints(Integer.parseInt(battlepoints));

        DataHandler.insertWeapon(weapon);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * deletes a weapon identified by its name
     * @param weaponName the name
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteWeapon(
            @QueryParam("weaponName") String weaponName
    ){
        int httpStatus = 200;
        if (!DataHandler.deleteWeapon(weaponName)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * updates a weapon
     * @param weaponName the name
     * @param battlepoints the battlepoints
     * @return Response
     */
    @POST
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateWeapon(
            @FormParam("weaponName") String weaponName,
            @FormParam("battlepoints") String battlepoints
    ){
        int httpStatus = 200;
        Weapon weapon = DataHandler.readWeaponByName(weaponName);
        if (weapon != null){
            weapon.setWeaponName(weaponName);
            weapon.setBattlepoints(Integer.parseInt(battlepoints));

            DataHandler.updateWeapon();
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
