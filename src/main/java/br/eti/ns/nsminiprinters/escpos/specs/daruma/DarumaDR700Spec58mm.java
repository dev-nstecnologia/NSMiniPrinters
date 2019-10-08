package br.eti.ns.nsminiprinters.escpos.specs.daruma;

public class DarumaDR700Spec58mm extends DarumaDR700Spec80mm{

    @Override
    public int getNormalColumnsCount() {
        return 34;
    }

    @Override
    public int getCondensedColumnsCount() {
        return 40;
    }

    @Override
    public int getExpandedColumnsCount() {
        return 17;
    }

}
