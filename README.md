## Dr. Karnaugh's Lab

Sophomore Computer Science 2 class project, a 3D puzzle game made my myself and 2 fellow Students in Java.

You explore Dr. Karnaugh's spooky lab, solving binary logic puzzles with his mysterious equipment to get to the next level.  But don't dawdle or his evil killer bunny Stan will get you!

![image](https://github.com/user-attachments/assets/b44aa59e-7f04-454e-a08b-b16cfa80f549)

https://github.com/user-attachments/assets/0e7b535e-b7ae-40f9-b259-24e1225dfbb4

Based on JOGL 1.1.1, which I had to dig up on the web archive.

The game is playable as is.

Things that don't work:
- MP3 playback is not working.  Presumably based on depends.
- Theres some general Jank with mouselook, framerates, display modes, etc that could be fixed

### Run instructions:
From the root directory you can just run the JAR
java -cp "lib/*;KarnaughGame.jar" "-Djava.library.path=lib" drk.menu.Menu

### Build Instructions:
You can also build the jar again from source:

javac -cp "lib/*" -sourcepath .\ -d .\bin .\drk\circuit\*.java 

javac -cp "lib/*" -sourcepath .\ -d .\bin .\drk\game\KarnaughGame.java

jar cfm KarnaughGame.jar MANIFEST.MF -C bin . -C . .
