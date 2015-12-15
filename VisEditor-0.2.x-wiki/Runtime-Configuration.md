`RuntimeConfiguration` objects allows you to change various settings how runtime works.

Each `VisAssetManager` have their own `RuntimeConfiguration`. To change default configuration you must do:
```java
VisAssetManager manager = new VisAssetManager(batch);
manager.getSceneLoader().setRuntimeConfig(configuration);
```

For example to enable box2d debug renderer which is useful for debugging physics:
```java
VisAssetManager manager = new VisAssetManager(batch);

RuntimeConfiguration configuration = new RuntimeConfiguration();
configuration.useBox2dDebugRenderer = true;
manager.getSceneLoader().setRuntimeConfig(configuration);
```

Each setting of RuntimeConfiguration is javadoc documented, refer to that if you would like to know more about each setting.

