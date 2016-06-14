# Modules

The idea behind this is, that you can have different modules in your project which are independent from each other and can be turned on and off using a simple configuration file.

## The configuration file

The configuration looks like this:
  
    ModuleA
    ModuleB
    ModuleC

The modules will be loaded in order from the `ModuleManager` class after calling the `initialize` method.

## Module Structure

The structure is fairly simple. You make a package which holds your modules, let's call it `com.idea.modules`. Then for a module called `Sample` it would look like this:

    com/idea/modules
    |-- Sample
        |-- Sample.java

You get the idea. You create a package with the name of your module inside your modules package and a Java file with the exact same name. This is your main class for the module.

You can only call the main class from the `ModuleManager`, but you can add as many custom classes as you wish inside the module.

## Usage

Modules may not call each other directly. They have to use the `ModuleManager.invoke` method for that.
If you want to call `method1` in `ModuleA` from `ModuleB` that returns a String, then it would look like this:

    String string = ModuleManager.invoke("ModuleA", "method1");

It is possible to pass a variable number of arguments to the `invoke` method:

    Integer sum = ModuleManager.invoke("Math", "sum", 1, 2);
    Double sqrt = ModuleManager.invoke("Math", "sqrt", 9.0);

Please note, that you can basically call any method in a module if you wish to do so. The `ModuleManager` can and will successfully call `private`, `protected` and package local methods.

In other contexts this is behavior which might not be wanted. But in the case of this structure it makes sense. You can declare every method in the modules main class `private` so that it can not be used without the `ModuleManager`.
