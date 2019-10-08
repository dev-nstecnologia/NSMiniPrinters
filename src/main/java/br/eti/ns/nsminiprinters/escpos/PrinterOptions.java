package br.eti.ns.nsminiprinters.escpos;

/**
 * Created by alissonlima on 7/28/16.
 */
public class PrinterOptions {
    public String port;
    public int portSpeed;
    public PAPERWIDTH paperWidth = PAPERWIDTH.PAPER_80MM;

    public enum PAPERWIDTH {
        PAPER_80MM("80mm"),
        PAPER_58MM("58mm");

        private final String name;

        PAPERWIDTH(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static PAPERWIDTH getByName(String name){
            for (PAPERWIDTH paperwidth : PAPERWIDTH.values()) {
                if (paperwidth.getName().equals(name)){
                    return paperwidth;
                }
            }
            return null;
        }
    }
}
