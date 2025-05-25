# Projecte DAM
Aplicació desenvolupada per Fabri per a l'assignatura de Aplicacions per a dispositius mòbils

## Versions utilitzades:

Java: 17
per poder utilitzar una versio moderna i compatible amb Android Studio
SDK: API 29
Perque aixi hi haura compatibilitat amb molts dispositius i que aixi el desenvolupament sigui mes facil i estable
Android Studio versio 2022.3.1 Patch 3 perque es la versio que tenia instal·lada al portatil

## Dependencies utilitzades:

androidx.lifecycle:lifecycle-viewmodel
androidx.lifecycle:lifecycle-livedata
androidx.room:room-runtime
androidx.navigation:navigation-fragment
androidx.navigation:navigation-ui
androidx.recyclerview:recyclerview
androidx.cardview:cardview
com.google.android.material:material

## Estructura:

Aquesta aplicacio l'he estructurat mitjançant MVVM amb Viewmodel i livedata. Gracies a aixo el manteninment del codi, gestio i modularitat son molt mes facils de mantenir. 
Tambe he implementat Room per poder emmagatzemar les partide que han sigut jugades.
A MainActivity estan situats la majoria de Fragment gestionats mitjançant Navigation Component que m'ha permet una navegacio molt mes senzilla entre pantalles.

## Explicacio de la app

He implementat una splash screen amb logotip i missatge inicial de benvingut al joc un cop entres a la aplicacio.
Pantalla de joc amb compte enrere, les eleccions, el resultat un marcador amb punts del jugador i la ia
Dues estrategies una aleatoria i una basada en exploracio
Pantalla principal amb el historial de partides
Pantalla de credits amb dades del creador i una d'informacio amb una petita explicacio del joc i les regles del joc
