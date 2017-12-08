package util;

import database.User;
import database.jsonhelpers.JSONDataFolders;
import database.jsonhelpers.JSONDataManager;
import database.jsonhelpers.JSONHelper;
import database.jsonhelpers.JSONToObjectConverter;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void testUser() {
        String userName = "user1";
        User newUser = new User(userName);
        String theme = "dark.css";
        newUser.setTheme(theme);
        newUser.setWorkspace("LeftCameraWorkspace");
//        Make User JSON File
        JSONDataManager manager = new JSONDataManager(JSONDataFolders.USER_SETTINGS);
        manager.writeJSONFile(userName, JSONHelper.JSONForObject(newUser));

//        Read User JSON File
        JSONObject blueprint = manager.readJSONFile(userName);
        JSONToObjectConverter<User> converter = new JSONToObjectConverter<>(User.class);
        User user = converter.createObjectFromJSON(User.class, blueprint);

        assertEquals("Test Theme Saving", theme, user.getThemeName());
    }

}
