package bowling;


public class Lancer {

    //Définition des attributs nécessaires
    //Définition d'un attribut "nbBoulesAAbbatre" définissant le nb. de boules abbatues chaque lancer
    private int nbQuillesAbattues;

    Lancer(int nbQuillesAbattues){
        this.nbQuillesAbattues = nbQuillesAbattues;
    }

    //Définition des Getters
    public int getNbQuillesAbattues(){
        return this.nbQuillesAbattues;
    }

}