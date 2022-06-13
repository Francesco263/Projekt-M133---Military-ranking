package ch.bzz.militaryranking.model;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;

public class Weapon {

    @FormParam("weaponID")
    private int weaponID;

    @FormParam("secureCode")
    @NotEmpty
    @Pattern(regexp = "^\\d{3}\\.[a-zA-Z]{4}\\.[a-zA-Z]{4}\\.\\d$")
    private String secureCode;

    @FormParam("weaponName")
    @NotEmpty
    @Size(min=2, max=40)
    private String weaponName;

    @FormParam("battlepoints")
    @NotNull
    @Min(1)
    @Max(100000)
    private int battlepoints;

    /**
     * sets weaponName
     * @param weaponName
     */
    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    /**
     * sets battle points
     * @param battlepoints
     */
    public void setBattlepoints(int battlepoints) {
        this.battlepoints = battlepoints;
    }

    /**
     * gets weapon name
     * @return weaponName
     */
    public String getWeaponName() {
        return weaponName;
    }

    /**
     * gets battle points
     * @return battlepoints
     */
    public int getBattlepoints() {
        return battlepoints;
    }

    /**
     * gets weaponID
     * @return
     */
    public int getWeaponID() {
        return weaponID;
    }

    /**
     * sets weaponID
     * @param weaponID
     */
    public void setWeaponID(int weaponID) {
        this.weaponID = weaponID;
    }

    /**
     * gets secure code
     * @return
     */
    public String getSecureCode() {
        return secureCode;
    }

    /**
     * sets secure code
     * @param secureCode
     */
    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }
}
