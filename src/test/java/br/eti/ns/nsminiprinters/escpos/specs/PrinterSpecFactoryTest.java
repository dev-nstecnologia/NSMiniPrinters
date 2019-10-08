package br.eti.ns.nsminiprinters.escpos.specs;

import br.eti.ns.nsminiprinters.MiniPrinterModel;
import br.eti.ns.nsminiprinters.escpos.specs.epson.EpsonT20Spec80mm;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by alissonlima on 8/3/16.
 */
public class PrinterSpecFactoryTest {

    @Test
    public void getByModelEpsonT20Test(){
        try{
            PrinterSpec printerSpec = PrinterSpecFactory.getByModel(MiniPrinterModel.EPSON_TM_T20);
            assertTrue(EpsonT20Spec80mm.class.isInstance(printerSpec));
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void getByModelNameEpsonT20Test(){
        try{
            PrinterSpec printerSpec = PrinterSpecFactory.getByModel("EPSON T20");
            assertTrue(EpsonT20Spec80mm.class.isInstance(printerSpec));
        } catch(Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}