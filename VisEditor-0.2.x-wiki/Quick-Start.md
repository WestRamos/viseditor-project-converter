This guide will teach you the basic of using VisEditor

## Setup
After downloading and unpacking VisEditor simply double click the jar file. Alternatively you can run editor from command line:
```
java -jar /path/to/VisEditor-x.y.z.jar
```

Java 8 is required.

Tip for 4K display users: if editor UI is too small, launch with argument: `--scale-ui` or open **Settings** dialog and enable UI scaling option located in **Experimental** section.

## Project creation

After loading you will see startup page, to create new project simply click `New project` on start page or use menu bar: `File -> New project...`.
On the right you will see @VisEditor Twitter feed, make sure to read it some times, you will get there information about news and updates.  
[[images/quick_start/01.png]]

After first launch you will see in upper right corner, dialog like this:  
[[images/quick_start/01_spine.png]]  
Spine is a skeleton animation system for 2D games, feel free to enable it you are planning to use Spine. If you dismiss this dialog
you can do it later from `Settings` window under tab `Plugins`.

Next you will see new project dialog. Here you can choose project type, currently two project types are supported: LibGDX and Generic.
The Generic project is like the name says generic, it does not depend on any game framework or engine. For this project you just
have to specify Project Root and Output folder. The project root is main project directory, inside it your assets and scene will be stored.
Output folder is like the names says directory where to you project will be exported.  
[[images/quick_start/02.png]]  
The other project type is LibGDX. For this project type you have to specify Gradle root directory. If you are using default 
project from LibGDX gdx-setup then the source and assets folder are already set so you don't have to change them. During project creation your assets from `root/android/asset` folder will be moved to `root/vis/assets`.

Currently there aren't any differences between those two project types, but in the future LibGDX project would have more
features focused for LibGDX development for example launching your game straight from the editor.

[[images/quick_start/03.png]]  


Press `Create`, you project will be automatically opened.

## First scene

Let's create out first scene, to do so use menu: `Scene -> New Scene...` you will see dialog like this:  
[[images/quick_start/05.png]]  
Here you can set scene properties, type in scene name and press `Create`. Editor will ask you to open your new scene, press
yes. (Tip: to open any other scene, in content browser navigate to scene directory and double click `.scene` file.). All
scene files must be located in /scene subdirectory. Now with te scene loaded we need asset that we can work with, for the 
example when use Kenney Tappy Plane free assets, download them from [here](http://opengameart.org/sites/default/files/TappyPlane.zip).

To pan camera in scene press and hold right mouse button. To change zoom use mouse wheel.

In editor content browser navigate to gfx directory and press button with folder icon to open current folder in your system explorer.  
[[images/quick_start/07.png]]  
From unpacked TappyPlane zip file copy all files from PNG folder into gfx folder so it will look like this:  
[[images/quick_start/08.png]]  
Go back to the editor, you will see that all your copied assets are now visible in content browser and ready to use:  
[[images/quick_start/09.png]]  
To add asset to scene simply drag and drop them into scene view. Your sprite will appear in scene view, after you click it you will see
entity properties window. Here you can adjust entity scale, position, rotation, tint and so on. Depending on selected entity you may see different
set on options. Tip: in any of the number filed in properties dialog you can press Ctrl or Shift with Plus or Minus key to quickly change values by 1 or 10.  
[[images/quick_start/10.png]]  

Drag the background onto scene. You will notice that our scene size is too big.
[[images/quick_start/11.png]]  
We can quickly change that, use menu `Scene -> Scene Settings' and change width and height to 8 and 4.8.  
[[images/quick_start/12.png]]
[[images/quick_start/13.png]]  
Now our background fits perfectly!
[[images/quick_start/14.png]]  
Finish the rest of scene, add and arrange entities however you want. Tip: to change z-order of entity, select it and press
Page Up or Page Down. Entity must overlap other entity in order this to work. You can select multiple entities by holding and
dragging left mouse button.

After you are done you must export your project.
[[images/quick_start/15.png]]  

Export progress window will appear. If you are using LibGDX based project you can follow to next part about loading your scene from code.
For generic project type you will have to write your own loader. [VisEditor LibGDX runtime](https://github.com/kotcrab/VisEditor/tree/master/Runtime) may be a good start.
[[images/quick_start/16.png]]  

## Loading scene from code (LibGDX only)
Before continuing setup your [[LibGDX project | Libgdx Project Setup]].
Loading exported scene is really easy.
```java
public class ExampleApplication extends ApplicationAdapter {
	SpriteBatch batch;
	VisAssetManager manager;

	Scene scene;

	@Override
	public void create () {
		batch = new SpriteBatch();

		//VisAssetManager is a extension of standard LibGDX AssetManager, 
		//it makes loading scene easier
		manager = new VisAssetManager(batch);

		//here we load our scene, this is a blocking operation and it 
		//will block your app until loading entire scene is finished
		scene = manager.loadSceneNow("scene/test.scene");
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		scene.render();
	}

	@Override
	public void resize(int width, int height) {
		scene.resize(width, height);
	}

	@Override
	public void dispose () {
		//here we must release used resources, Scene itself is 
		//disposable but because we loaded it via AssetsManager will dispose it for us
		batch.dispose();
		manager.dispose();
	}
}
```
In the above example we use `manager.loadSceneNow(...)`, but this isn't the only way you can load scene, you can load is asynchronously and display progress bar, refer to AssetManager guide for more infomration.

#### Adding own managers and systems into entity engine
```java
SceneParameter parameter = new SceneParameter();
parameter.managers.add(new GameManager());
parameter.systems.add(new GameSystem());
parameter.systems.add(new OtherGameSystem());
//...

scene = manager.loadSceneNow("scene/game.scene", parameter);
```

`SceneParameter` passed into asset manager allows you to add your own managers and systems into entity engine.

### Enabling FreeType
You are using FreeType fonts (TrueType ttf font files) you must enable it before loading scene:
```java
manager = new VisAssetManager(batch);
//...
manager.enableFreeType(new FreeTypeFontProvider());
```
Remember that you must have [FreeType Gradle dependency](https://github.com/libgdx/libgdx/wiki/Dependency-management-with-Gradle#freetypefont-gradle) in your project.

### Enabling Spine for VisRuntime
If you are using Spine in your game then you must enable Spine support. First add vis-runtime-spine.jar to your project dependencies, follow [this guide](https://github.com/kotcrab/VisEditor/wiki/LibGDX-Project-Setup#spine-runtime-setup) for that, then simply register Spine support in VisAssetManager:
```java
manager = new VisAssetManager(batch);
//...
manager.registerSupport(new SpineSupport());
```

## Next steps

* [Learn more about Artemis and ECS frameworks](https://github.com/junkdog/artemis-odb/wiki/Introduction-to-Entity-Systems)
* Check out source of [VisEditor SuperJumper demo](https://github.com/kotcrab/VisEditor-SuperJumper-Demo)
* Start reading libGDX runtime [[documentation|LibGDX Runtime Introduction]]
* Read runtime [Javadoc](http://www.javadoc.io/doc/com.kotcrab.vis/vis-runtime/)