package br.eti.ns.nsminiprinters.escpos.specs.epson;

public class EpsonT70Spec58mm extends EpsonT70Spec80mm {

    @Override
    public int getNormalColumnsCount() {
        return 29;
    }

    @Override
    public int getCondensedColumnsCount() {
        return 38;
    }

    @Override
    public int getExpandedColumnsCount() {
        return 13;
    }

}
