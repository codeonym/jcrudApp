# TP4: Gestion de la BD bd_m2i

Bienvenue dans l'application web de gestion de la base de données bd_m2i, dédiée à la gestion des étudiants du M2I (semestre 1).

## Instructions

1. **Configuration MySQL**
   - Si vous utilisez Wamp ou un package équivalent, assurez-vous d'affecter un mot de passe à l'utilisateur root de MySQL.

2. **Modèle MVC**
   - Respectez le modèle MVC pour organiser votre code de manière structurée.

3. **Droits des Utilisateurs**
   - Les personnes authentifiées auront les mêmes droits, notamment la consultation, l'ajout, la modification, etc.

4. **Sécurité**
   - Évitez la faille XSS et l'injection SQL pour assurer la sécurité de l'application.

5. **Entête Commun**
   - Les différentes pages doivent avoir le même entête, avec l'utilisation du logo de la faculté.

6. **Sessions**
   - Utilisez des sessions pour gérer l'authentification des utilisateurs.

## Description de la BD

- Créez une base de données `bd_m2i_s1` pour la gestion des étudiants du M2I (semestre 1).
- Créez un utilisateur `admin` (mot de passe : `master2022`) avec tous les privilèges sur cette BD.

## Caractéristiques des Étudiants

Un étudiant est caractérisé par :
- Nom
- Prénom
- CNE (unique)
- Date de naissance
- Adresse

Chaque étudiant est inscrit dans six modules et a une note dans chaque module (de 0 à 20). Une absence est notée par -1.

## Authentification

Pour utiliser l'application web, un étudiant doit posséder un login et un mot de passe.

## Fonctionnalités de l'Application

1. **Authentification**
   - L'application demande à l'utilisateur de saisir un login et un mot de passe. En cas d'informations incorrectes, un message d'erreur s'affiche.

2. **Gestion des Étudiants**
   - Consulter les informations d'un étudiant.
   - Ajouter un nouvel étudiant.
   - Supprimer un étudiant.
   - Modifier les informations d'un étudiant.

3. **Recherche d'Étudiant**
   - Ajoutez la possibilité de rechercher un étudiant particulier par nom ou CNE.

## Modélisation de la BD

Rédigez un rapport d'une ou deux pages expliquant la démarche de modélisation de la base de données.

---

**Note:** Assurez-vous de suivre ces instructions pour garantir le bon fonctionnement de l'application.
