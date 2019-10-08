package br.eti.ns.nsminiprinters.escpos.specs.bematech;

public class BematechMP2500THSpec58mm extends BematechMP2500THSpec80mm {

    @Override
    public byte[] getInitialPrinterSetup() {
//        byte[] ENABLE_ESC_BEMA = { 0x1D, (byte)0xF9, 0x35, 0x00 };
        return new byte[]{0x1D, (byte)0xF9, 0x35, 0x00};
    }

    @Override
    public int getNormalColumnsCount() {
        return 30;
    }

    @Override
    public int getCondensedColumnsCount() {
        return 39;
    }

    @Override
    public int getExpandedColumnsCount() {
        return 15;
    }

}
