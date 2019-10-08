package br.eti.ns.nsminiprinters.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by alissonlima on 8/31/15.
 */
public class PrintersPatternsUtils {

    /**
     * Validate if the portAddress parameter is a Ethernet Address.
     * It will be true if the portAddress contains 3 dots(.) and 1 colon (:).
     * @param portAddress
     * @return 192.168.0.10:2000 will return true; localhost will return false
     */
    public static boolean isEthernetAddress(String portAddress) {
        if (StringUtils.isNotBlank(portAddress)){
            if (StringUtils.countMatches(portAddress, ".") == 3){
                return portAddress.contains(":");
            }
        }
        return false;
    }
}
