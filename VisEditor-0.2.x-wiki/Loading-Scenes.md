Loading scene is done by using `VisAssetManager` which extends standard libGDX `AssetManager`. Using asset manager makes loading scenes much easier. You must pass SpriteBatch object to asset manager constructor.

```java
VisAssetManager manager = new VisAssetManager(batch);
Scene scene = manager.loadSceneNow("scene/test.scene");
```

That's it all required to load scene. After loading you must call two methods of Scene class: `render()` and `resize(width, height)`

In most situations you will want to add your own managers and systems into entity engine, those allows you to create your game logic. Refer to [artemis-odb wiki](https://github.com/junkdog/artemis-odb/wiki) on how managers and systems works. To add them create `SceneParameter` object, add your systems and managers and pass it to `loadSceneNow` method.

```java
SceneParameter parameter = new SceneParameter();
parameter.managers.add(new TestManager());
parameter.systems.add(new TestSystem());

VisAssetManager manager = new VisAssetManager(batch);
Scene scene = manager.loadSceneNow("scene/test.scene", parameter);
```

It's very useful to enable detailed error logging on manager, it will allow you to find errors in scene loading:
```java
manager.getLogger().setLevel(Logger.ERROR);
```

You can also enable other extensions:
```java
manager.enableFreeType(new FreeTypeFontProvider());
manager.registerSupport(new SpineSupport());
```

Enabling FreeType requires you to have FreeType LibGDX extension in your project. For Spine support you must have vis-runtime-spine dependency. Refer to [[LibGDX Project Setup]] page for information how to add them.

### Full example
```java
public class MyGame extends ApplicationAdapter {
	SpriteBatch batch;
	VisAssetManager manager;

	Scene scene;

	@Override
	public void create () {
		batch = new SpriteBatch();

		//creates new asset manager
		manager = new VisAssetManager(batch);

		//enable detailed error logging
		manager.getLogger().setLevel(Logger.ERROR);

		//enable freetpye (must have freetype libgdx extension)
		manager.enableFreeType(new FreeTypeFontProvider());

		//enable Spine support (must have Spine runtime in project)
		manager.registerSupport(new SpineSupport());

		//allows to change default runtime settings
		//see wiki page 'RuntimeConfiguration' for more details
		RuntimeConfiguration configuration = new RuntimeConfiguration();
		//configuration.useBox2dDebugRenderer = true;
		manager.getSceneLoader().setRuntimeConfig(configuration);

		//allows to add own managers and systems into artemis-odb
		SceneParameter parameter = new SceneParameter();
		parameter.managers.add(new TestManager());
		parameter.systems.add(new TestSystem());

		//loads scene
		scene = manager.loadSceneNow("scene/test.scene", parameter);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		scene.render();
	}

	@Override
	public void resize (int width, int height) {
		scene.resize(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose(); //asset manager will also dispose scene
	}
}
```