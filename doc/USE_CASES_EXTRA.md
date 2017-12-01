* Bouncing collisions
    * Primary Actor: Entity
    * Allow two entities who collide together to reverse their direction without stalling
* Entity Properties
    * Primary Actor: Entity
    * Allow an entity to hold properties like health, etc.
* Refactor Events
    * Primary Actor: Event
    * Refactor user inputs, collisions, etc under a single Event that can then be processed by Scripts
* Finite State Machine Logic
    * Primary Actors: Logic, Transition, State
    * Test to make sure that the FSM works according to expectations
* Create Entity from Event
    * Primary Actor: Event, Screen
    * Be able to access the Screen/Level from an Event and create a dialogue box, etc
* Create Event from Event
    * Primary Actor: Event
    * Create the ability to create an event from within an event
* Level Switching
    * Primary Actor: Entity
    * Be able to switch scenes/levels (think PokeCenter in Pokemon)
* MiniMap for Location Change
    * Primary Actor: MiniMap Panel
    * Be able pass target location from authoring to engine to change the location of camera in map
* Map Cache
    * Primary Actor: Map, Camera
    * Make a canvas that is slightly larger than the camera viewport that act as the cache for camera
    * Only imageview inside this canvas range would be updated at each engine loop
* Entity Authoring Transform
    * Primary Actor: Render
    * Make Render in the front end draggable and scalable
* Camera Movement
    * Primary Actor: Camera
    * Make camera/frame moving based on either the player position or some user input
* Creating Separate Gaming Window
    * Primary Actor: Camera
    * Use existing camera panel to create a new gaming window that is very similar to current camera panel
* User Input for Library Panel
    * Primary Actor: Library Panel
    * Allow user to add new folder to library panel and input customized vector images
* Entity Animated Image
    * Primary Actor: Render, imagescript
    * Use a series of imagescript set inputstream command to change the render for a animated effect.
* User Accounts for Developers
    * Primary Actor: AuthoringInitializer
    * Prompt user for username on program startup, then load their personalized settings from local files, or database
* Add password protection to user accounts
    * Primary Actor: AuthoringInitializer
    * Prompt user  for both username and password
    * Run SHA256 on password input, test against hash locally/on remote. Add bypass incase any of us forget our passwords...
* Develop workspace theme inside Authoring
    * Primary Actor: StyleSettingsPanel
    * User opens the panel, which loads current style settings and allows user to edit and save changes, after some validation.
* Ability to print to console on events (for debugging / testing)
    * Primary Actor: ConsolePanel
    * User can attach print scripts to entities or events that print specified variables / text to the console panel while the game is running in the authoring environment.
