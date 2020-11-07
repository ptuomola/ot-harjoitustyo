package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {

    Kassapaate kp;

    @Before
    public void setUp() {
        kp = new Kassapaate();
    }

    @Test
    public void luotuKassapaateOikein() {
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void ostaEdullinenKateisella() {
        assertEquals(60, kp.syoEdullisesti(300));
        assertEquals(1, kp.edullisiaLounaitaMyyty());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
        assertEquals(100240, kp.kassassaRahaa());
    }
    
    @Test
    public void ostaEdullinenKateisellaEiTarpeeksiRahaa() {
        assertEquals(200, kp.syoEdullisesti(200));
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
    }

    @Test
    public void ostaMaukasKateisella() {
        assertEquals(200, kp.syoMaukkaasti(600));
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(1, kp.maukkaitaLounaitaMyyty());
        assertEquals(100400, kp.kassassaRahaa());
    }
    
    @Test
    public void ostaMaukasKateisellaEiTarpeeksiRahaa() {
        assertEquals(300, kp.syoMaukkaasti(300));
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
    }
    
    @Test 
    public void ostaEdullinenKortilla() {
        Maksukortti mk = new Maksukortti(1000);
        assertEquals(true, kp.syoEdullisesti(mk));
        assertEquals(1, kp.edullisiaLounaitaMyyty());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(760, mk.saldo());
    }

    @Test 
    public void ostaEdullinenKortillaEiTarpeeksiRahaa() {
        Maksukortti mk = new Maksukortti(100);
        assertEquals(false, kp.syoEdullisesti(mk));
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(100, mk.saldo());
    }

    @Test 
    public void ostaMaukasKortilla() {
        Maksukortti mk = new Maksukortti(1000);
        assertEquals(true, kp.syoMaukkaasti(mk));
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(1, kp.maukkaitaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(600, mk.saldo());
    }

    @Test 
    public void ostaMaukasKortillaEiTarpeeksiRahaa() {
        Maksukortti mk = new Maksukortti(100);
        assertEquals(false, kp.syoMaukkaasti(mk));
        assertEquals(0, kp.edullisiaLounaitaMyyty());
        assertEquals(0, kp.maukkaitaLounaitaMyyty());
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(100, mk.saldo());
    }
    
    @Test
    public void kortinLatausToimii() {
        Maksukortti mk = new Maksukortti(100);
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(100, mk.saldo());
        kp.lataaRahaaKortille(mk, 100);
        assertEquals(100100, kp.kassassaRahaa());
        assertEquals(200, mk.saldo());
    }
    
    @Test
    public void kortinLatausNegatiivinenMaara() {
        Maksukortti mk = new Maksukortti(100);
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(100, mk.saldo());
        kp.lataaRahaaKortille(mk, -100);
        assertEquals(100000, kp.kassassaRahaa());
        assertEquals(100, mk.saldo());
    }
}
