package org.yabue.baka.modules.ModulA;

import org.yabue.baka.modules.AbstractModule;
import org.yabue.baka.modules.ModuleInvocationException;
import org.yabue.baka.modules.ModuleManager;

/**
 * TXT
 *
 * @author Yannick Bülter
 * @version 1.0
 */
public class ModulA extends AbstractModule {
    @Override
    protected void initialize() {
    }

    private String start() throws ModuleInvocationException {
        return ModuleManager.invoke("ModulB", "callA");
    }

    public String answerB() {
        return "Hello World!";
    }
}
