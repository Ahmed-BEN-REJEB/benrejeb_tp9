package bowling;

import java.util.ArrayList;

/**
 * Cette classe a pour but d'enregistrer le nombre de quilles abattues lors des
 * lancers successifs d'<b>un seul et même</b> joueur, et de calculer le score
 * final de ce joueur
 */
public class PartieMonoJoueur {

    //Définition des attributs nécessaires
    private ArrayList<Tour> tours;
    private int tourCourant;

	/**
	 * Constructeur
	 */
	public PartieMonoJoueur() {
        this.tours = new ArrayList<>();
        this.tours.add(new Tour(false));
        this.tourCourant = 1;
	}

	/**
	 * Cette méthode doit être appelée à chaque lancer de boule
	 *
	 * @param nombreDeQuillesAbattues le nombre de quilles abattues lors de ce lancer
	 * @throws IllegalStateException si la partie est terminée
	 * @return vrai si le joueur doit lancer à nouveau pour continuer son tour, faux sinon	
	 */
	public boolean enregistreLancer(int nombreDeQuillesAbattues) {
        if (estTerminee()) {
            throw new IllegalStateException("La partie est terminée.");
        }
    
        // Ajouter un nouveau tour si nécessaire
        if (tours.isEmpty() || tours.get(tourCourant - 1).estTermine()) {
            boolean dernierTour = (tourCourant == 10);
            tours.add(new Tour(dernierTour));
            tourCourant++;
        }        
    
        // Obtenir le tour actuel (après s'assurer qu'il existe)
        Tour tourActuel = tours.get(tourCourant - 1);
        tourActuel.ajouterLancer(nombreDeQuillesAbattues);

        if (tours.get(tourCourant-1).estStrike() || tours.get(tourCourant-1).estSpare())  {
            tourCourant++;
        }
    
        return !tourActuel.estTermine();
    }    

	/**
	 * Cette méthode donne le score du joueur.
	 * Si la partie n'est pas terminée, on considère que les lancers restants
	 * abattent 0 quille.
	 * @return Le score du joueur
	 */
	public int score() {
        int score = 0;

        for (int i = 0; i < tours.size(); i++) {
            Tour tour = tours.get(i);
            score += tour.scoreSimple();

            if (tour.estStrike() && i < 9) {
                score += bonusStrike(i);
            } else if (tour.estSpare() && i < 9) {
                score += bonusSpare(i);
            }
        }

        return score;
    }

	/**
	 * @return vrai si la partie est terminée pour ce joueur, faux sinon
	 */
	public boolean estTerminee() {
        if (!tours.isEmpty()) {
            return tourCourant == 10 && tours.get(9).estTermine();
        }
        return false;
    }


	/**
	 * @return Le numéro du tour courant [1..10], ou 0 si le jeu est fini
	 */
	public int numeroTourCourant() {
        return estTerminee() ? 0 : tourCourant;
    }

	/**
	 * @return Le numéro du prochain lancer pour tour courant [1..3], ou 0 si le jeu
	 *         est fini
	 */
	public int numeroProchainLancer() {
        if (estTerminee()) {
            return 0;
        } else if (tours.isEmpty() || tourCourant - 1 >= tours.size()) {
            return 1;
        } else {
            Tour tour = tours.get(tourCourant - 1);
            return tour.getLancers().size() + 1;
        }
    }    

    private int bonusStrike(int index) {
        int bonus = 0;
    
        if (index + 1 < tours.size()) {
            Tour prochainTour = tours.get(index + 1);
            bonus += prochainTour.getLancers().get(0).getNbQuillesAbattues();
    
            if (prochainTour.estStrike() && index + 2 < tours.size()) {
                bonus += tours.get(index + 2).getLancers().get(0).getNbQuillesAbattues();
            } else if (prochainTour.getLancers().size() > 1) {
                bonus += prochainTour.getLancers().get(1).getNbQuillesAbattues();
            }
        }
    
        return bonus;
    }
    
    private int bonusSpare(int index) {
        if (index + 1 < tours.size() && !tours.get(index + 1).getLancers().isEmpty()) {
            return tours.get(index + 1).getLancers().get(0).getNbQuillesAbattues();
        }
        return 0;
    }
    

}