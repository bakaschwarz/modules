package org.yabue.baka.modules.ModulB;

import org.yabue.baka.modules.BakaModuleInterface;
import org.yabue.baka.modules.ModuleInvocationException;
import org.yabue.baka.modules.ModuleManager;

/**
 * This is a test module.
 *
 * @author Yannick Bülter
 * @version 1.0
 */
public class ModulB implements BakaModuleInterface {
    @Override
    public void initialize() {
    }

    protected String callA() throws ModuleInvocationException {
        return ModuleManager.invoke("ModulA", "answerB");
    }
}
