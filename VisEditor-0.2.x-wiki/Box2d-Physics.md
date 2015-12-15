Box2d Physics
============

Start by enabling physics, on menu bar press `Scene -> Physics Settings`. Then select **Physics Enabled** checkbox, set gravity if you want (eg. **Gravity Y**: -9.8) then press **Save**.

To use physics for entity select it and then press **Add Component** button in **Entity Properties** dialog. Add **PolygonComponent** and **PhysicsPropertiesComponent**. In properties dialog you will be able to set various Box2d properties (friction, density, body type, fixed rotation etc.) that will be used to create physics body.

Now you have to edit entity polygon that will be used for collisions. To do so select polygon edition tool (triangle button on the toolbar or press `F2` key), next in left down corner you will be **Polygon Tool** settings. Press **Set From Bounds** to set default polygon shape. You can edit polygon now, drag points and add them by clicking on polygon line. To delete point select it and press delete key.

That's it, now if you export your project and run it, sprites with those two components will have physics enabled. Don't forget to add libGDX Box2d extensions if you haven't done that already!
