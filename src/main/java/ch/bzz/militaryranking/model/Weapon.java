package ch.bzz.militaryranking.model;
public class Weapon {

    private String weaponName;
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
}
