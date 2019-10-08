package br.eti.ns.nsminiprinters.escpos.specs.epson;

public class EpsonT20Spec58mm extends EpsonT20Spec80mm {

    @Override
    public int getNormalColumnsCount() {
        return 35;
    }

    @Override
    public int getCondensedColumnsCount() {
        return 46;
    }

    @Override
    public int getExpandedColumnsCount() {
        return 17;
    }

}
