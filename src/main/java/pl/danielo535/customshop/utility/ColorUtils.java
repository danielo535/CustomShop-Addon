package pl.danielo535.customshop.utility;

import pl.danielo535.customwallet.utils.TextUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ColorUtils {
    public static String colorText(String message) {
        return TextUtils.format(message);
    }

    public static List<String> colorList(List<String> messages) {
        return messages.stream().map(ColorUtils::colorText).collect(Collectors.toList());
    }
}