package org.yabue.baka.modules;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.IntFunction;

/**
 * This class manages the loading and accessing of modules.
 * Interaction between modules shall only be done through this module.
 *
 * @author Yannick Bülter
 * @version 1.0
 */
public class ModuleManager {

    private static String modulesPackagePath = ModuleManager.class.getPackage().getName();

    private static final String[] DEFAULT_MODULES = {"Core"};

    private static File moduleFile;

    private static final HashMap<String, AbstractModule> loadedModules;

    public static void initialize(String modulePackage, File moduleConfigurationLocation) throws ModuleLoadException {
        moduleFile = moduleConfigurationLocation;
        modulesPackagePath = modulePackage;
        loadModules();
    }

    /**
     * Loads all modules which are enabled in the module.conf.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void loadModules() throws ModuleLoadException {
        try {
            if (!moduleFile.exists()) {
                FileUtils.touch(moduleFile);
                for(String defaultModule : DEFAULT_MODULES) {
                    FileUtils.write(moduleFile, defaultModule + "\n", true);
                }
            }
            List<String> modules = Files.readAllLines(moduleFile.toPath());
            for (String module : modules) {
                AbstractModule abstractModule =
                        (AbstractModule) Class.forName(modulesPackagePath + "." + module + "." + module).newInstance();
                loadedModules.put(module, abstractModule);
                abstractModule.initialize();
            }
        } catch (Exception e) {
            throw new ModuleLoadException("An error orccured while loading the modules.", e);
        }
    }

    /**
     * This method enables the interaction between modules.
     *
     * @param moduleS The module, whose method shall be called.
     * @param methodS The method to be called.
     * @param arguments Possible arguments.
     * @param <R> Possible return value type.
     * @return Returns the value which {@param methodS} returns, if any.
     * @throws ModuleInvocationException
     */
    @SuppressWarnings("unchecked")
    public static <R> R invoke(String moduleS, String methodS, Object... arguments) throws ModuleInvocationException {
        try {
            AbstractModule module = loadedModules.get(moduleS);
            if(module != null) {
                Class<?>[] argArray = Arrays.stream(arguments).map(Object::getClass).toArray((IntFunction<Class<?>[]>) Class[]::new);
                Method method = loadedModules.get(moduleS).getClass().getDeclaredMethod(methodS, argArray);
                method.setAccessible(true);
                return (R) method.invoke(module, arguments);
            }
        } catch (Exception e) {
            throw new ModuleInvocationException("Method could not be executed.", e);
        }
        throw new ModuleInvocationException(String.format("The module %s is not loaded or does not exist!", moduleS), new Throwable());
    }

    static {
        loadedModules = new HashMap<>();
    }
}

