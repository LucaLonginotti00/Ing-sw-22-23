# Peer-Review 2: Protocollo di Comunicazione

Filippo Fini, Matteo Fiorentino, Luca Longinotti

Gruppo 52

Valutazione della documentazione del protocollo di comunicazione del gruppo 51.



## Lati positivi

Il protocollo è chiaro, dettagliato e, in generale, completo.
Tutti i messaggi sono stati descritti in maniera esaustiva, specificando anche i valori passati come parametri.

### Start game:
- Molta chiarezza nelle operazioni usate per creare un nuovo gioco.
- Utilizzo di più client nel diagramma.

### Action phase:
- È stato molto apprezzato il modo in cui è stato descritto lo spostamento degli studenti nella dining room e nelle isole.



## Lati negativi

### Start game:
- Il server crea un nuovo game senza conoscere l'effettivo numero di giocatori e quindi non può sapere come inizializzare i nuovi oggetti.
- Il client invia informazioni al server (GameInfo(expert)) senza che il server le abbia richieste. Questo non è corretto da un punto di vista logico in quanto in qualsiasi gioco è il server che chiede le informazioni di cui ha bisogno. In caso contrario il client non saprebbe quali informazioni inviare.
- Non è specificata la procedura da seguire in caso di informazioni errate, come per esempio un nickname già utilizzato.

### Avalaible actions:
- Le azioni in un turno sono predefinite e da eseguire in un ordine ben preciso, quindi non è necessario farle scegliere al giocatore da una lista. 
- Invece di richiedere al giocatore di scegliere un'azione da una lista sarebbe meglio richiedere in ordine, con messaggi separati e specifici, tutte le azioni da eseguire. Questo potrebbe essere vantaggioso durante l'implementazione in modo da non creare una "mega classe" con tutte le varie opzioni di messaggio.

### Planning phase:
- Sarebbe meglio specificare la parte in cui il server aggiunge studenti alle tessere nuvola.

### Action phase:
- Come nella planning phase sarebbe opportuno mostrare le operazioni che il server esegue, tra cui il calcolo dell'influenza, il merge delle isole e la relativa notifica al client.

### End game:
- Non sono presenti le operazioni del server relative alla chuisura del gioco e delle connessioni.

### Character card:
- Mancano le operazioni del server in cui viene applicato l'effetto della carta scelta.



## Confronto

- Sono presenti i sequence diagrams di pause game e character card che possono esere una buona aggiunta al nostro protocollo.

- Un'altra differenza è la loro scelta di non modellare l'heartbeat che invece potrebbe essere introdotto.

- Tutti i messaggi inviati e ricevuti specificano parametri. Questa sarà un'altra aggiunta al nostro protocollo.

- A differenza nostra è stato scelto di non specificare le operazioni svolte dal server che, a nostro parere, sono un'importante aggiunta per la completezza del sequence diagram.
