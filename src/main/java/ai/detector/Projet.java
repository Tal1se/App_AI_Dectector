package ai.detector;

import java.util.ArrayList;
import java.util.List;

public class Projet {
    private String nom;
    private String auteur;
    private List<FichierProjet> fichiers;

    public Projet(String nom) {
        this.nom = nom;
        this.auteur = nom;
        this.fichiers = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = (auteur != null && !auteur.isEmpty()) ? auteur : "Inconnu";
    }

    public List<FichierProjet> getFichiers() {
        return new ArrayList<>(fichiers);
    }

    public void addFichier(FichierProjet fichier) {
        if (fichier != null) {
            fichiers.add(fichier);
        }
    }

    public boolean removeFichier(FichierProjet fichier) {
        return fichiers.remove(fichier);
    }

    public String toString() {
        return nom;
    }
}
