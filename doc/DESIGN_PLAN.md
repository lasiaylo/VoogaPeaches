# DESIGN_PLAN

## Introduction

Our video game engine will be designed to allow users to create various games in the 2D world exploring RPG genre. Our primary goals are to allow flexible extension of the authoring environment to allow video game creates to have a rich availability of features to add to their game, such as spotify integration and multiplayer chat. The genre we support is characterized by a flat plane that scrolls in two dimensions. Every game will be created with a front-ground level that contains players, enemies, and player elements such as projectiles. The background level contains elements in the environment such as the ground (grass, etc.) and buildings, if any. The camera can follow the player, or be arbitrarily implemented, to allow various ways for the player of the game to view the game.

The game creator will be created in five parts: the authoring environment, player, engine, data, and message board (PubSub). The former four components all communicate through the message board, following the publish-subscribe design pattern. The authoring environment consists of a list of panels that can be opened, moved around, and expanded as the user desires. The player contains a screen that receives data from the board, and an input module that publishes data to the board. The data module will be able to save authoring environment as well as game creation states, and potentially data within an actual playthrough of a game.


## Overview

* Authoring
    * The authoring environment consists of a menu bar and a workspace. The menu bar is fully customizable and can loads menus both from an XML file and can be passed a menu to display programmatically. A workspace describes the layout of various areas of the screen that hold the camera to view the game, as well as all of the panels used for various purposes, such as viewing an entity's properties. All of the panels communicate through an implementation of the Publish Subscribe pattern (called PubSub), which also sends updated information to the engine.
    * The enviroment has an embedded Player, but it is updated by the engine directly.
* Player (the main game view. Receives rendered image from the Camera)
    * Input (takes in and processes user input and sends commands to the engine)
    * If embedded in the authoring environment, it has additional buttons to pause the engine cycles, reset the game to default states, view certain layers in the camera, etc.
* Engine
    * All game objects in the engine are an Entity object, which has attached scripts that define its behavior, and a state that holds information and changes over time.
    * The camera tracks player position and displays a bounded portion of the map of the current game. It renders only part of the map at any given time, and displays that section of the map.
    * A map is defined by multiple layers, starting with a background layer that holds tiles such as grass, etc. The user can define as many layers as they desire, and entities are added to a specific layer.
* Data


## User Interface
* The basis of the authoring environment is the workspace. It defines the layout of panels on the screen, as well as where the embedded camera is displayed.
* The camera is a representation of the game. This aspect is where you would add different types of blocks or add characters into your game and serves as a good baseline for what the actual player will see when they play the game. In fact, actual playing of the game is actually using the camera but without the rest of the authoring environment attached.
* TabPanes make up the rest of the authoring environment. These panel areas can hold any given panel, like a library panel, properties, social media, etc. These panels provide the resources to create a game for the user. For scripts.example, the library panel contains all the possible elements you can add to the camera like water squares, grass, etc. The user clicks the type of block they want to add and clicks the square on the camera where they want that new square to be placed.

User Customizability:
* Workspace: The user can choose a workspace as well as the settings for that workspace (Such as the division of its tabpanes on the screen). The location of each of the panels also dynamically changeable and saved for each user.
* Themes: There will be multiple types of themes that allow the user to choose the cool color designs they might want to use. This affects the look and feel of the authoring enviroment.

Here is an scripts.example of a workspace a user could decide to use, if it is built:

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
* Create an enemy bot level that multiple players can attack in the same game (multiplayer functionality).

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
