package br.eti.ns.nsminiprinters.escpos.specs.bematech;

import java.io.UnsupportedEncodingException;

/**
 * Created by alissonlima on 7/28/16.
 */
public class BematechMP2500THSpec80mm extends BematechMP4200THSpec80mm {

    @Override
    public byte[] getInitialPrinterSetup() {
        return new byte[]{0x1D, (byte)0xF9, 0x35, 0x00};
    }

    @Override
    public int getNormalColumnsCount() {
        return 48;
    }

    @Override
    public int getCondensedColumnsCount() {
        return 64;
    }

    @Override
    public int getExpandedColumnsCount() {
        return 24;
    }

    @Override
    public byte[] stringToEncodedBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("CP850");
    }
}
