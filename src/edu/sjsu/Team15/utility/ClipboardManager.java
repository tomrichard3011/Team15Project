package edu.sjsu.Team15.utility;

import io.github.novacrypto.SecureCharBuffer;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Handles copying to clipboard in a thread safe way
 */
public class ClipboardManager implements Runnable {
    /** Time before clipboard is cleared */
    private final int clearTime;
    /** password to be copied to clipboard */
    private SecureCharBuffer charBuffer;

    /**
     * Constructor
     * @param charBuffer password to copy to clipboard
     * @param clearTime how long the password exists on the clipboard
     */
    public ClipboardManager(SecureCharBuffer charBuffer, int clearTime) {
        this.clearTime = clearTime;
        this.charBuffer = charBuffer;
    }

    /**
     * Copy to clipboard
     * @param charBuffer password
     * @param clearTime clear time to use
     */
    public static void copyToClip(SecureCharBuffer charBuffer, int clearTime) {
        StringBuilder password = new StringBuilder();
        password.append(charBuffer.toStringAble());

        switch (getOSType()) {
            case LINUX:
                linuxCopyToClip(password.toString(), clearTime);
            case WINDOWS:
                windowsCopyToClip(password.toString(), clearTime);
            case MAC:
                macCopyToClip(password.toString(), clearTime);

        }
    }

    /**
     * Find operating system type
     * @return OS enum
     */
    private static OS getOSType(){
        if (System.getProperty("os.name").startsWith("Linux")) return OS.LINUX;
        else if (System.getProperty("os.name").startsWith("Windows")) return OS.WINDOWS;
        else return OS.MAC;
    }

    /**
     * SecureCharBuffer setter
     * @param charBuffer password to be used to copy
     */
    public void setCharBuffer(SecureCharBuffer charBuffer) {
        this.charBuffer = charBuffer;
    }

    /**
     * Windows specific copy to clipboard
     * @param password password to copy
     * @param clearTime how long password exists on the clipboard
     */
    private static void windowsCopyToClip(String password, int clearTime) {
        try {
            StringSelection ss = new StringSelection(password);
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            cb.setContents(ss, null);

            Thread.sleep(clearTime * 1000);

            ss = new StringSelection("");
            cb.setContents(ss, null);
        }
        catch (Exception e) {
            System.exit(-1);
        }
    }

    /**
     * Linux specific copy to clipboard function
     * Requires xclip
     * @param password password to copy
     * @param clearTime how long password exists on the clipboard
     */
    private static void linuxCopyToClip(String password, int clearTime) {
        try {
            Runtime run = Runtime.getRuntime();
            run.exec(new String[]{"sh", "-c", "echo '" + password + "' | xclip -selection clipboard"});
            Thread.sleep(clearTime * 1000);
            run.exec(new String[]{"sh", "-c", "echo " + "" + " | xclip -selection clipboard"});
        }
        catch (Exception e)
        {
            System.exit(1);
        }
    }

    /**
     * MacOS specific copy to clipboard function
     * @param password password to copy
     * @param clearTime how long password exists on the clipboard
     */
    private static void macCopyToClip(String password, int clearTime) { // UNTESTED
        try {
            Runtime run = Runtime.getRuntime();
            run.exec(new String[]{"sh", "-c", "echo '" + password + "' | pbcopy"});
            Thread.sleep(clearTime * 1000);
            run.exec(new String[]{"sh", "-c", "echo " + "" + " | pbcopy"});
        }
        catch (Exception e)
        {
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        copyToClip(this.charBuffer, this.clearTime);
    }

    private enum OS {
        LINUX,
        WINDOWS,
        MAC
    }
}