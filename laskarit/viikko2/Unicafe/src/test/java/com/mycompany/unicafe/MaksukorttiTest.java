package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void alkuSaldoOikein() {
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void lataaminenKasvattaaSaldoa() {
        assertEquals(10, kortti.saldo());
        kortti.lataaRahaa(5);
        assertEquals(15, kortti.saldo());
    }
    
    @Test
    public void ottaminenVahentaaRahaa() {
        assertEquals(10, kortti.saldo());
        kortti.otaRahaa(3);
        assertEquals(7, kortti.saldo());
    }

    @Test
    public void saldoEiMuutuJosOtetaanLiikaa() {
        assertEquals(10, kortti.saldo());
        kortti.otaRahaa(11);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void ottoPalauttaaTrueJosRahaaRiittavasti() {
        assertEquals(true, kortti.otaRahaa(10));
    }

    @Test 
    public void ottoPalauttaaFalseJosRahaaEiRiittavasti() {
        assertEquals(false, kortti.otaRahaa(11));
    }

    @Test
    public void toStringToimii() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
}
