# Documento di Design: Nonsense Generator

## domain model ##
da generare a codice ultimato

## system sequence diagram ##
da completare(prova.puml)

## Internal sequence diagram ##
tutti i file .puml

## Design class model diagram ##
da generare a codice ultimato

questo devo vedere se e' il file giusto dove metterli
## Design pattern(molto probabilmente ce ne sono altri)
1. Singleton
Dove: La classe ApiHandler implementa il pattern Singleton.

Come: Ha un campo statico privato private static ApiHandler INSTANCE; che contiene l’unica istanza della classe.
Il costruttore è privato, quindi non può essere chiamato dall’esterno.
Il metodo statico pubblico getInstance() controlla se l’istanza esiste già; se no, la crea e la restituisce. In questo modo, in tutta l’applicazione esisterà una sola istanza di ApiHandler.

Perché: Questo pattern viene usato per centralizzare la gestione delle chiamate alle API e la lettura della chiave API, evitando duplicazioni e garantendo coerenza.

2. Facade
Dove: L’intera classe ApiHandler funge da Facade.

Come: Espone metodi semplici come getSyntaxTree, getPartsOfText, getToxicityScore, ecc.
Nasconde la complessità delle chiamate alle API esterne (Google Syntax API, Toxicity API), la gestione delle chiavi, la costruzione delle richieste e il parsing delle risposte JSON.
Gli altri componenti dell’applicazione possono così interagire con le API tramite un’interfaccia semplice e coerente, senza preoccuparsi dei dettagli implementativi.

Perché:Il pattern Facade semplifica l’utilizzo di sistemi complessi, fornendo un unico punto di accesso e nascondendo la logica interna.

3. Adapter
Dove: Nei metodi che convertono le risposte delle API in strutture dati Java.

Come: Metodi come getElementsOfText, getElementsOfTextLemma, getToxicityScore e getToxicityScoreList ricevono risposte JSON dalle API esterne.
Questi metodi trasformano (adattano) i dati JSON in oggetti e strutture dati Java (ArrayList<String[]>, List<Pair<String, String>>, Pair<String, Integer>, ecc.) che sono più facili da usare nel resto dell’applicazione.

Perché: Il pattern Adapter viene usato per rendere compatibili formati di dati diversi, permettendo all’applicazione di lavorare con dati provenienti da fonti esterne senza dover gestire direttamente il formato originale.