package br.eti.ns.nsminiprinters.escpos.specs;

import br.eti.ns.nsminiprinters.MiniPrinterModel;
import br.eti.ns.nsminiprinters.escpos.PrinterOptions;
import br.eti.ns.nsminiprinters.escpos.specs.bematech.*;
import br.eti.ns.nsminiprinters.escpos.specs.daruma.*;
import br.eti.ns.nsminiprinters.escpos.specs.elgin.*;
import br.eti.ns.nsminiprinters.escpos.specs.epson.*;

/**
 * Created by alissonlima on 8/3/16.
 */
public class PrinterSpecFactory {

    public static PrinterSpec getByModel(MiniPrinterModel miniPrinterModel) throws MiniPrinterModel.PrinterModelNotSupportedException {
        return getByModel(miniPrinterModel, PrinterOptions.PAPERWIDTH.PAPER_80MM);
    }

    public static PrinterSpec getByModel(MiniPrinterModel miniPrinterModel, PrinterOptions.PAPERWIDTH paperWidth) throws MiniPrinterModel.PrinterModelNotSupportedException {
        switch (miniPrinterModel){
            case BEMATECH_MP_2500_TH:
                if(PrinterOptions.PAPERWIDTH.PAPER_58MM.equals(paperWidth)){
                    return new BematechMP2500THSpec58mm();
                }
                return new BematechMP2500THSpec80mm();

            case BEMATECH_MP_4200_TH:
                if(PrinterOptions.PAPERWIDTH.PAPER_58MM.equals(paperWidth)){
                    return new BematechMP4200THSpec58mm();
                }
                return new BematechMP4200THSpec80mm();

            case DARUMA_DR700:
                if(PrinterOptions.PAPERWIDTH.PAPER_58MM.equals(paperWidth)){
                    return new DarumaDR700Spec58mm();
                }
                return new DarumaDR700Spec80mm();

            case EPSON_TM_T20:
                if(PrinterOptions.PAPERWIDTH.PAPER_58MM.equals(paperWidth)){
                    return new EpsonT20Spec58mm();
                }
                return new EpsonT20Spec80mm();

            case EPSON_TM_T70:
                if(PrinterOptions.PAPERWIDTH.PAPER_58MM.equals(paperWidth)){
                    return new EpsonT70Spec58mm();
                }
                return new EpsonT70Spec80mm();

            case ELGIN_I9:
                if(PrinterOptions.PAPERWIDTH.PAPER_58MM.equals(paperWidth)){
                    return new ElginI9Spec58mm();
                }
                return new ElginI9Spec80mm();

        }

        throw new MiniPrinterModel.PrinterModelNotSupportedException(miniPrinterModel);

    }

    public static PrinterSpec getByModel(String miniPrinterModel) throws MiniPrinterModel.PrinterModelNotSupportedException {
        return getByModel(MiniPrinterModel.getByName(miniPrinterModel));

    }
}
