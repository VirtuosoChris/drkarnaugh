## Dr. Karnaugh's Lab (2008)

Sophomore Computer Science 2 class project, a 3D puzzle game made my myself and 2 fellow Students in Java.

You explore Dr. Karnaugh's spooky lab, solving binary logic puzzles with his mysterious equipment to get to the next level.  But don't dawdle or his evil killer bunny Stan will get you!

At the time we were influenced by Portal, stuff we were learning in class (like Karnaugh maps and truth tables), the vorpal bunny from Monty Python, the Yeti from Ski Free, [Rocky's Boots](https://en.wikipedia.org/wiki/Rocky%27s_Boots), as well as just a general sense of 80's Apple II / home computer game kitsch.

![image](https://github.com/user-attachments/assets/b44aa59e-7f04-454e-a08b-b16cfa80f549)

![image](https://github.com/user-attachments/assets/e29e1116-1aef-4b9c-b930-f8d1390b7a62)

![image](https://github.com/user-attachments/assets/9c40d9f1-1025-4186-97de-a48c3bdd9478)

Based on JOGL 1.1.1, which I had to dig up on the web archive.

The game is playable as is.

Things that don't work:
- MP3 playback is not working.  Presumably based on depends.
- Theres some general Jank with mouselook, framerates, display modes, etc that could be fixed
- This version of the game is asset light, but I absolutely think it would be worth hooking up a stanford bunny renderer (static mesh and rotation) to chase the player instead of just the placeholder pink ball I used for time.
- A "hologram" of the gate symbol floating over the maze components would be a good quality of life improvement
- Replace brick-sphere placeholder rendering for journals with a custom model as well and hook it up to some dummy lore writeups
- Could render the wires going into and out of the components while we're at it.  That was also something we cut for time.  Right now you just sort of have to read the tooltips and keep stuff in your head.

At this point I think the game would be eminently playable and fun as is.  Beyond that I'd just leave this build alone and remake this concept in a modern engine like Virtuoso (maybe for VR?) if I wanted to take it further.

### Run instructions:
From the root directory you can just run the JAR
java -cp "lib/*;KarnaughGame.jar" "-Djava.library.path=lib" drk.menu.Menu

### Build Instructions:
You can also build the jar again from source:

javac -cp "lib/*" -sourcepath .\ -d .\bin .\drk\circuit\*.java 

javac -cp "lib/*" -sourcepath .\ -d .\bin .\drk\game\KarnaughGame.java

jar cfm KarnaughGame.jar MANIFEST.MF -C bin . -C . .

This project was interesting for the three of us as students due to:
- Its 3D graphics with real-time lighting
- Maze Generation
- Nav-graph table for the Bunny to traverse the rooms and then chase the player when they're in the same room
- Binary Logic component System
- Complete set of game features
- etc!



https://github.com/user-attachments/assets/0e7b535e-b7ae-40f9-b259-24e1225dfbb4
