package ch.bzz.militaryranking.model;
import java.util.Vector;

public class Fahrzeug {

    private String fahrzeugName;
    private int anzahl;
    private int kampfpunkte;
    private Vector<Waffe> waffen;

    public Fahrzeug(String fahrzeugName, int anzahl, int kampfpunkte){
        this.fahrzeugName = fahrzeugName;
        this.anzahl = anzahl;
        this.kampfpunkte = kampfpunkte*anzahl;
    }

    public int getKampfpunkte(){
        return kampfpunkte;
    }

    public Waffe getWaffe(int index){
        if (index >= 0 && index < waffen.size()){
            return waffen.get(index);
        }
        return null;
    }

    public void addWaffe(Waffe waffe){
        waffen.add(waffe);
    }

    public void removeWaffe(int index){
        if (index >= 0 && index <= waffen.size()){
            waffen.remove(index);
        }
    }

    public String getFahrzeugName(){
        return fahrzeugName;
    }

    public int getAnzahl(){
        return anzahl;
    }


}
