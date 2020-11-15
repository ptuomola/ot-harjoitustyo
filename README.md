# Ohjelmistotekniikka, harjoitustyö

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/ptuomola/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/ptuomola/ot-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

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

Kattavuusraporttia voi tarkastella selaimella tiedosto target/site/jacoco/index.html
