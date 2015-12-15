VisEditor stores backup of your scenes. There are two types of backup: 
* last sucessful save
* first save since editor lanuch. 

Backup are stored inside `project root/vis/modules/.sceneBackup` directory.
Directory structure there is the same as your `project root/vis/assets/scene` folder. 

Files with `.bak` extension are last sucessful save and files with extension `.firstSaveBak` are first sucesful save since editor lanuch. 

To restore backup simply copy backup file to `project root/vis/assets/scene` folder and remove backup extension.