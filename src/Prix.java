public class Prix {

    // Prix TTC = prix HT + TVA (taux en pourcentage)
    public static double ttc(double ht, double tauxTva) {
        if (ht < 0 || tauxTva < 0) {
            throw new IllegalArgumentException("valeurs negatives interdites");
        }
        return arrondir(ht * (1 + tauxTva / 100.0));
    }

    // Prix apres remise (pourcentage entre 0 et 100)
    public static double remise(double prix, double pourcentage) {
        if (prix < 0) {
            throw new IllegalArgumentException("prix negatif interdit");
        }
        if (pourcentage < 0 || pourcentage > 100) {
            throw new IllegalArgumentException("remise entre 0 et 100");
        }
        return arrondir(prix * (1 - pourcentage / 100.0));
    }

    // Total d'un panier : somme des prix unitaires x quantites
    public static double total(double prixUnitaire, int quantite) {
        if (prixUnitaire < 0 || quantite < 0) {
            throw new IllegalArgumentException("valeurs negatives interdites");
        }
        return arrondir(prixUnitaire * quantite);
    }

    // Arrondi a 2 decimales
    private static double arrondir(double valeur) {
        return Math.round(valeur * 100.0) / 100.0;
    }
}