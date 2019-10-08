package br.eti.ns.nsminiprinters.escpos.specs.epson;

/**
 * Created by alissonlima on 7/28/16.
 */
public class EpsonT70Spec80mm extends EpsonT20Spec80mm {

    @Override
    public int getNormalColumnsCount() {
        return 42;
    }

    @Override
    public int getCondensedColumnsCount() {
        return 56;
    }

    @Override
    public int getExpandedColumnsCount() {
        return 20;
    }

}
