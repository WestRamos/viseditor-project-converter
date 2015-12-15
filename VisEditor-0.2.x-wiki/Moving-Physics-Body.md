Box2d physics body can be obtained from `PhysicsComponent`. Then you can use standard box2d methods to set velocity, apply forces, impulses etc. If Entity has `SpriteComponent`, it will be automatically updated by VisRuntime, this behavior can be disabled in [[Runtime Configuration]]

Example of moving physics body:
```java
@Wire
public class PlayerSystem extends BaseSystem implements AfterSceneInit {
		//assigned by artemis, see @Wire annotation at the top
        ComponentMapper<SpriteComponent> spriteCm;
        ComponentMapper<PhysicsComponent> physicsCm;
        VisIDManager idManager;
 
        Sprite sprite;
        Body body;
       
        @Override
        public void afterSceneInit() {
                Entity player = idManager.get("player");
                sprite = spriteCm.get(player).sprite;
                body = physicsCm.get(player).body;
        }
 
        @Override
        protected void processSystem() {
                float x = body.getLinearVelocity().x;
                float y = body.getLinearVelocity().y;
                float desiredVel = 0;
               
                if (Gdx.input.isKeyPressed(Keys.LEFT)) {
                        desiredVel = -20;
                        sprite.setFlip(true, false);
                } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
                        desiredVel = 20;
                        sprite.setFlip(false, false);
                }
 
                if (Gdx.input.isKeyJustPressed(Keys.UP)) {
                        float impulse = body.getMass() * 200;
                        body.applyForce(0, impulse, body.getWorldCenter().x, body.getWorldCenter().y, true);
                }
				
                float velChange = desiredVel - x;
                float impulse = body.getMass() * velChange;
                body.applyForce(impulse, 0, body.getWorldCenter().x, body.getWorldCenter().y, true);
        }
}
```