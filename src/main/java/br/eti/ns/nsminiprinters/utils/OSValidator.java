package br.eti.ns.nsminiprinters.utils;

/**
 * Created by alissonlima on 11/27/15.
 */
public class OSValidator {

    private final static String OS_NAME = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {

        return (OS_NAME.indexOf("win") >= 0);

    }

    public static boolean isWindowsXP() {

        return (isWindows() && OS_NAME.indexOf("xp") >= 0);

    }

    public static boolean isMac() {

        return (OS_NAME.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (OS_NAME.indexOf("nix") >= 0 || OS_NAME.indexOf("nux") >= 0 || OS_NAME.indexOf("aix") > 0 );

    }

    public static boolean isSolaris() {

        return (OS_NAME.indexOf("sunos") >= 0);

    }

}
