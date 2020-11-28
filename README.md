# FlightLogBook

Yksityislentäjillä on lakisääteinen vaatimus pitää lennoistaan lokikirjaa, johon pitää merkitä jokaisesta lennosta tietyt tiedot - mm. nousujen/laskujen määrä, lähtö/saapumisajat (UTC), lentoonlähtö/laskeutumisajat (UTC) yms. Tarvittavien kellonaikojen yms. merkitseminen muistiin lennon aikana on hankalaa, varsinkin kun ne usein sattuvat juuri niihin lennon vaiheisiin, jolloin on paljon muutakin tärkeää tekemistä.

Tämän sovelluksen tarkoitus onkin tarjota yksinkertainen käyttöliittymä lentäjälle kellonaikojen ja muiden tärkeiden lentotietojen muistiin tallentamiseen lennon aikana, ja muodostaa tarvittavat lokikirjan rivit näiden talletettujen tietojen perusteella.

Sovellus laskee myös talletettujen lokikirjan tietojen perusteella yksinkertaisia yhteenvetotietoja lentäjän tiedoksi, mm. kokonaislentoaika, laskeutumisien yhteismäärä yms.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/ptuomola/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/ptuomola/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/ptuomola/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot

Maven projekti on hakemistossa "FlightLogBook". Allaolevat käskyt pitää suorittaa ko. hakemistossa. 

### Ohjelman käynnistys

Ohjelman voi käynnistää komennolla:

```
mvn compile exec:java -Dexec.mainClass=org.tuomola.flightlogbook.Main
```

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston FlightLogBook-1.0-SNAPSHOT.jar

### Checkstyle

Tiedostoon checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
