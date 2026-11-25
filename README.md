# Boutique TVA

![Java](https://img.shields.io/badge/Java-21-orange)
![Docker Hub](https://img.shields.io/badge/Docker%20Hub-ckabanga%2Fboutique--tva-blue)

Calculateur de prix pour une petite boutique en ligne : prix TTC, remises et totaux.
Projet final CI/CD avec Jenkins et Docker (Horizon School of Digital Technologies).

## Scénario

Une petite boutique en ligne calcule les prix TTC, les remises et les totaux de ses clients.
Une erreur de calcul fait perdre de l'argent ou fausse les factures.
Chaque changement doit donc être testé automatiquement,
puis ne passer en production qu'après approbation.

## Avancement par semaine

**Où en sommes-nous : semaine 3 (pipeline CI).**

### Semaine 1 : idée et application

- [x] Application Java : calculs TTC, remise, total
- [x] Serveur HTTP avec `/health` qui répond `OK`
- [x] Dépôt GitHub public, commits à mon nom
- [x] README avec le scénario
- [x] Première pull request fusionnée
- [ ] Binôme à ajouter en collaborateur dès sa désignation (l'enseignant autorise à travailler seul en attendant)
- [ ] Idée et scénario validés par l'enseignant

### Semaine 2 : tests et image

- [x] 7 tests unitaires qui passent (`PrixTest`)
- [x] 5 tests HTTP qui passent (`ServerTest`)
- [x] `docker build` et `docker run` à la main

### Semaine 3 : pipeline CI (fonctionnalités 1 à 8)

- [ ] Jenkinsfile dans Git, job « Pipeline script from SCM »
- [ ] Build
- [ ] Tests en parallèle (unitaires et HTTP)
- [ ] Rapports JUnit
- [ ] Paramètres `DEPLOY_ENV` et un second paramètre
- [ ] Artefacts avec fingerprint
- [ ] Image Docker `1.0.N`, seulement si le build est vert
- [ ] Push sur Docker Hub

### Semaine 4 : livraison continue (fonctionnalités 9 à 12)

- [ ] Staging sur le port 8081 avec smoke test
- [ ] Production sur le port 8082 après approbation
- [ ] E-mail de succès et d'échec (Mailpit)
- [ ] `cleanWs()` en fin de run
- [ ] Bonus : scan Trivy, rollback, HEALTHCHECK, pollSCM, timeouts
- [ ] Rapport `report.pdf`, captures d'écran, démo de 5 minutes

## Adresses de l'application

| Adresse | Réponse | Exemple |
|---|---|---|
| `/` | Version de l'application | `Boutique TVA 1.0.1 is running` |
| `/health` | État du service | `OK` |
| `/ttc/{ht}/{taux}` | Prix TTC | `/ttc/100/18` renvoie `118.00` |
| `/remise/{prix}/{pourcent}` | Prix après remise | `/remise/200/10` renvoie `180.00` |
| `/total/{prix}/{quantite}` | Total de la commande | `/total/25/4` renvoie `100.00` |

Valeur invalide : `400`. Adresse inconnue : `404`.

## Lancer en local

Prérequis : JDK 21.

```
javac -d build/classes src/*.java test/*.java
java -cp build/classes PrixServer 8090
```

Puis ouvrir http://localhost:8090/health

## Lancer les tests

```
java -cp build/classes PrixTest
java -cp build/classes ServerTest
```

- `PrixTest` : 7 tests unitaires (logique de calcul)
- `ServerTest` : 5 tests HTTP (serveur sur le port 18090)

Les rapports JUnit XML sont écrits dans `build/test-results/`.

## Docker

Image publique : [ckabanga/boutique-tva](https://hub.docker.com/r/ckabanga/boutique-tva)

```
docker run -d -p 8081:8080 -e APP_VERSION=1.0.1 ckabanga/boutique-tva:1.0.1
```

Les images sont taguées `1.0.N` (N = numéro de build Jenkins), jamais `latest`.

## Environnements

| Environnement | Port |
|---|---|
| Staging | 8081 |
| Production | 8082 |

## Pipeline Jenkins

Le `Jenkinsfile` à la racine est lu depuis Git (Pipeline script from SCM).

Build, tests en parallèle (unitaires et HTTP), rapports JUnit, artefacts avec fingerprint,
image Docker, push Docker Hub, staging avec smoke test, approbation, production,
e-mail de notification, nettoyage de l'espace de travail.

## Structure du projet

```
boutique-tva/
├── src/
│   ├── Prix.java          logique de calcul
│   └── PrixServer.java    serveur HTTP
├── test/
│   ├── PrixTest.java      tests unitaires
│   └── ServerTest.java    tests HTTP
├── Dockerfile
├── Jenkinsfile
└── README.md
```

## Équipe

| Personne | Compte GitHub | Rôle |
|---|---|---|
| Claude Kabanga | [claudekabanga-prog](https://github.com/claudekabanga-prog) | Application, tests, pipeline |
| À définir | À définir | À définir |

## Sources

Code, tests, Dockerfile et Jenkinsfile écrits pour ce projet.
Aucun code externe réutilisé.
