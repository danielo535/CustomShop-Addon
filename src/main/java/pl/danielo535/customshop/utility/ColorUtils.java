package pl.danielo535.customshop.utility;

import lombok.experimental.UtilityClass;
import me.kodysimpson.simpapi.colors.ColorTranslator;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ColorUtils {
    public static String colorText(String message) {
        return ColorTranslator.translateColorCodes(message);
    }

    public static List<String> colorList(List<String> messages) {
        return messages.stream().map(ColorUtils::colorText).collect(Collectors.toList());
    }
}