package org.yabue.baka.modules;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test the basic functionality of modules.
 *
 * @author Yannick Bülter
 * @version 1.0
 */
public class ModuleTest {

    @Before
    public void setUp() throws IOException, ModuleLoadException {
        File moduleConf = new File("testConf.conf");
        if(!moduleConf.exists()) {
            FileUtils.touch(moduleConf);
            FileUtils.write(moduleConf, "ModulA\n", true);
            FileUtils.write(moduleConf, "ModulB\n", true);
        }
        ModuleManager.initialize("org.yabue.baka.modules", moduleConf);
    }

    @Test
    public void testModules() throws ModuleInvocationException {
        assertEquals("Hello World!", ModuleManager.invoke("ModulA", "start"));
        assertNull(ModuleManager.invoke("ModulA", "falseMethod"));

    }

    @After
    public void cleanUP() {
        File moduleConf = new File("testConf.conf");
        if(moduleConf.exists()) {
            assertTrue(FileUtils.deleteQuietly(moduleConf));
        }
    }
}