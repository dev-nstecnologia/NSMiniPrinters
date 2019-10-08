package br.eti.ns.nsminiprinters.escpos.specs.bematech;

public class BematechMP4200THSpec58mm extends BematechMP4200THSpec80mm {

    @Override
    public int getNormalColumnsCount() {
        return 32;
    }

    @Override
    public int getCondensedColumnsCount() {
        return 42;
    }

    @Override
    public int getExpandedColumnsCount() {
        return 16;
    }

}
