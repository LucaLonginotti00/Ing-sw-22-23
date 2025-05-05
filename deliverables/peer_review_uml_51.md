# Peer-Review 1: UML

Filippo Fini, Matteo Fiorentino, Luca Longinotti

Gruppo 52

Valutazione del diagramma UML delle classi del gruppo 51.

## Lati positivi

Nell'UML sono presenti la maggior parte delle classi base e la struttura generale è quasi delineata del tutto.
Le principali idee positive riguardano la gestione della classe SchoolBoard, l'attenzione ai dettagli e la creazione della classe IslandGroup per la gestione delle isole e del loro comportamento.
Un ulteriore aspetto da sottolineare è il buon utilizzo delle strutture logiche di progettazione, in primis le mappe per usufruire delle enumerazioni in liste e array.

## Lati negativi

L'UML presentato risulta essere incompleto, poiché in alcune classi non sono presenti attributi e metodi fondamentali per lo svolgimento del gioco, come il calcolo dell'influenza e l'unione delle isole.

Problemi logici:
- Dal momento che esiste la classe Professors, andrebbe creata anche la classe Students in quanto la loro gestione è molto simile.
- L'attributo deck dovrebbe stare nella classe Player siccome le carte assistente sono tenute in mano dal giocatore.
- L'attributo discardDeck non dovrebbe far parte della classe SchoolBoard, ma bensì della classe Tabletop.
- La classe MotherNature, essendo vuota, risulta essere superflua; essa potrebbe essere gestita con attributi e metodi all'interno di un'altra classe.
- Le carte personaggio non possono essere raggruppate solamente per il loro costo, ma bisogna tener conto dei 12 effetti diversi che non sono presenti nello schema. 
- Per quanto riguarda i collegamenti tra le classi, il verso delle frecce tra le classi MotherNature e IslandGroup e tra Professor e SchoolBoard deve essere invertito.
- Inoltre consigliamo di revisionare gli indici dei collegamenti che indicano la molteplicità delle classi, in particolare quelli relativi alle isole che non risultano essere in linea con le regole del gioco.
- Il tipo dell'attributo tower nella classe Island è errato.
- L'attributo general_reservoir non differenzia gli studenti tra di loro; questo rende impossibile sapere quanti ne restano per ogni colore.
- L'attributo coins e il metodo addCoins devono essere inseriti nella stessa classe.

Problemi grafico:
- Mancano le enumerazioni utilizzate nelle classi.
- Non è specificato il tipo ritornato dai metodi.



## Confronto tra le architetture

Il principale punto di forza di questo UML rispetto al nostro è la gestione delle isole tramite un "arcipelago".
L'aspetto che sicuramente implementeremo è la gestione del discardDeck.
