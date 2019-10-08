package br.eti.ns.nsminiprinters.escpos;

import br.eti.ns.nsminiprinters.utils.OSValidator;
import br.eti.ns.nsminiprinters.utils.PrintersPatternsUtils;
import jssc.SerialPort;
import jssc.SerialPortException;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by alissonlima on 7/28/16.
 */
public class EscPosPrinter {

    private final static Logger logger = Logger.getLogger(EscPosPrinter.class);
    private final PrinterOptions printerOptions;
    private final static Object lockObject = new Object();

    public EscPosPrinter(PrinterOptions printerOptions) {
        this.printerOptions = printerOptions;
    }

    public void print(byte[] content) throws Exception {
        print(content, 1);
    }

    public void print(byte[] content, int copies) throws Exception {

        synchronized (lockObject){

            logger.debug("Iniciando impressao em mini impressora. Quantidade de copias: " + copies);

            for (int i = 1; i <= copies; i++) {
                logger.debug("Imprimindo copia " + i);
                printContent(content);
            }
        }

    }

    public void printContent(byte[] content) throws Exception{
        String printerPort = printerOptions.port;
        logger.debug("Porta configurada para impressora: " + printerPort);

        if (PrintersPatternsUtils.isEthernetAddress(printerPort)){
            printContentInEthernetPrinter(content);

        } else {
            try {
                executeWindowsPortSettingHack(printerPort);

                Path printerPortPath = Paths.get(printerPort);
                if (isPrinterPortFile(printerPortPath)){
                    printContentInFile(content, printerPortPath);

                } else {
                    printContentInSerialPrinter(content);
                }

            } catch (InvalidPathException e){
                printContentInSerialPrinter(content);
            }

        }
    }

    private void printContentInEthernetPrinter(byte[] content) throws IOException {
        logger.debug("Imprimindo conteudo em impressora via Ethernet");
        String[] ethernetAddress = printerOptions.port.split(":");
        Socket socket = new Socket(ethernetAddress[0], Integer.parseInt(ethernetAddress[1]));

        try (OutputStream outputStream = socket.getOutputStream()){
            outputStream.write(content);
            outputStream.flush();
        }
        socket.close();
    }

    //Hack to work on Windows because it lose the port settings
    //so we have to force it to configure again
    private void executeWindowsPortSettingHack(String printerPort) throws InterruptedException {
        if (OSValidator.isWindows()){
            try {
                if (printerOptions.portSpeed == 0){
                    logger.debug("Sem velocidade configurada. Hack de configuracao de porta serial nao sera executado");
                    return;
                }

                String modeComPath = System.getenv("WINDIR") + "\\System32\\mode.com";
                String modeComParam = String.format("%1s:%1s,n,8,1", printerPort, printerOptions.portSpeed);

                logger.debug(String.format("Executando hack de configuracao de porta no Windows: %1s %1s",
                        modeComPath,
                        modeComParam));

                String[] command = {modeComPath, modeComParam};
                Process p = Runtime.getRuntime().exec(command);
                p.waitFor();

                logger.debug("Retorno: " + p.exitValue());

            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private boolean isPrinterPortFile(Path printerPort) {
        return Files.exists(printerPort);
    }

    private void printContentInFile(byte[] content, Path printerPort) throws IOException {
        logger.debug("Imprimindo conteudo em impressora via OutputStream (arquivo)");
        try (FileOutputStream fileOutputStream = new FileOutputStream(printerPort.toFile())){
            fileOutputStream.write(content);
            fileOutputStream.flush();
        }
    }

    private void printContentInSerialPrinter(byte[] content) throws SerialPortException {
        logger.debug("Imprimindo conteudo em impressora via porta Serial");
        SerialPort serialPort = new SerialPort(printerOptions.port);

        try {
            serialPort.openPort();

            serialPort.setParams(printerOptions.portSpeed,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort.writeBytes(content);

        } finally {
            if (serialPort.isOpened()){
                serialPort.closePort();
            }
        }
    }

}
