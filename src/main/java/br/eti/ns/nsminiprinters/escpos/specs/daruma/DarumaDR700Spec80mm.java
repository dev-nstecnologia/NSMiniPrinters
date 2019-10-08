package br.eti.ns.nsminiprinters.escpos.specs.daruma;

import br.eti.ns.nsminiprinters.escpos.specs.PrinterSpec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by alissonlima on 7/28/16.
 */
public class DarumaDR700Spec80mm extends PrinterSpec {

    @Override
    public byte[] getCenterTextOn() {
        return new byte[]{0x1b, 0x6A, 1};
    }

    @Override
    public byte[] getCenterTextOff() {
        return new byte[]{ 0x1b, 0x6A, 0 };
    }

    @Override
    public byte[] getBoldOn() {
        return new byte[]{0x1B, 0x45};
    }

    @Override
    public byte[] getBoldOff() {
        return new byte[]{0x1B, 0x46};
    }

    @Override
    public byte[] getExpandedOn() {
        return new byte[]{0x1B, 0x57, 1};
    }

    @Override
    public byte[] getExpandedOff() {
        return new byte[]{0x1B, 0x57, 0};
    }

    @Override
    public byte[] getCondensedOn() {
        return new byte[]{0x1B, 0x0F};
    }

    @Override
    public byte[] getCondensedOff() {
        return new byte[]{0X12};
    }

    @Override
    public byte[] getCutPaper() {
        return new byte[]{0x1b, 0x6d};
    }

    @Override
    public int getNormalColumnsCount() {
        return 48;
    }

    @Override
    public int getCondensedColumnsCount() {
        return 57;
    }

    @Override
    public int getExpandedColumnsCount() {
        return 24;
    }

    @Override
    public byte[] getInitialPrinterSetup() {
        return new byte[0];
    }

    @Override
    public int getEndBreaklinesCount() {
        return 6;
    }

    @Override
    public byte[] stringToEncodedBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("ISO-8859-1");
    }

    @Override
    public byte[] getBarcode128Bytes(String barcodeString) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        int barcodeMiddle = barcodeString.length() / 2;

        initBarcode128(stream);
        stream.write(barcodeString.substring(0, barcodeMiddle).getBytes());
        endBarcode128(stream);

        stream.write(PrinterSpec.NEW_LINE_BYTES);

        initBarcode128(stream);
        stream.write(barcodeString.substring(barcodeMiddle, barcodeString.length()).getBytes());
        endBarcode128(stream);

        return stream.toByteArray();
    }

    private void initBarcode128(ByteArrayOutputStream stream) throws IOException {
        stream.write(new byte[]{0x1B, 0x62, 0x05}); //code128
        stream.write(new byte[]{0x02}); //bar width 2 - 5
        stream.write(new byte[]{0x50}); //bar height 50 - 200
        stream.write(new byte[]{0x00}); //disable human readable content
    }

    private void endBarcode128(ByteArrayOutputStream stream) throws IOException {
        stream.write(new byte[]{0x00}); //end barcode
    }

    @Override
    public byte[] getQrCodeBytes(String qrCodeString) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int urlLenght = qrCodeString.length();
        int bMenos = urlLenght >> 8;
        int bMais = (urlLenght & 0xff) + 2;

        byte[] qrCodeBegin = {0x1b, (byte)0x81};
        stream.write(qrCodeBegin);

        stream.write(bMais);
        stream.write(bMenos);
        stream.write(3);
        stream.write(0x4d);
        stream.write(qrCodeString.getBytes());

        return stream.toByteArray();
    }

    @Override
    public byte[] getOpenDrawerBytes() {
        return new byte[]{0x1B, 0x70};
    }
}
