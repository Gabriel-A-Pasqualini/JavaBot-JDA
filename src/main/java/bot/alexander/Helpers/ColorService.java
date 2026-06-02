package bot.alexander.Helpers;

import java.awt.Color;

public class ColorService {

    public static int convertColor(String color){
        return Color.decode(color).getRGB();
    }    
}
