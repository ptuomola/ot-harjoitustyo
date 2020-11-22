# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria, ja koodin pakkausrakenne on seuraava:

<img src="packagediagram.png" width="160">

Pakkauksien sisältö on seuraavaa:

- _org.tuomola.flightlogbook.ui_ sisältää käyttöliittymän - tällä hetkellä toteutettuna tekstikäyttöliittymänä, myöhemmin JavaFX:llä
- _org.tuomola.flightlogbook.service_ sisältää sovelluslogiikan
- _org.tuomola.flightlogbook.domain_ sisältää domain-tietomallin POJO:ina
- _org.tuomola.flightlogbook.dao_ sisältää tietojen pysyväistallenuksesta vastaavan koodin (toteutettu JPA:lla)

