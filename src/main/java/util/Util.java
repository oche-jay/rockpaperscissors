package util;

import com.google.common.io.Resources;
import model.Game;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;

public class Util {

    public static Game loadGameFromConfig(String configFile){

        try {
           return new Yaml().loadAs(Resources.getResource(configFile).openStream(), Game.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
