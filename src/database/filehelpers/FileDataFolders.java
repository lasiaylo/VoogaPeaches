package database.filehelpers;

import util.PropertiesReader;

/**
 * Enum defining the different folders in the data folder of the
 * project where user saved files can be read and written from by the
 * FileDataManager class
 *
 * @author Walker Willetts
 */
public enum FileDataFolders {
    USER_IMAGES("images/user_images"),
    IMAGES("images"),
    SCRIPTS("scripts");

    private final String filepath;

    FileDataFolders(String path) {
        this.filepath = PropertiesReader.path("db_files") + path + "/";
    }

    /**
     * @return A {@code String} representing the path of the folder within the project
     */
    String path() {
        return filepath;
    }
}