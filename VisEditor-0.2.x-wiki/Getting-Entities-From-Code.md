Runtime has two built-in managers that allows to get entities and group by their ID set in VisEditor.

* VisIDManager - allows to get entities by string id
* VisGroupManager - allows to get entities by string id and int id

### Examples

#### VisIDManager
```java
@Wire
public class TestManager extends Manager implements AfterSceneInit {
	//this will be automatically assigned by artemis-odb, see @Wire annotation above
	private VisIDManager idManager;

	@Override
	public void afterSceneInit () {
		//if there is only one entity with this id
		Entity player = idManager.get("player");

		//if there are multiple entities with same id
		Array<Entity> entites = idManager.getMultiple("enemies");
	}
}
```

#### VisGroupManager
```java
@Wire
public class GroupManagerTest extends Manager implements AfterSceneInit {
	//this will be automatically assigned by artemis-odb, see @Wire annotation above
	private VisGroupManager groupManager;

	@Override
	public void afterSceneInit () {
		//get group by string id
		Array<Entity> group = groupManager.get("group");

		//get group by int id (not recommend)
		Array<Entity> anotherGroup = groupManager.get(0);
	}
}
```
`VisGroupManager` can be disabled in [[Runtime Configuration]]