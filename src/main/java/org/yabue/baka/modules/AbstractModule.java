package org.yabue.baka.modules;

/**
 * All modules need to implement this class.
 *
 * @author Yannick Bülter
 * @version 1.0
 */
public abstract class AbstractModule {

    /**
     * This method is automatically called when a module is loaded from the {@link ModuleManager}
     */
    protected abstract void initialize();
}