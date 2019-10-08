package br.eti.ns.nsminiprinters.escpos.specs.elgin;

public class ElginI9Spec58mm extends ElginI9Spec80mm {

    @Override
    public int getNormalColumnsCount() {
        return 30;
    }

    @Override
    public int getCondensedColumnsCount() {
        return 40;
    }

    @Override
    public int getExpandedColumnsCount() {
        return 15;
    }

}
