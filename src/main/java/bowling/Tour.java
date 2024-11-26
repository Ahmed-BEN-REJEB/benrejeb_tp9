package bowling;

import java.util.ArrayList;

public class Tour {
    private ArrayList<Lancer> lancers;
    private boolean termine;
    private final boolean dernierTour;

    public Tour(boolean dernierTour) {
        this.lancers = new ArrayList<>();
        this.termine = false;
        this.dernierTour = dernierTour;
    }

    public void ajouterLancer(int nbQuillesAbattues) {
        lancers.add(new Lancer(nbQuillesAbattues));

        if (dernierTour) {
            if (lancers.size() == 3 || (lancers.size() == 2 && scoreSimple() < 10)) {
                termine = true;
            }
        } else {
            if (lancers.size() == 2 || estStrike()) {
                termine = true;
            }
        }
    }

    public boolean estStrike() {
        return lancers.size() == 1 && lancers.get(0).getNbQuillesAbattues() == 10;
    }

    public boolean estSpare() {
        return lancers.size() == 2 && this.scoreSimple() == 10;
    }

    public int scoreSimple(){
        int Somme= 0;
        for(Lancer l : lancers){
            Somme+= l.getNbQuillesAbattues();
        }
        return Somme;
    }

    public boolean estTermine() {
        return termine;
    }

    public ArrayList<Lancer> getLancers() {
        return lancers;
    }
}