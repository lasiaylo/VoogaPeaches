Uses Cases
===============
1. Create Entity Object
    - Primary Actors: Entity
    - User clicks on a button on the interface to create a new entity object. An instance of the object entity is created and available for the user to place.
2. Placing Entity Object
    - Primary Actors:Entity, Map
    - User clicks on the GUI map editor in order place the Entity at that position. The position of the Entity is saved.
3. Objects collide with one another
    - Primary Actors: Collider, CollisionManager
    - CollisionManager checks whether any two Collider objects are colliding
4. Adding Image to Entity
    - Primary Actor: Entity, SpriteScript
    - Entity adds SpriteScript to its list of Components. SpriteScript returns a user defined image.
5. Adding new behavior to an Entity
    - Primary Actor: Entity, Script
    - User creates a new script from either a new combination of preset behaviors or totally new functionality and adds it to the entity 
6. Moving the object
    - Primary Actor: Entity, MoveScript, ControlScript
    - Player inputs a command mapped to a move command. The ControlScript interprets this as a movement and subsequently calls the movescript to move the object
7. Player tries to move past wall
    - Primary Actor: Entity, Entity, CollisionScript
    - Player tries to move past wall but is stopped by the interpretation of CollisionManager, which notes that the wall and the player have collided
    - Player gets tag of wall (wall1), which determines that its velocity should be set to zero.
8. Player tries to move into building
    - Primary Actor: Entity, Entity, CollisionScript
    - The CollisionScript of the building will change the scene
9. Player enters minigame
    - Primary Actor: minigame engine, Entity
    - Player collide with a special entity, the scene changes and a new minigame instance is created and run. 
10. Moving Entity into a different layer to be rendered
    - Primary Actor: Entity, Layer, Map
    - User clicks on a button to toggle through the layers, placing the Entity on the desired layer in the desired position on that layer’s map.
11. Player beats the game
    - Primary Actor: Entity, GameLogic
    - Player beats the game. Win screen read locally/leaderboard read from database gets displayed, play a sound, option to send a tweet via Twitter button, continue playing/free roam, etc.
12. Player dies
    - Primary Actor: Entity, GameLogic
    - Player dies in game. Loss screen is displayed, play a sound, option to restart from last saved position/checkmark.
13. View inventory
    - Primary Actor: Entity
    - Player presses a key/clicks on a button to view inventory. Game is paused. Screen switches to display the player’s inventory.
14. Move entities inside the camera panel when playing in the authoring environment ( this is kind of in the realm of gaming(player side) environment)
    - Primary Actor: Camera Panel
    - Imageview representation of the GameObject change their locations inside the gridpane(camera panel) based on the information sent from engine
15. Load saved game to workspace for authoring
    - Primary Actor: I/O, Menu Panel, Camera Panel, Properties panel
    - Load data from the online server to a local copy and import the data into workspace, show image in the camera panel, show properties of player in properties panel. 
16. Change location of camera inside the minimap for authoring
    - Primary Actor: Camera Panel
    - When user clicks in the minimap, the camera panel will get the clicking location inside the minimap and scale it up, then the specific location inside the map would be displayed in the camera panel. 
17. Adding a GameObject into the screen of the authoring environment
    - Primary Actor: Library Panel, Camera Panel and Properties Panel
    - User selects a type of GameObject to create from the defaults/self-made objects in the Library Panel, which appears in the middle of the Camera Panel and updates the Properties Panel to show what current properties for that object are and is ready to be edited
18. Resizing the GameObject in the authoring environment
    - Primary Actor: Camera Panel and Properties Panel
    - User can click and drag a corner/side of the object to size both dimensions or one. This dynamically also updates the x and y properties in the Properties Panel
19. Specify the collision of a GameObject with others within the authoring environment
    - Primary Actor: Properties Panel and Library Panel
    - User can select a script from the Library Panel/file explorer to associate with an object for a specific task (eg. collisions/contact) within the Properties Panel
20. Rearranging the display of panels in the authoring environment
    - Primary Actor: Screen and I/O
    - The user can rearrange these panels (menu, camera, properties, library) and save the rearranged panels to have preferences that can be uploaded.
21. Create an in game Menu Bar within the authoring environment
    - Primary Actor: OverlayPanel
    - The user uses the overlay panel to edit the default menu-bar, and adds or removes tabs as they wish. The data will be saved, and the engine will read the correct menu bar items on launching the game.
22. Load image from folder to library for authoring 
    - Primary Actor: ResourcePanel
    - User click a button labeled “New Resource” in the ResourcePanel, which prompts a file explorer. The selected image will then be listed in the ResourcePanel.
23. Stop and run the game in authoring environment
    - Primary Actor: PlayerPanel, Engine
    - The player clicks pause/run buttons in the panel next to the embedded player, sending a signal through the message board to the engine. 
    - The engine ceases updating, freezing the game. Controls are reallocated to the authoring environment for selecting objects, etc.
24. Save built game to database in form of JSON
    - Primary Actor: DatabaseConnector and JSONCreator
    - When a user has a version of the game that they are currently satisfied with then they should be able to save the game to an online database that holds on to the game
25. Load game from online database by retrieving JSON 
    - Primary Actor: DatabaseConnector and JSONConverter 
    - When a user wants to load a game from the database then they should be able to pull the game’s JSON file and then our program should convert this file into a working game for the User to play 
26. Allow user to post a tweet online through a button in the game 
    - Primary Actor: WebExtensionView, TwitterExtensionView
    - When creating the game the user’s will be able to add a button the screen that, when clicked, will open up a popup window that allows the user to make a new tweet about the game
27. Allow user to post a facebook post online through a button in the game 
    - Primary Actor: WebExtensionView, FacebookExtensionView
    - When creating the game the user’s will be able to add a button the screen that, when clicked, will open up a popup window that allows the user to make a new facebook post about the game 
28. Allow the storage of all custom created objects to an online database that is viewable by all
    - Primary Actor: DatabaseConnector, Entity 
    - User’s will be able to create a new custom entity for the game that will then be saved and uploaded to the database along with the appropriate files for the entity. Then other user’s that are loading or editing the game will be able to get the appropriate custom objects
29. Allow multiple players on a single game
    - Primary Actor: DatabaseConnector, Engine
    - Information about changes in the state of the game can be sent using the Database methods to receive state changes, that can be interpreted by the engine allowing players to play in a single game
30. Allow changes user to make changes in the authoring environment that can be easily tracked (potentially allows for multiple editors on the same game)
    - Primary Actor: DatabaseConnector, Camera Panel, Properties panel
    - The information for changes in the authoring environment can be stored or sent by some sort of database that holds information about a specific authoring environment. This can allow for basic changes on two different environments for the same project
31. Accessing different predefined behaviors for different games will come from the database
    - Primary Actor: DatabaseConnector, Properties panel
    - When accessing the preset tiles or scripts in the properties panel, the information is then acquired using methods from the Dat
33.
    - Primary Actor: 
    -
34.
    - Primary Actor: 
    - 
35.
    - Primary Actor: 
    -
36.
    - Primary Actor: 
    -
37. 
    - Primary Actor: 
    -
38. 
    - Primary Actor: 
    -
39.
    - Primary Actor: 
    -
40.
    - Primary Actor: 
    -
