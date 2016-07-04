package org.yabue.baka.modules;

import org.apache.commons.io.FileUtils;

import java.io.File;
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

    private static String modulesPackagePath;

    /**
     * These names will be added into a newly generated module configuration file.
     */
    private static String[] defaultModules;

    private static File moduleFile;

    private static final HashMap<String, BakaModuleInterface> loadedModules;

    /**
     * This method must be called to enable the usage of modules.
     * You need to provide the location of modules and the location of the configuration file.
     *
     * @param modulePackage The package name, that contains modules.
     * @param moduleConfigurationLocation The location of the module configuration. Will be created if not existent.
     * @throws ModuleLoadException
     */
    public static void initialize(String modulePackage, File moduleConfigurationLocation) throws ModuleLoadException {
        moduleFile = moduleConfigurationLocation;
        modulesPackagePath = modulePackage;
        ModuleManager.defaultModules = new String[0];
        loadModules();
    }

    /**
     * This method must be called to enable the usage of modules.
     * You need to provide the location of modules and the location of the configuration file.
     *
     * @param modulePackage The package name, that contains modules.
     * @param moduleConfigurationLocation The location of the module configuration. Will be created if not existent.
     * @param defaultModules The default modules to be generated in case a configuration file is not found.
     * @throws ModuleLoadException
     */
    public static void initialize(String modulePackage, File moduleConfigurationLocation, String... defaultModules)
            throws ModuleLoadException {
        moduleFile = moduleConfigurationLocation;
        modulesPackagePath = modulePackage;
        ModuleManager.defaultModules = defaultModules;
        loadModules();
    }

    /**
     * Loads all modules which are enabled in the module.conf.
     *
     * @throws ModuleLoadException
     */
    private static void loadModules() throws ModuleLoadException {
        try {
            if (!moduleFile.exists()) {
                FileUtils.touch(moduleFile);
                for(String defaultModule : defaultModules) {
                    FileUtils.write(moduleFile, defaultModule + "\n", true);
                }
            }
            List<String> modules = Files.readAllLines(moduleFile.toPath());
            for (String module : modules) {
                BakaModuleInterface bakaModuleInterface =
                        (BakaModuleInterface) Class.forName(modulesPackagePath + "." + module + "." + module).newInstance();
                loadedModules.put(module, bakaModuleInterface);
                bakaModuleInterface.initialize();
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
            BakaModuleInterface module = loadedModules.get(moduleS);
            if(module != null) {
                Class<?>[] argArray = Arrays.stream(arguments).map(Object::getClass).toArray((IntFunction<Class<?>[]>) Class[]::new);
                Method method = loadedModules.get(moduleS).getClass().getDeclaredMethod(methodS, argArray);
                method.setAccessible(true);
                return (R) method.invoke(module, arguments);
            }
        } catch (Exception e) {
            if(loadedModules.get(moduleS).showFail())
                throw new ModuleInvocationException("Method could not be executed.", e);
        }
        if(loadedModules.get(moduleS).showFail())
            throw new ModuleInvocationException(String.format("The module %s is not loaded or does not exist!", moduleS), new Throwable());
        return null;
    }

    static {
        loadedModules = new HashMap<>();
    }
}

