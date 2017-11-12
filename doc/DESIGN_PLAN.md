# DESIGN_PLAN

## Introduction

Our video game engine will be designed to allow users to create various games in the 2D world exploring RPG genre. Our primary goals are to allow flexible extension of the authoring environment to allow video game creates to have a rich availability of features to add to their game, such as spotify integration and multiplayer chat. The genre we support is characterized by a flat plane that scrolls in two dimensions. Every game will be created with a front-ground level that contains players, enemies, and player elements such as projectiles. The background level contains elements in the environment such as the ground (grass, etc.) and buildings, if any. The camera can follow the player, or be arbitrarily implemented, to allow various ways for the player of the game to view the game. 

The game creator will be created in five parts: the authoring environment, player, engine, data, and message board (PubSub). The former four components all communicate through the message board, following the publish-subscribe design pattern. The authoring environment consists of a list of panels that can be opened, moved around, and expanded as the user desires. The player contains a screen that receives data from the board, and an input module that publishes data to the board. The data module will be able to save authoring environment as well as game creation states, and potentially data within an actual playthrough of a game. 


## Overview

* Authoring
    * The authoring environment consists of a series of “areas” on the Screen, each of which can be filled with various tabbed Panels. All of the panels communicate through a single object (tentatively the AuthoringController), which also sends updated information to the engine via a PubSub channel.
    * The enviroment has an embedded Player, but it is updated by the engine directly.
* Player (the main game view. Receives rendered image from the Camera)
    * Input (takes in and processes user input and sends commands to the engine)
* Engine
    * Camera (tracks player position and provides a bounding box for renderer)
    * Layer
    * Renderer (keeps track of the base layer and GameObjects and renders objects within bounding box provided by Camera)
* PubSub (acts as a communication center for all of the inner modules of the system)
* Data


## User Interface
- There will be separate windows for gaming environment and authoring environment: user will create and edit the game in authoring environment while playing the game in gaming environment.
- Inside the authoring environment, the window would be divided into three(or four) main areas. While the camera panel, where user can see and manipulate game scene and the location of GameObject, would always stay in one area, other two areas are flexible in terms of the panel they host. 
- Currently, we are planning to provide properties panel, library panel and menu panel. Properties panel would allow user to change the properties of any selected GameObject. Library panel would display default library for GameObject or library loaded by the user to the authoring environment. Visual representation of each GameObject would be shown inside this panel, and an instance of the GameObject would be added to camera panel when user clicks on it. Menu panel would provide user more options to change properties of the game. 

![UI](UI.png)


## Design Details


## Example Games

Pokemon

* Tile based movement with collision-based events. 
    * Showcase basic functionality in terms of movement, collisions, etc.
* Implementation of “minigames” within the game
* Speech bubbles and texts have displays to show interactions
* Camera holds the player in the center of the screen
* Store information about items, past pokemon, etc.
* Able to declare different tiles as preset tile types in authoring
* Ability to enter different buildings and store information about how the view will change as you enter a building
* Battle with different pokemons with the ability to try to catch pokemon

Realm of the Mad God

* Movement is not limited by set number of shapes, meaning that there is free movement of characters
* Real-time top-down shooter with RPG elements. 
* Shooter with bullets that travel through the game scene
* Camera holds the player in the center of the screen
* Create an enemy bot level that multiple players can attack in the same game

EarthBound

* Free movement of characters
* Party movement/ Party Battle
* Different layout of battle scene (Pseudo -First Person)



## Design Consideration

Rendering method 

* Design Decision: Layers
    * Camera will decide bounds to render
    * Rendered data will be presented in layers; each layer will be rendered together and on top of previous layers
* User will be allowed to draw custom boundaries on their map
    * Pro: More intuitive and more interesting feature
    * Con: Harder to implement
* Engine embedded into authoring interface
* JSON data storage
    * More flexible and more specific to java
    * Not XML
    * Integration with firebase







