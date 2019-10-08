package br.eti.ns.nsminiprinters;

import br.eti.ns.nsminiprinters.escpos.specs.PrinterSpec;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by alissonlima on 9/21/15.
 */
@XmlType
@XmlEnum(String.class)
public enum MiniPrinterModel {

    @XmlEnumValue("BEMATECH MP-4200 TH") BEMATECH_MP_4200_TH("BEMATECH MP-4200 TH"),
    @XmlEnumValue("BEMATECH MP-2500 TH") BEMATECH_MP_2500_TH("BEMATECH MP-2500 TH"),
    @XmlEnumValue("DARUMA DR700") DARUMA_DR700("DARUMA DR700"),
    @XmlEnumValue("EPSON T20") EPSON_TM_T20("EPSON T20"),
    @XmlEnumValue("EPSON T70") EPSON_TM_T70("EPSON T70"),
    @XmlEnumValue("ELGIN I9") ELGIN_I9 ("ELGIN I9");

    private final String name;

    MiniPrinterModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MiniPrinterModel getByName(String name){
        for (MiniPrinterModel miniImpressoraModelo : MiniPrinterModel.values()) {
            if (miniImpressoraModelo.getName().equals(name)) return miniImpressoraModelo;
        }
        return null;
    }

    public static class PrinterModelNotSupportedException extends Exception{
        final MiniPrinterModel miniPrinterModel;

        public PrinterModelNotSupportedException(MiniPrinterModel miniPrinterModel) {
            super("MiniPrinterModel not supported: " + (miniPrinterModel != null ? miniPrinterModel.name : null));
            this.miniPrinterModel = miniPrinterModel;
        }
    };

}
