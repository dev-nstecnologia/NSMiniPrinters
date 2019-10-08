package br.eti.ns.nsminiprinters.escpos.specs;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by alissonlima on 7/28/16.
 */
public abstract class PrinterSpec {

    public final static byte[] NEW_LINE_BYTES = new byte[]{13, 10}; //\r\n

    private byte[] groupSeparator;

    public byte[] getGroupSeparator(){
        if (groupSeparator == null){
            groupSeparator = StringUtils.leftPad("", getNormalColumnsCount(), "-").concat("\r\n").getBytes();
        }
        return groupSeparator;
    }

    public abstract byte[] getCenterTextOn();
    public abstract byte[] getCenterTextOff();

    public abstract byte[] getBoldOn();
    public abstract byte[] getBoldOff();

    public abstract byte[] getExpandedOn();
    public abstract byte[] getExpandedOff();

    public abstract byte[] getCondensedOn();
    public abstract byte[] getCondensedOff();

    public abstract byte[] getCutPaper();

    public abstract int getNormalColumnsCount();
    public abstract int getCondensedColumnsCount();
    public abstract int getExpandedColumnsCount();

    public abstract byte[] getInitialPrinterSetup();

    public abstract int getEndBreaklinesCount();

    public abstract byte[] stringToEncodedBytes(String str) throws UnsupportedEncodingException;

    public abstract byte[] getBarcode128Bytes(String qrCodeString) throws IOException;

    public abstract byte[] getQrCodeBytes(String barcodeString) throws IOException;

    public abstract byte[] getOpenDrawerBytes();
}
