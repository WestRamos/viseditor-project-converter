Each entity that is rendered on screen have at least 3 components:
* RenderableComponent - stores Z index
* LayerComponent - stores layer ID
* Component holding content to render: eg. SpriteComponent, TextComponent, ParticleComponent etc.

```java
world.createEntity().edit()
	.add(new RenderableComponent(0)) //z index
	.add(new LayerComponent(0)) //layer id
	.add(new SpriteComponent(new Sprite(texture)))
```

### Using Layers ID properly
Changing layers order in VisEditor causes layer ID to change. If you are using multiple layers you shouldn't use constant int numbers but instead retrieve int id during runtime using layer name. For example:
```
int layerId = scene.getLayerDataByName("background").id;
```