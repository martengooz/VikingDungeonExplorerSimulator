# Viking Dungeon Explorer Simulator
A 2D dungeon crawler game for the course INDA14.

### Uses: 
* Slick2D library (build 237)
* LWJGL 2.9.2
* JRE 1.7+ 

### Installation
1. Install Slick2D with LWJGL
2. Build JAR file (not runnable)
  1. In Eclipse, File > Export
  2. Choose Java > JAR file, click Next
  3. Select "VikingDungeonExplorer"
  4. Select "Export generated class files and resources"
  5. Select "Compress the contents of the JAR file"
  6. Click Finish
3. Build runnable JAR file 
  1. Download Jarsplice and open it (http://ninjacave.com/jarsplice).
  2. Add .jar file from 2)
  3. Add slick.jar & lwjgl.jar from Slick2D libs.
  4. Add OS natives from lwjgl > native > [OS] 
  5. Create Fat Jar
4. Done 

### Troubleshooting
**Game crashes on startup**

Open the runnable jarfile in an archive managager and move the "res" folder to the same folder as the jar file.  
