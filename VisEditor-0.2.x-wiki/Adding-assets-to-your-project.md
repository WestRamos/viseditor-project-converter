Adding assets to your project is very simple. To add assets open your project folder then go to `vis/assets` directory. You can also press Explore button in VisEditor (next to search bar in content explorer) and then go to parent folder.

You will see multiple directories there.

* **gfx** - add your textures here, `.png` and `.jpg` are supported
* **atlas** - add your texture atlases here, each atlas must coexist of two files: `.atlas` and `.png` file. To create texture atlases you should use this free [tool](https://github.com/libgdx/libgdx/wiki/Texture-packer). Note: you should use texture atlases when you already have them and don't want to depackage them. VisEditor automatically packages assets from gfx folder into texture atlas.
* **font** - TrueType fonts, file with `.ttf` format
* **bmpfont** - Bitmap fonts files, each font must have two files: `.fnt` and `.png`. You can use free tool [Hiero](https://github.com/libgdx/libgdx/wiki/Hiero) to generate bitmap fonts, distance field fonts are supported
* **music** - music files in `.ogg`, `.wav` or `.mp3` format (music is always streamed from file on disk)
* **sound** - sound files in `.ogg`, `.wav` or `.mp3` format (sound is always loaded to memory)
* **scene** - your scene files, created from VisEditor
* **particle** - particles files, each particle effect coexist of minimum two files: .p file and one or more .png images. You can use this free [tool](https://github.com/libgdx/libgdx/wiki/2D-Particle-Editor) to create particle effects.
* **shader** - (since VisEditor 0.2.1) shader files, each shader must have two files: `.frag` (Fragment Shader) file and `.vert` (Vertex Shader) file
* **spine** - Spine animations in either JSON or binary format. Each animation must have three files: `.atlas` file, `.png` file and `.json` (text) or `.skel` (binary), don't forget to enable Spine plugin first (File -> Settings -> Plugin tick `spine` and restart VisEditor.
* **spriter** - Spriter animations in SCML file. Each animation in separate folder. Follow this guide for more information: [[Using Spriter|Spriter]]

