`CameraManager` allows you to access camera and change it's position, rotation etc.

### Example
Moving camera using arrows keys
```java
@Wire
public class TestCamera extends BaseSystem {
	//this will be automatically assigned by artemis-odb, see @Wire annotation above
	private CameraManager cameraManager;

	@Override
	protected void processSystem () {
		Vector3 position = cameraManager.getCamera().position;
		float delta = 0.2f;

		//move camera using WSAD keys

		if(Gdx.input.isKeyPressed(Keys.W))
			position.y += delta;
		else if (Gdx.input.isKeyPressed(Keys.S))
			position.y -= delta;

		if(Gdx.input.isKeyPressed(Keys.D))
			position.x += delta;
		else if (Gdx.input.isKeyPressed(Keys.A))
			position.x -= delta;
	}
}
```