package br.eti.ns.nsminiprinters.escpos.specs.epson;

import br.eti.ns.nsminiprinters.escpos.specs.PrinterSpec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by alissonlima on 7/28/16.
 */
public class EpsonT20Spec80mm extends PrinterSpec {

    @Override
    public byte[] getCenterTextOn() {
        return new byte[]{ 0x1B, 0x61, 1 };
    }

    @Override
    public byte[] getCenterTextOff() {
        return new byte[]{ 0x1B, 0x61, 0 };
    }

    @Override
    public byte[] getBoldOn() {
        return new byte[]{0x1B, 0x21, 8};
    }

    @Override
    public byte[] getBoldOff() {
        return new byte[]{0x1B, 0x21, 0};
    }

    @Override
    public byte[] getExpandedOn() {
        return new byte[]{0x1B, 0x21, 0x20};
    }

    @Override
    public byte[] getExpandedOff() {
        return new byte[]{0x1B, 0x21, 0};
    }

    @Override
    public byte[] getCondensedOn() {
        return new byte[]{0x1B, 0x4D, 1};
    }

    @Override
    public byte[] getCondensedOff() {
        return new byte[]{0x1B, 0x4D, 0};
    }

    @Override
    public byte[] getCutPaper() {
        return new byte[]{0x1d, 0x56, 1};
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
    public byte[] getInitialPrinterSetup() {
        //ENABLE_UTF8
        return new byte[]{0x1B, 0x74, 0x10};
    }

    @Override
    public int getEndBreaklinesCount() {
        return 3;
    }

    @Override
    public byte[] stringToEncodedBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("CP1252");
    }

    @Override
    public byte[] getBarcode128Bytes(String barcodeString) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        stream.write(new byte[]{0x1D, 0x48, 0x00}); //disable human readable text printing
        stream.write(new byte[]{0x1D, 0x77, 0x02}); //barcode width
        stream.write(new byte[]{0x1D, 0x68, 0x50}); //barcode height
        stream.write(new byte[]{0x1D, 0x6B, 0x49}); //code128
////        stream.write(new byte[]{0x1D, 0x6B, 0x43}); //ean13
        stream.write((char)((barcodeString.length()/ 2) + 2));
        stream.write(new byte[] {'{', 'C'}); // enable CODE128 C

        writeBarcodeInPairs(barcodeString, stream);

        return stream.toByteArray();
    }

    private void writeBarcodeInPairs(String barcodeString, ByteArrayOutputStream stream) {
        String barcodePairs[] = barcodeString.split("(?<=\\G.{2})");
        for (String barcodePair : barcodePairs) {
            stream.write(Integer.parseInt(barcodePair));
        }
    }

    @Override
    public byte[] getQrCodeBytes(String qrCodeString) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        int store_len = qrCodeString.length() + 3;
        byte store_pL = (byte) (store_len % 256);
        byte store_pH = (byte) (store_len / 256);

        // QR Code: Select the model
        //              Hex     1D      28      6B      04      00      31      41      n1(x32)     n2(x00) - size of model
        // set n1 [49 x31, model 1] [50 x32, model 2] [51 x33, micro qr code]
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=140
        byte[] modelQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x04, (byte)0x00, (byte)0x31, (byte)0x41, (byte)0x32, (byte)0x00};

        // QR Code: Set the size of module
        // Hex      1D      28      6B      03      00      31      43      n
        // n depends on the printer
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=141
        byte[] sizeQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x03, (byte)0x00, (byte)0x31, (byte)0x43, (byte)0x03};


        //          Hex     1D      28      6B      03      00      31      45      n
        // Set n for error correction [48 x30 -> 7%] [49 x31-> 15%] [50 x32 -> 25%] [51 x33 -> 30%]
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=142
        byte[] errorQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x03, (byte)0x00, (byte)0x31, (byte)0x45, (byte)0x31};


        // QR Code: Store the data in the symbol storage area
        // Hex      1D      28      6B      pL      pH      31      50      30      d1...dk
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=143
        //                        1D          28          6B         pL          pH  cn(49->x31) fn(80->x50) m(48->x30) d1…dk
        byte[] storeQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, store_pL, store_pH, (byte)0x31, (byte)0x50, (byte)0x30};


        // QR Code: Print the symbol data in the symbol storage area
        // Hex      1D      28      6B      03      00      31      51      m
        // https://reference.epson-biz.com/modules/ref_escpos/index.php?content_id=144
        byte[] printQR = {(byte)0x1d, (byte)0x28, (byte)0x6b, (byte)0x03, (byte)0x00, (byte)0x31, (byte)0x51, (byte)0x30};

        // flush() runs the print job and clears out the print buffer

        // write() simply appends the data to the buffer
        stream.write(modelQR);

        stream.write(sizeQR);
        stream.write(errorQR);
        stream.write(storeQR);
        stream.write(qrCodeString.getBytes());
        stream.write(printQR);

        return stream.toByteArray();
    }

    @Override
    public byte[] getOpenDrawerBytes() {
        //0x1b 0x70 0x00 0x19 0xFA Conector pino 2
        //0x1b 0x70 0x01 0x19 0xFA Conector pino 5
        return new byte[]{0x1B, 0x70, 0x00, 0x19, (byte)0xFA, 0x1B, 0x70, 0x01, 0x19, (byte)0xFA};
    }
}
