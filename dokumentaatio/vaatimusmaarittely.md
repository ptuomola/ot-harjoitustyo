# Vaatimusmäärittely

## Sovelluksen tarkoitus

Yksityislentäjillä on lakisääteinen vaatimus pitää lennoistaan lokikirjaa, johon pitää merkitä jokaisesta lennosta tietyt tiedot - mm. nousujen/laskujen määrä, lähtö/saapumisajat, lentoonlähtö/laskeutumisajat yms. Tarvittavien kellonaikojen yms. merkitseminen muistiin lennon aikana on hankalaa, varsinkin kun ne usein sattuvat juuri niihin lennon vaiheisiin, jolloin on paljon muutakin tärkeää tekemistä. 

Tämän sovelluksen tarkoitus onkin tarjota yksinkertainen käyttöliittymä lentäjälle kellonaikojen ja muiden tärkeiden lentotietojen muistiin tallentamiseen lennon aikana, ja muodostaa tarvittavat lokikirjan rivit näiden talletettujen tietojen perusteella. 

Sovellus laskee myös talletettujen lokikirjan tietojen perusteella yksinkertaisia yhteenvetotietoja lentäjän tiedoksi, mm. kokonaislentoaika, laskeutumisien yhteismäärä yms.

## Käyttäjät

Sovelluksella on vain yksi käyttäjärooli, eli _normaali käyttäjä_. Jokaista käyttäjää varten luodaan oma lokikirja, johon talletetaan tämän käyttäjän lentotiedot. Tiedot lentokoneista ja kentistä ovat jaettuja käyttäjien välillä - toisin sanoen yhden käyttäjän lisäämät koneet/kentät näkyvät kaikille käyttäjille.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

- *TEHTY* Käyttäjä voi luoda järjestelmään käyttäjätunnuksen
    - *TEHTY* Käyttäjätunnuksen täytyy olla uniikki ja pituudeltaan vähintään 3 merkkiä
    - *TEHTY* Käyttäjätunnuksen luodessaan käyttäjä voi myös antaa tärkeimmät henkilötiedot lokikirjan luomista varten (syntymäaika, koko nimi, email)

- *TEHTY* Käyttäjä voi kirjautua järjestelmään
    - *TEHTY* Kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus kirjautumislomakkeelle
    - *TEHTY* Jos käyttäjää ei ole olemassa, ilmoittaa järjestelmä tästä

### Kirjautumisen jälkeen

- *TEHTY* Käyttäjä näkee yhteenvedon tähänastisista lennoistaan, mukaanlukien:
    - *TEHTY* Lentojen kokonaismäärä
    - *TEHTY* Lentojen kokonaisaika
    - *TEHTY* Nousujen / laskeutumisien määrä viime 90 päivän aikana, ja varoitus mikäli vähemmän kuin kuin 3 kpl (matkustajia saa kuljettaa vain mikäli vähintään 3)
    - *TEHTY* Lentotuntien määrä viime 12 kk aikana - ja varoitus mikäli vähemmän kun 12 tuntia (luokkakelpoisuuden uusimiseen tarvitaan vähintään 12 tuntia viimeisen 12 kk aikana)

- Käyttäjä voi myös katsoa yhteenvetotietoja:
    - Lentokentistä, joissa hän on käynyt, viimeinen käyntipäivä ja nousujen/laskeutumisien määrä per kenttä
    - Lentokoneista, joilla hän on lentänyt: viimeinen lentopäivä ja lentotuntien määrä per kone

- *TEHTY* Käyttäjä voi siirtyä uuden lennon tallennustilaan

### Uuden lennon tallennustilassa

- *TEHTY* Käyttäjä voi syöttää tiedot lentokoneesta, lähtökentästä, saapumiskentästä ja lennon tyypistä. Mikäli lentokonetta tai kenttiä ei ole sovelluksen tietokannassa, ne luodaan

- *TEHTY* Tietoja lentokoneesta, lähtökentästä, saapumiskentästä ja lennon tyypistä voidaan korjata lennon aikana kirjoittamalla uuden kentän tiedot

- *TEHTY* Käyttäjä voi yhtä nappia painamalla aloittaa lennon, jolloin lähtöaika talletetaan muistiin ja matkustusajan lasku alkaa

- *TEHTY* Tämän jälkeen käyttäjä voi yhtä nappia painamalla merkitä lentoonlähdön, jolloin lentoonlähtöaika talletetaan muistiin ja lentoajan lasku alkaa

- *TEHTY* Tämän jälkeen käyttäjä voi merkitä yhtä nappia painamalla läpilaskun, jolloin lentoonlähtöjen ja nousujen määrää kasvatetaan yhdellä

- *TEHTY* Tämän jälkeen käyttäjä voi merkitä yhtä nappia painamalla laskeutumisen, jolloin lentoajan laskeminen päätetään ja laskeutumisaika talletetaan muistiin

- *TEHTY* Tämän jälkeen käyttäjä voi merkitä yhtä nappia painamalla saapumisajan, jolloin matkustusajan laskeminen päätetään ja saapumisaika talletetaan muistiin. Tämän jälkeen sovellus palaa alkunäkymään (sama kuin kirjautumisen jälkeen).

## Jatkokehitysideoita

- Lisätietojen tallennus lentokentistä - esim. koordinaatit, palvelut, nettisivujen osoite, radiotaajuudet

- Lisätietojen tallennus lentokoneista - esim. tyyppi, tarvittava kelpuutustyyppi, tärkeimmät tiedot (lentonopeus, paino, kantama)

- Lentolupien ja kelpoisuuksien tietojen talletus: tuokka/tyyppihyväksyntöjen tyypit ja viimeiset voimassaolopäivät, varoitus kun pitää uusia yms.

- Mahdollisuus erilaisten ohjaajatyyppien lentoaikojen laskemiseen erikseen (single pilot / multi-pilot ja PIC / Co-Pilot / Dual / FI)

- Mahdollisuus simulaattorilentojen tallentamiseen

- Mahdollisuus erilaisten lentotilojen lentoaikojen laskemiseen erikseen (yö / IFR)

