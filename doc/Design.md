# App Studio Design Document Ghost Game #
=====================================
##### Vincent - 10384081 - <vincent.erich@live.nl> #####
##### Design Document Version: 1.0 #####

This design document captures any and all design decisions that I have made before implementing the Ghost application. This document builds on the sketches and information provided in the README (../README.md), so please check out that file first.

<b>Note</b>: This design document will display many similarites with the README file. However, besides sketches of each screen, this document will also capture the relations between the screens (activities/controllers, classes, methods, etc.). 

### Controllers/Activities (with menu items) ###

The hand-drawn sketches below are adopted from the README. Each sketch depicts a 'screen' in the application. The name above each sketch is the name of the cotroller/activity that will manage the screen.

<b>Note</b>: The controller/activity 'GhostGame' is (for now) the only controller/activity with menu items.  

* <b>MainMenu</b> (1)

![](/Images\ Project\ Proposal/Sketch-1.jpg?raw=true "Sketch 'MainMenu' controller/activity.")

* <b>Highscores</b> (2)

![](/Images\ Project\ Proposal/Sketch-2.jpg?raw=true "Sketch 'Highscores' controller/activity.")

* <b>HowToPlay</b> (3)

![](/Images\ Project\ Proposal/Sketch-3.jpg?raw=true "Sketch 'HowToPlay' controller/activity.")

* <b>Settings</b> (4)

![](/Images\ Project\ Proposal/Sketch-4.jpg?raw=true "Sketch 'Settings' controller/activity.")

* <b>PlayerSelect</b> (5)

![](/Images\ Project\ Proposal/Sketch-5.jpg?raw=true "Sketch 'PlayerSelect' controller/activity.")

* <b>GhostGame</b> (includes menu items, 6 + 7)

![](/Images\ Project\ Proposal/Sketch-6.jpg?raw=true "Sketch 'GhostGame' controller/activity.")

![](/Images\ Project\ Proposal/Sketch-7.jpg?raw=true "Sketch menu 'GhostGame' controller/activity.")

* <b>Results</b> (8)

![](/Images\ Project\ Proposal/Sketch-8.jpg?raw=true "Sketch 'Results' controller/activity.")

### Model classes ###

The hand-drawn sketches below show four model classes ('Players', 'HighscoresData', 'Preferences', and 'GameState') with their properties and methods.

![](/Images\ Design\ Document/Sketch-1.jpg?raw=true "Sketch model classes 'Players', 'HighscoresData', and 'Preferences'.")

![](/Images\ Design\ Document/Sketch-2.jpg?raw=true "Sketch model class 'GameState'.")

<img src="https://github.com/vincent-erich/App-studio-ghost-project/blob/master/doc/Images%20Design%20Document/Sketch_1.jpg"/>

### API's ###

It is likely that some API's will be used while implementing the application (i.e., API's for Dutch and English dictionaries). 
