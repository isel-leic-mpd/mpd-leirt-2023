package isel.mpd.mvc.view.configdrawers;

import isel.mpd.mvc.app.App;

import java.util.Map;

public class ConfigFactory {
    private static Map<String, ConfigDrawer> configMap = Map.ofEntries (
        Map.entry(App.SHAPE_CMD_RECT, new RectConfig())
        // other entries...
    );

    public static ConfigDrawer createConfigDrawer(String name) {
        if (name.equals(App.SHAPE_CMD_RECT)) return new RectConfig();
        // other drawers creations
        return null;
    }

    // alternative using a ConfigDrawer map by name
    public static ConfigDrawer createConfigDrawer2(String name) {
        return configMap.get(name);
    }

}
