# Détecteur IA Mark 2.0

## Informations Générales

**2023 - 2024**

## Procédure pour tester l'application

Suivez ces étapes pour tester l'application :

1. **Démarrer l'application :**
    - Ouvrez votre terminal.
    - Naviguez jusqu'au dossier contenant le projet.
    - Exécutez la commande suivante :
      ```bash
      java -jar DétecteurIA.jar
      ```
    - Vous devriez voir l'interface graphique s'ouvrir.


2. **Charger des projets :**
    - Utilisez le bouton 'Charger depuis un dossier' pour sélectionner un dossier contenant les differents projets à analyser.
    - Vous pouvez également charger des projets depuis un fichier CSV en utilisant le bouton 'Charger depuis CSV'.


3. **Interagir avec les projets :**
    - Sélectionnez un projet dans la liste pour voir ses fichiers.
    - Vous pouvez ajouter, supprimer des projets ou sauvegarder l'ensemble des projets en CSV.


4. **Analyser et réanalyser les fichiers :**
    - Sélectionnez un fichier et utilisez le bouton 'Réanalyser' afin recalculer le score IA si le score actuel ne cous convient pas.

## Procédure pour recompiler l'application

Pour recompiler le projet, suivez ces étapes :

1. Assurez-vous que JDK et Maven sont installés sur votre système.
2. Ouvrez votre terminal et naviguez jusqu'au dossier racine du projet.
3. Exécutez la commande suivante pour compiler le projet :
   ```bash
   mvn clean install

## Etat actuel du projet

L'application fonctionne correctement avec toutes les fonctionnalités attendues.

Je pense que j'aurais pu refactoriser davantage le code afin d'utiliser davantage de patterns vus en cours.

Pour concevoir le visuel de l'application, j'ai utilisé SceneBuilder.

Le code initial fourni par Dorel semble relativement bien organisé, même si, comme mentionné précédemment, peu de patterns de conception sont utilisés.

J'estime avoir passé environ 8 heures en tout sur ce projet.
