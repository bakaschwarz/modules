package org.yabue.baka.modules;

/**
 * This exception is thrown, when a module could not be loaded.
 *
 * @author Yannick Bülter
 * @version 1.0
 */
public class ModuleLoadException extends Exception {
    public ModuleLoadException(String text, Throwable cause) {
        super(text, cause);
    }
}
