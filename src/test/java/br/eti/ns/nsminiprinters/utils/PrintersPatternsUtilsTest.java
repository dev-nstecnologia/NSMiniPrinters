package br.eti.ns.nsminiprinters.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alissonlima on 7/28/16.
 */
public class PrintersPatternsUtilsTest {

    @Test
    public void isEthernetPortTest(){
        try{
            assertTrue(PrintersPatternsUtils.isEthernetAddress("192.168.0.10:2000"));
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void isEthernetPortWithCom3Test(){
        try{
            assertFalse(PrintersPatternsUtils.isEthernetAddress("COM3"));
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}