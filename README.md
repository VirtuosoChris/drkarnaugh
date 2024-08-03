## Dr. Karnaugh's Lab

Sophomore Computer Science 2 class project, made my myself and 2 fellow Students in Java.

Based on JOGL 1.1.1, which I had to dig up on the web archive.

The JAR is in the repo and you can just run it with 

Build instructions:
java -cp "lib/*;KarnaughGame.jar" "-Djava.library.path=lib" drk.menu.Menu

Build Instructions:

javac -cp "lib/*" -sourcepath .\ -d .\bin .\drk\circuit\*.java 
javac -cp "lib/*" -sourcepath .\ -d .\bin .\drk\game\KarnaughGame.java
jar cfm KarnaughGame.jar MANIFEST.MF -C bin . -C . .
