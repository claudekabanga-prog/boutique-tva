\# Boutique TVA



\## Scénario

Une petite boutique en ligne calcule les prix TTC, les remises et les totaux

de ses clients. Une erreur de calcul fait perdre de l'argent ou fausse les

factures : chaque changement doit être testé automatiquement et ne passer en

production qu'après approbation.



\## Adresses

\- / : version de l'application

\- /health : OK

\- /ttc/100/18 : 118.00

\- /remise/200/10 : 180.00

\- /total/25/4 : 100.00



\## Lancer en local

javac -d build/classes src/\*.java test/\*.java

java -cp build/classes PrixServer 8090



\## Docker Hub

https://hub.docker.com/r/ckabanga/boutique-tva

