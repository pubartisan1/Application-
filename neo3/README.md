# Néo 3

Première base fonctionnelle de Néo 3 : interface mobile/PWA, humanoïde animé léger, synthèse vocale, reconnaissance vocale quand le navigateur la fournit, cache hors ligne, modes Aujourd'hui, Vérité, Coran et Histoires.

## Principes
- Ne jamais présenter une hypothèse comme un fait.
- FAIT / INDICE / HYPOTHÈSE / RUMEUR / INCONNU.
- Pour le Coran : distinguer texte, traduction et interprétation ; ne jamais inventer de verset.
- Faible consommation de données et fonctionnement dégradé hors connexion.

## Prochaines couches
- flux Néo Radio et expiration automatique des actualités ;
- recherche multi-source ;
- Whisper.cpp local Android ;
- moteur LLM local ;
- voix locale ;
- avatar/lip-sync avancé ;
- emballage Android APK/AAB.

## Personnalité et évolution
Néo possède désormais une personnalité déclarative versionnée dans `neo-personality.json`. Ses moteurs sont interchangeables via `engines.js` : une technologie plus récente peut remplacer voix, STT, cerveau local, recherche ou avatar sans modifier ses principes. `freshness.js` centralise les durées de vie des informations et les niveaux de confiance.


Android build pipeline activated: 2026-10-05.

APK rebuild after SDK workflow fix.
