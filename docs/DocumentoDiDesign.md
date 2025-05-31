# Documento di Design: Nonsense Generator

## domain model ##
![DomainModel.png](/images/DomainModel.png)

descrizione degli ogetti
---

## system sequence diagram ##
da completare(prova.puml)

---

## Internal sequence diagram ##
### GENERATE SENTENCE ###
![GenerateSentences.png](/images/GenerateSentences.png)

### ANALYZE SENTENCE ###
![AnalyzeStructure.png](/images/AnalyzeStructure.png)

### ADD TO DICTIONARY ###
![AddToDictionary.png](/images/AddToDictionary.png)

### SHOW HISTORY ###
![ShowHystory.png](/images/ShowHystory.png)

### TOXICITY CONTROLLER ###
![ToxicityController.png](/images/ToxicityController.png)

---

## Design class model diagram ##
![DesignClassModelDiagram.png](/images/DesignClassModelDiagram.png)

descrizione dei vari componenti:
-ControllerApplication
-Generator
-ApiHandler
-HistoryHandler
-Dictionary

---

## architectural pattern ##
Il progetto NONSENSE-GENERATOR ha come pattern architetturale MVC (Model-View-Controller) e viene applicato così:

**Model**: comprende le classi che rappresentano la logica di dominio e i dati, come Dictionary, Word, Verb, Template, ecc. Queste classi gestiscono la struttura delle parole, i template e le operazioni sui dati.
**View**: è costituita dai file HTML (ad esempio index.html, error.html) e dalle risorse statiche nella cartella resources/static/. Questi file definiscono l’interfaccia utente e presentano i risultati.
**Controller**: le classi nella cartella controller (come ControllerApplication, ApiHandler, HistoryHandler) gestiscono le richieste dell’utente, coordinano l’interazione tra Model e View e restituiscono le risposte appropriate.
In sintesi, il Controller riceve le richieste dalla View, utilizza il Model per elaborare i dati e aggiorna la View con i risultati, separando chiaramente la logica di presentazione da quella di business.

---

## Design pattern ##
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