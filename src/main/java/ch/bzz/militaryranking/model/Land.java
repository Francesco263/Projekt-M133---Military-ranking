package ch.bzz.militaryranking.model;
import java.util.Vector;

public class Land {

    private String name;
    private int militaerstaerke;
    private Vector<Fahrzeug> fahrzeuge;

    public Land(String name){
        this.name = name;
    }
    public void berechneStaerke(){
        for (int i = 0; i < fahrzeuge.size(); i++){
            militaerstaerke = militaerstaerke + fahrzeuge.get(i).getKampfpunkte();
        }
    }
    public void addFahrzeug(Fahrzeug fahrzeug){
        fahrzeuge.add(fahrzeug);
    }
    public void removeFahrzeug(int index){
        if (index >= 0 && index <= fahrzeuge.size()){
            fahrzeuge.remove(index);
        }
    }
    public Fahrzeug getFahrzeug(int index){
        return fahrzeuge.get(index);
    }

}
