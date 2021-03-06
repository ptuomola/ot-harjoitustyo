# Testausdokumentti

Ohjelmaa on testattu sekä automatisoiduin yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti tapahtunein järjestelmätason testein.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Automatisoidut testit on jaettu luokkiin sen perusteella, mihin domain-olioon liittyvää toiminnallisuutta ne testaavat. Eli esimerkiksi Aircraft-olioon liittyvät testit ovat luokassa AircraftTest, FlightLog-olioon liittyvät testit ovat luokassa FlightLogTest jne. Kukin testiluokka testaa olioon liittyviä toiminnallisuuksia sekä itse domain-oliosta (s.o. pakkauksen [org.tuomola.flightlogbook.domain](https://github.com/ptuomola/ot-harjoitustyo/tree/master/FlightLogBook/src/main/java/org/tuomola/flightlogbook/domain)-luokista) että myös vastaavasta sovelluslogiikkaluokasta (s.o. pakkauksen [org.tuomola.flightlogbook.service](https://github.com/ptuomola/ot-harjoitustyo/tree/master/FlightLogBook/src/main/java/org/tuomola/flightlogbook/service)-luokista). 

Kaikki automatisoidut testit sijaitsevat pakkauksessa [org.tuomola.flightlogbook.domain.test](https://github.com/ptuomola/ot-harjoitustyo/tree/master/FlightLogBook/src/test/java/org/tuomola/flightlogbook/domain/test). Niiden testaamat toiminnallisuudet vastaavat käyttöliittymän [org.tuomola.flightlogbook.ui](https://github.com/ptuomola/ot-harjoitustyo/tree/master/FlightLogBook/src/main/java/org/tuomola/flightlogbook/ui) tekemiä kutsuja sovelluslogiikkaan ja domain-olioihin. 

Integraatiotestit käyttävät datan pysyväistallennukseen keskusmuistitoteutusta H2-tietokannasta. Tämä konfiguroidaan "test"-profiilin käytössä ollessa [JpaTestConfig](https://github.com/ptuomola/ot-harjoitustyo/tree/master/FlightLogBook/src/main/java/org/tuomola/flightlogbook/dao/JpaTestConfig.java)-luokassa. 

Domain-olioiden _equals_ ja _hashCode()_ yhteensopivuus Java-standardin kanssa testataan käyttämällä EqualsVerifier-kirjastoa. 

### DAO-luokat

DAO-luokkien toiminnallisuus testautuu automaattisesti sovelluslogiikkaa testaavien luokkien kautta. Kuten jo mainittu, testit käyttävät keskusmuistitietokantaa, joka alustuu automaattisesti uudestaan jokaista testikertaa varten. Samalla testataan myös tietokannan automaattinen alustustoiminto. 

### Testauskattavuus

Käyttöliittymäkerrosta lukuunottamatta sovelluksen testauksen rivikattavuus on 84% ja haarautumakattavuus 84%

<img src="https://github.com/ptuomola/ot-harjoitustyo/blob/master/dokumentaatio/images/testcoverage.png" width="800">

Testaamatta jäivät lähinnä jotkin virhetilanteet (esim. puuttuva pakollinen tieto, null pointer), joita varten sovelluslogiikassa on tarkastus, mutta joita ei normaalissa sovelluksen käytössä yleensä pitäisi tapahtua. 

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.

### Asennus ja konfigurointi

Sovellus on haettu ja sitä on testattu [käyttöohjeen](https://github.com/ptuomola/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) kuvaamalla tavalla sekä OSX- että Linux-ympäristöön. 

Sovellusta on testattu sekä tilanteissa, joissa ohjelman tietokanta on jo ollut olemassa,  ja joissa sitä ei ole ollut (jolloin ohjelma on luonut sen itse).

### Toiminnallisuudet

Kaikki [määrittelydokumentin](https://github.com/ptuomola/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md#perusversion-tarjoama-toiminnallisuus) ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi. Kaikkien toiminnallisuuksien yhteydessä on syötekentät yritetty täyttää myös virheellisillä arvoilla kuten tyhjillä.
