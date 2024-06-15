package ai.detector;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DevoreurIO {
    public static void sauvegarderProjetsEnCSV(List<Projet> projets, String chemin) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(chemin))) {
            // En-tête du CSV
            writer.write("Nom du Projet,Auteur du Projet,Nom du Fichier,Chemin du Fichier,Type du Fichier,Score IA\n");
            for (Projet projet : projets) {
                for (FichierProjet fichier : projet.getFichiers()) {
                    writer.write(String.format("%s,%s,%s,%s,%s,%.2f\n",
                            projet.getNom(),
                            projet.getAuteur(),
                            fichier.getNom(),
                            fichier.getChemin(),
                            fichier.getType(),
                            fichier.getScoreIA()));
                    }
            }
        }
    }

    public static List<Projet> chargerProjetsDepuisCSV(String chemin) throws IOException {
        Map<String, Projet> projetsMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(chemin))) {
            reader.readLine(); // Ignorer l'en-tête
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                String nomProjet = parts[0].trim();
                String auteurProjet = parts[1].trim();
                String nomFichier = parts[2].trim();
                String cheminFichier = parts[3].trim();
                String typeFichier = parts[4].trim();
                double scoreIA = Double.parseDouble(parts[5].trim());

                FichierProjet fichier = new FichierProjet(nomFichier, cheminFichier, "");
                fichier.setType(typeFichier);
                fichier.setScoreIA(scoreIA);

                Projet projet = projetsMap.computeIfAbsent(nomProjet, k -> new Projet(k));
                projet.setAuteur(auteurProjet);
                projet.addFichier(fichier);
            }
        }
        return new ArrayList<>(projetsMap.values());
    }

    public static List<Projet> chargerProjetsDepuisDossier(String dossierRacine) throws IOException {
        List<Projet> projets = new ArrayList<>();
        Files.list(Paths.get(dossierRacine)).filter(Files::isDirectory).forEach(dir -> {
            Projet projet = new Projet(dir.getFileName().toString());
            try {
                Files.walk(dir).filter(Files::isRegularFile).forEach(file -> {
                    try {
                        String content = new String(Files.readAllBytes(file));
                        FichierProjet fichierProjet = new FichierProjet(file.getFileName().toString(), file.toString(), content);
                        projet.addFichier(fichierProjet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            projets.add(projet);
        });
        return projets;
    }
}
