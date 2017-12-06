package database.jsonhelpers;

import util.PropertiesReader;


/**
 * Enum defining the different folders in the data folder of the
 * project where json files can be read and written from by the
 * JSONDataManager class
 */
public enum JSONDataFolders {
    GAMES("games"),
    IMAGES("images"),
    SCRIPTS("scripts"),
    USER_SETTINGS("user_settings"),
    ENTITY_BLUEPRINT("entity_blueprints");

    private final String filepath;

    JSONDataFolders(String path) {
        this.filepath = PropertiesReader.path("db_json") + path + "/";
    }

    /**
     * @return A {@code String} representing the path of the folder within the project
     */
    String path() {
        return filepath;
    }
}
