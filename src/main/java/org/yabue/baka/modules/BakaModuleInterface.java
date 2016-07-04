package org.yabue.baka.modules;

/**
 * All modules need to implement this class.
 *
 * @author Yannick Bülter
 * @version 1.0
 */
public interface BakaModuleInterface {

    /**
     * This value shows the {@link ModuleManager} whether it should throw Exceptions when a command fails.
     * Default is {@code true}.
     */
    default boolean showFail() {
        return true;
    }

    /**
     * This method is automatically called when a module is loaded from the {@link ModuleManager}
     */
    void initialize();
}