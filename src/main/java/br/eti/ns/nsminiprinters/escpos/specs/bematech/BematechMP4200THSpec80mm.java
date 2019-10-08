package br.eti.ns.nsminiprinters.escpos.specs.bematech;

import br.eti.ns.nsminiprinters.escpos.specs.PrinterSpec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by alissonlima on 7/28/16.
 */
public class BematechMP4200THSpec80mm extends PrinterSpec {

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
        return new byte[]{0x1B, 0x69};
    }

    @Override
    public int getNormalColumnsCount() {
        return 50;
    }

    @Override
    public int getCondensedColumnsCount() {
        return 67;
    }

    @Override
    public int getExpandedColumnsCount() {
        return 25;
    }

    @Override
    public byte[] getInitialPrinterSetup() {
//        byte[] ENABLE_ESC_BEMA = { 0x1D, (byte)0xF9, 0x35, 0x00 };
//        byte[] ENABLE_UTF8 = { 0x1B, 0x74, 0x08 };
//        0x1B, 0x33, 0x5 Diminui espacamento entre linhas
        return new byte[]{0x1D, (byte)0xF9, 0x35, 0x00, 0x1B, 0x74, 0x08, 0x1B, 0x33, 0x5};
    }

    @Override
    public int getEndBreaklinesCount(){
        return 0;
    }

    @Override
    public byte[] stringToEncodedBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF8");
    }

    @Override
    public byte[] getBarcode128Bytes(String barcodeString) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        stream.write(new byte[]{0x1D, 0x48, 0x00}); //disable human readable text printing
        stream.write(new byte[]{0x1D, 0x77, 0x02}); //barcode width
        stream.write(new byte[]{0x1D, 0x68, 0x50}); //barcode height
        stream.write(new byte[]{0x1D, 0x6B, 0x49}); //code128

        stream.write((char)barcodeString.length());
        stream.write(barcodeString.getBytes());

        return stream.toByteArray();
    }

    @Override
    public byte[] getQrCodeBytes(String qrCodeString) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //Centralizar
        stream.write(new byte[] {0X1b, 0X61, 0X01});

        //Iniciar QRCode
        stream.write(new byte[] {0x1D, 0x6B, 0x51});

        // Correction Level, Module Size, Version QRCode, Encoding Modes
        stream.write(new byte[] { 0x00, 0x06, 0x00, 0x01 });

        byte[] qrCodeStringBytes = qrCodeString.getBytes();

        int restoDivisao = qrCodeStringBytes.length;
        int divisao = 0;
        if(qrCodeStringBytes.length > 256){
            restoDivisao = qrCodeStringBytes.length % 256;
            divisao = qrCodeStringBytes.length / 256;
        }
        stream.write(new byte[]{(byte) restoDivisao, (byte)divisao});
        stream.write(qrCodeStringBytes);

        return stream.toByteArray();
    }


//    public byte[] getQrCodeBytes(String qrCodeString) throws IOException {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//        byte[] qrCodeStringBytes = qrCodeString.getBytes();
//
//        //FORNECIDO PELA BEMATECH
//        //	iniciar qrcode
//        byte[] qrcode = { 0X1b, 0X61, 0X01, 0x1D, 0x6B, 0x51 };
//
//        // configurar dimensão qrcode 8
//        byte[] dimensao = { 0x01, 0x10, 0x10, 0x01 };
//
//        //	resto da divisão caso o tamanho seja maior que 255 (tamanho do texto / 255) 31 = 1F
//        int resto_divisao = qrCodeStringBytes.length;
//        if(qrCodeStringBytes.length > 255)
//        {
//            resto_divisao = qrCodeStringBytes.length % 255;
//        }
//        byte resto_divisao_byte = (byte)resto_divisao;
//
//        //	resto da divisão caso o tamanho seja maior que 255 (tamanho do texto / 255) 31 = 1F
//        int divisao = 0;
//        if(qrCodeStringBytes.length > 255)
//        {
//            divisao = qrCodeStringBytes.length / 255;
//        }
//        byte divisao_byte = (byte)divisao;
//
//        /// Rotina para concatenar os array
//        int len = qrcode.length + dimensao.length + 1 + 1 + qrCodeStringBytes.length;
//        byte[] toprint = new byte[len];
//        int last_pos_qrcode 	= qrcode.length;
//        int last_pos_dimensao 	= qrcode.length + dimensao.length;
//        int last_pos_rest_div 	= last_pos_dimensao + 1;
//        int last_pos_divisao 	= last_pos_rest_div + 1;
//        for(int i=0; i<len; i++)
//        {
//            if(i < last_pos_qrcode){
//                toprint[i] = qrcode[i];
//            }else if(i < last_pos_dimensao){
//                toprint[i] = dimensao[i-last_pos_qrcode];
//            }else if(i < last_pos_rest_div){
//                toprint[i] = resto_divisao_byte;
//            }else if(i < last_pos_divisao){
//                toprint[i] = divisao_byte;
//            }else{
//                toprint[i] = qrCodeStringBytes[i-last_pos_divisao];
//            }
//        }
//        //FIM DO CODIGO FORNECIDO PELA BEMATECH
//
//        stream.write(toprint);
////        stream.write(" ".getBytes());
//
//        return stream.toByteArray();
//    }

    @Override
    public byte[] getOpenDrawerBytes() {
        return new byte[]{0x1B, 0x76};
    }
}
