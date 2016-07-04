package org.yabue.baka.modules.ModulA;

import org.yabue.baka.modules.BakaModuleInterface;
import org.yabue.baka.modules.ModuleInvocationException;
import org.yabue.baka.modules.ModuleManager;

/**
 * This is a test module.
 *
 * @author Yannick Bülter
 * @version 1.0
 */
public class ModulA implements BakaModuleInterface {
    @Override
    public void initialize() {
    }

    @Override
    public boolean showFail() {
        return false;
    }

    private String start() throws ModuleInvocationException {
        return ModuleManager.invoke("ModulB", "callA");
    }

    public String answerB() {
        return "Hello World!";
    }
}
