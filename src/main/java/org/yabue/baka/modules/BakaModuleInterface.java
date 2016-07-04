package org.yabue.baka.modules;

/**
 * All modules need to implement this class.
 * It is possible to define a method {@code defaultModuleCall} which acts as a default method to be called by the
 * {@link ModuleManager} when {@link ModuleManager#invokeDefault(String, Object...)} or
 * {@link ModuleManager#def(String, Object...)} are being called.
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
    default void initialize() {
        System.out.println(String.format(
                "%s does not implement a custom initialization method!",
                this.getClass().toString()
        ));
    }
}