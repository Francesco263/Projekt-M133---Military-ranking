package ch.bzz.militaryranking.model;
public class Waffe {
    private String waffenName;
    private int kampfpunkte;

    public Waffe(String waffenName, int kampfpunkte){
        this.waffenName = waffenName;
        this.kampfpunkte = kampfpunkte;
    }

    public String getWaffenName() {
        return waffenName;
    }

    public int getKampfpunkte() {
        return kampfpunkte;
    }
}
