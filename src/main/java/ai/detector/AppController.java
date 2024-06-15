package ai.detector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AppController {
    @FXML private ListView<Projet> listeProjets;
    @FXML private ListView<FichierProjet> listeFichiers;
    @FXML private TextField nomFichierField;
    @FXML private TextField cheminFichierField;
    @FXML private TextField typeFichierField;
    @FXML private TextField scoreIAField;

    private ObservableList<Projet> projets = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        listeProjets.setItems(projets);

        // Ajout de listeners pour projets et fichiers
        listeProjets.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                listeFichiers.setItems(FXCollections.observableArrayList(newValue.getFichiers()));
            }
        });

        listeFichiers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateFileDetails(newValue);
            }
        });
    }

    @FXML
    private void chargerDepuisDossier() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        if (selectedDirectory != null) {
            try {
                List<Projet> loadedProjects = DevoreurIO.chargerProjetsDepuisDossier(selectedDirectory.getAbsolutePath());
                projets.setAll(loadedProjects);
            } catch (IOException e) {
                showAlert("Erreur de chargement", "Impossible de charger les projets depuis le dossier.");
            }
        }
    }

    @FXML
    private void chargerDepuisCSV() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                List<Projet> loadedProjects = DevoreurIO.chargerProjetsDepuisCSV(selectedFile.getAbsolutePath());
                projets.setAll(loadedProjects);
            } catch (IOException e) {
                showAlert("Erreur de chargement", "Impossible de charger les projets depuis le fichier CSV.");
            }
        }
    }

    @FXML
    private void sauvegarderEnCSV() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                DevoreurIO.sauvegarderProjetsEnCSV(projets, selectedFile.getAbsolutePath());
            } catch (IOException e) {
                showAlert("Erreur de sauvegarde", "Impossible de sauvegarder les projets en CSV.");
            }
        }
    }

    @FXML
    private void ajouterProjet() {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);
            if (selectedDirectory != null) {
                try {
                    Projet newProjet = new Projet(selectedDirectory.getName());

                    Files.walk(Paths.get(selectedDirectory.getAbsolutePath()))
                            .filter(Files::isRegularFile)
                            .forEach(path -> {
                                try {
                                    String content = new String(Files.readAllBytes(path));
                                    FichierProjet fichier = new FichierProjet(path.getFileName().toString(), path.toString(), content);
                                    newProjet.addFichier(fichier);
                                } catch (IOException e) {
                                    System.err.println("Erreur lors de la lecture du fichier: " + e.getMessage());
                                }
                            });
                    projets.add(newProjet);
                } catch (IOException e) {
                    showAlert("Erreur de chargement", "Impossible de charger les fichiers du projet.");
                }
            }
        }

    @FXML
    private void supprimerProjet() {
        Projet selectedProjet = listeProjets.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            projets.remove(selectedProjet);
        }
    }

    @FXML
    private void reanalyserFichier() {
        FichierProjet selectedFichier = listeFichiers.getSelectionModel().getSelectedItem();
        if (selectedFichier != null) {
            selectedFichier.setScoreIA(Math.random()); // Simuler une réanalyse
            updateFileDetails(selectedFichier);
        }
    }

    private void updateFileDetails(FichierProjet fichier) {
        nomFichierField.setText(fichier.getNom());
        cheminFichierField.setText(fichier.getChemin());
        typeFichierField.setText(fichier.getType());
        scoreIAField.setText(String.format("%.2f", fichier.getScoreIA()));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
