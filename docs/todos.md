# TODOs:
- Vedere come gestire meglio la password. Sia sui check di constraint e validation, sia quando viene ritornato l'user tramite JSON.
- Permettere il create User del controller a tutti (ora permette solo se autenticati)
- Rendere il ritorno di una lista (ad esempio quella per la lista di Whispers di un utente) più descrittiva, ad esempio 
  {"resultSize": Long, "Whispers": [lista di Whispers]}