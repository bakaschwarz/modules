package org.yabue.baka.modules.ModulB;

import org.yabue.baka.modules.AbstractModule;
import org.yabue.baka.modules.ModuleInvocationException;
import org.yabue.baka.modules.ModuleManager;

/**
 * TXT
 *
 * @author Yannick Bülter
 * @version 1.0
 */
public class ModulB extends AbstractModule {
    @Override
    protected void initialize() {

    }

    protected String callA() throws ModuleInvocationException {
        return ModuleManager.invoke("ModulA", "answerB");
    }
}
