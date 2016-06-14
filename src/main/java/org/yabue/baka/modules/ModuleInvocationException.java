package org.yabue.baka.modules;

/**
 * Thrown when a method could not be found or called.
 *
 * @author Yannick Bülter
 * @version 1.0
 */
public class ModuleInvocationException extends Exception {
    public ModuleInvocationException(String text, Throwable cause) {
        super(text, cause);
    }
}
