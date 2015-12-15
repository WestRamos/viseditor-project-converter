In order to load exported project from VisEditor you need to add VisRuntime dependency to your LibGDX project.
Please refer to [LibGDX documentaion](https://github.com/libgdx/libgdx/wiki/Dependency-management-with-Gradle) if you don't know how to mange dependencies with Gradle. Alternatively JAR can be downloaded from [Maven repository](https://search.maven.org/#search|ga|1|g%3A%22com.kotcrab.vis%22%20AND%20a%3A%22vis-runtime%22). If you are creating new project, you can use gdx-setup to automatically add VisRuntime for you. (press 'Show Third Party Extension' button)

### Manual Gradle setup: 
Open build.gradle in project root.
In ``ext`` section under ``allprojects`` add:
```grovy
visRuntimeVersion = '0.2.5'
```

**Core dependency**
```groovy
compile "com.kotcrab.vis:vis-runtime:$visRuntimeVersion"
```

**HTML dependency**
```groovy
compile "net.onedaybeard.artemis:artemis-odb-gwt:0.13.1"
compile "net.onedaybeard.artemis:artemis-odb-gwt:0.13.1:sources"
compile "net.onedaybeard.artemis:artemis-odb:0.13.1:sources"
compile "com.kotcrab.vis:vis-runtime-gwt:$visRuntimeVersion"
compile "com.kotcrab.vis:vis-runtime-gwt:$visRuntimeVersion:sources"
compile "com.kotcrab.vis:vis-runtime:$visRuntimeVersion:sources"
```

Refresh Gradle dependencies.

``GdxDefinition.gwt.xml`` and ``GdxDefinitionSuperdev.gwt.xml`` (only if you are using HTML)
```xml
<inherits name='com.kotcrab.vis.vis-runtime' />
```

In same files declare your packages with components and systems like here:
```xml
<extend-configuration-property name="artemis.reflect.include" value="com.kotcrab.vis.demo.jumper.component" />
<extend-configuration-property name="artemis.reflect.include" value="com.kotcrab.vis.demo.jumper.system" />
<extend-configuration-property name="artemis.reflect.include" value="com.kotcrab.vis.demo.jumper.manager" />
```

**Important:** You need runtime 0.2.6-SNAPSHOT if you want to use GWT.

### LibGDX compatibility
Table bellows shows what version of LibGDX you need for your version of VisRuntime. It also shows what
Artemis-odb version is using VisRuntime.

| VisRuntime         | Artemis-odb | LibGDX              |
| ------------------ | ----------- | ------------------- |
| 0.2.0              | 0.10.2      | 1.6.4               |
| 0.2.1              | 0.10.2      | 1.6.4               |
| 0.2.2              | 0.10.2      | 1.6.4               |
| 0.2.3              | 0.10.2      | 1.6.4               |
| 0.2.4              | 0.10.2      | 1.6.4               |
| 0.2.5              | 0.13.1      | 1.7.0               |
| 0.2.6-SNAPSHOT              | 0.13.1      | 1.7.0               |

Using not matching versions may cause runtime exceptions.

## FreeType and Box2d
If you want to use FreeType fonts you must add FreeType libGDX extension. If you want to use Box2d physics you must add Box2d libGDX extension.

## Spine Runtime Setup
Unfortunately due to Spine license, the Vis Spine Runtime cannot be distributed as Gradle dependency. This dependency must be added as JAR dependency.

1. <s>Navigate to VisEditor installation directory then `plugins/spine/lib`</s> Use this [jar](http://dl.kotcrab.com/stuff/vis-runtime-spine.jar) for now.
2. Copy `vis-runtime-spine.jar` to the directory `Gradle project root/libs` (create libs folder)
3. Alter your `build.gradle` script in the root directory as follows:
 * Locate the `:core` stub where the core project's dependencies are declared
 * Add the line in dependencies
 ```grooovy
 compile fileTree(dir: '../libs', include: '*.jar')
 ```
 * Your script should now look a little like this:
 ```groovy
project(":core") {
   ...

    dependencies {
        ...
        compile fileTree(dir: '../libs', include: '*.jar')
    }
}
 ```
 * Locate the `:android` stub and add the same dependency again:
 ```groovy
project(":android") {
   ...

    dependencies {
        ...
        compile fileTree(dir: '../libs', include: '*.jar')
    }
}
```

Refresh your Gradle project dependencies, either on command line or from IDE.
