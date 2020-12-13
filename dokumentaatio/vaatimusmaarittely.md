# Vaatimusmäärittely

## Sovelluksen tarkoitus

Yksityislentäjillä on lakisääteinen vaatimus pitää lennoistaan lokikirjaa, johon pitää merkitä jokaisesta lennosta tietyt tiedot - mm. nousujen/laskujen määrä, lähtö/saapumisajat, lentoonlähtö/laskeutumisajat yms. Tarvittavien kellonaikojen yms. merkitseminen muistiin lennon aikana on hankalaa, varsinkin kun ne usein sattuvat juuri niihin lennon vaiheisiin, jolloin on paljon muutakin tärkeää tekemistä. 

Tämän sovelluksen tarkoitus onkin tarjota yksinkertainen käyttöliittymä lentäjälle kellonaikojen ja muiden tärkeiden lentotietojen muistiin tallentamiseen lennon aikana, ja muodostaa tarvittavat lokikirjan rivit näiden talletettujen tietojen perusteella. 

Sovellus laskee myös talletettujen lokikirjan tietojen perusteella yksinkertaisia yhteenvetotietoja lentäjän tiedoksi, mm. kokonaislentoaika, laskeutumisien yhteismäärä yms.

## Käyttäjät

Sovelluksella on vain yksi käyttäjärooli, eli _normaali käyttäjä_. Jokaista käyttäjää varten luodaan oma lokikirja, johon talletetaan tämän käyttäjän lentotiedot. Tiedot lentokoneista ja kentistä ovat jaettuja käyttäjien välillä - toisin sanoen yhden käyttäjän lisäämät koneet/kentät näkyvät kaikille käyttäjille.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

- Käyttäjä voi luoda järjestelmään käyttäjätunnuksen
    - Käyttäjätunnuksen täytyy olla uniikki ja pituudeltaan vähintään 3 merkkiä
    - Käyttäjätunnukselle pitää antaa salasana, jonka pitää olla vähintään 8 merkkiä pitkä ja sisältää pieniä ja isoja kirjaimia, ja vähintään yksi erikoismerkki ja numero
    - Salasanat talletetaan tietokantaan kryptattuna
    - Käyttäjätunnuksen luodessaan käyttäjä voi myös antaa tärkeimmät henkilötiedot lokikirjan luomista varten (syntymäaika, koko nimi, email)

- Käyttäjä voi kirjautua järjestelmään
    - Kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus ja sitä vastaava salasana kirjautumislomakkeelle
    - Jos käyttäjää ei ole olemassa, kirjautuminen hylätään
    - Jos syötetty salasana ei vastaa käyttäjän tallennettua salasanaa, kirjautuminen hylätään

### Kirjautumisen jälkeen

- Käyttäjä näkee yhteenvedon tähänastisista lennoistaan, mukaanlukien:
    - Lentojen kokonaismäärä
    - Lentojen kokonaisaika
    - Nousujen / laskeutumisien määrä viimeisten 90 päivän aikana, ja varoitus mikäli vähemmän kuin kuin 3 kpl (matkustajia saa kuljettaa vain mikäli vähintään 3)
    - Lentotuntien määrä viimeisten 365 päivän aikana - ja varoitus mikäli vähemmän kun 12 tuntia (luokkakelpoisuuden uusimiseen tarvitaan vähintään 12 tuntia viimeisen vuoden aikana)

- Käyttäjä voi myös katsoa yhteenvetotietoja:
    - Lentokentistä, joissa hän on käynyt, viimeinen käyntipäivä ja nousujen/laskeutumisien määrä per kenttä
    - Lentokoneista, joilla hän on lentänyt: viimeinen lentopäivä ja lentotuntien määrä per kone

- Käyttäjä voi siirtyä uuden lennon tallennustilaan

### Uuden lennon tallennustilassa

- Käyttäjä voi syöttää tiedot lentokoneesta, lähtökentästä ja saapumiskentästä. Mikäli lentokonetta tai kenttiä ei ole sovelluksen tietokannassa, ne luodaan

- Tietoja lentokoneesta, lähtökentästä ja saapumiskentästä voidaan korjata lennon aikana kirjoittamalla uudet tiedot

- Käyttäjä voi yhtä nappia painamalla aloittaa lennon, jolloin lähtöaika talletetaan muistiin ja matkustusajan lasku alkaa

- Tämän jälkeen käyttäjä voi yhtä nappia painamalla merkitä lentoonlähdön, jolloin lentoonlähtöaika talletetaan muistiin ja lentoajan lasku alkaa

- Tämän jälkeen käyttäjä voi merkitä yhtä nappia painamalla läpilaskun, jolloin lentoonlähtöjen ja nousujen määrää kasvatetaan yhdellä

- Tämän jälkeen käyttäjä voi merkitä yhtä nappia painamalla laskeutumisen, jolloin lentoajan laskeminen päätetään ja laskeutumisaika talletetaan muistiin

- Tämän jälkeen käyttäjä voi merkitä yhtä nappia painamalla saapumisajan, jolloin matkustusajan laskeminen päätetään ja saapumisaika talletetaan muistiin. Tämän jälkeen sovellus palaa alkunäkymään (sama kuin kirjautumisen jälkeen).

## Jatkokehitysideoita

- Tällä hetkellä ikkunan sulkeminen painamalla "close"-painiketta lopettaa koko sovelluksen. Joissain tilanteissa kannattaisi ehkä vain lopettaa nykyinen toiminto ja palata pääikkunaan.

- Lisätietojen tallennus lentokentistä - esim. koordinaatit, palvelut, nettisivujen osoite, radiotaajuudet

- Lisätietojen tallennus lentokoneista - esim. tyyppi, tarvittava kelpuutustyyppi, tärkeimmät tiedot (lentonopeus, paino, kantama)

- Lentolupien ja kelpoisuuksien tietojen talletus: tuokka/tyyppihyväksyntöjen tyypit ja viimeiset voimassaolopäivät, varoitus kun pitää uusia yms.

- Mahdollisuus erilaisten ohjaajatyyppien lentoaikojen laskemiseen erikseen (single pilot / multi-pilot ja PIC / Co-Pilot / Dual / FI)

- Mahdollisuus simulaattorilentojen tallentamiseen

- Mahdollisuus erilaisten lentotilojen lentoaikojen laskemiseen erikseen (yö / IFR)

