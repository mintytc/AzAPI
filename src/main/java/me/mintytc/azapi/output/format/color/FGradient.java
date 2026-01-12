package me.mintytc.azapi.output.format.color;

import com.google.common.base.Preconditions;
import me.mintytc.azapi.events.format.EvtFGradientApplied;
import me.mintytc.azapi.output.format.Format;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 1.0.0-R0.1
 *
 */
public class FGradient implements Format {

    public static final FGradient INST = new FGradient();

    private FGradient() {
    }

    @Contract("_ -> new")
    private static @NotNull Color hexToColor(@NotNull String hex) {
        if (hex.startsWith("#")) hex = hex.substring(1);
        if (hex.length() != 6) throw new IllegalArgumentException("Invalid hex color: " + hex);

        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return new Color(r, g, b);
    }

    @Override
    public String apply(OfflinePlayer player, String input) {

        Pattern pattern = Pattern.compile("<gradient:#([0-9a-fA-F]{6}):#([0-9a-fA-F]{6})>(.*?)</gradient>");
        Matcher matcher = pattern.matcher(input);

        StringBuffer buffer = new StringBuffer();
        FGradient gradient = new FGradient();

        while (matcher.find()) {
            EvtFGradientApplied evt = new EvtFGradientApplied(input, matcher);
            if (evt.isCancelled()) continue;

            Color from = hexToColor(matcher.group(1));
            Color to = hexToColor(matcher.group(2));
            String text = matcher.group(3);
            String gradientText = gradient.rgbGradient(text, from, to, new LinearInterpolator()) + ChatColor.RESET;
            matcher.appendReplacement(buffer, gradientText);
        }
        matcher.appendTail(buffer);
        input = buffer.toString();

        return input;
    }

    // mode == true: starts of "slow" and becomes "faster", see the orange curve
    // mode == false: starts of "fast" and becomes "slower", see the yellow curve
    @Contract(pure = true)
    private double @NotNull [] quadratic(double from, double to, int max, boolean mode) {
        final double[] results = new double[max];
        if (mode) {
            double a = (to - from) / (max * max);
            for (int i = 0; i < results.length; i++) {
                results[i] = a * i * i + from;
            }
        } else {
            double a = (from - to) / (max * max);
            double b = -2 * a * max;
            for (int i = 0; i < results.length; i++) {
                results[i] = a * i * i + b * i + from;
            }

        }
        return results;
    }

    public String rgbGradient(String str, Color from, Color to, Interpolator interpolator) {

        // interpolate each component separately
        final double[] red = interpolator.interpolate(from.getRed(), to.getRed(), str.length());
        final double[] green = interpolator.interpolate(from.getGreen(), to.getGreen(), str.length());
        final double[] blue = interpolator.interpolate(from.getBlue(), to.getBlue(), str.length());

        final StringBuilder builder = new StringBuilder();

        // create a string that matches the input-string but has
        // the different color applied to each char
        for (int i = 0; i < str.length(); i++) {
            builder.append(colorToHex(new Color(
                            (int) Math.round(red[i]),
                            (int) Math.round(green[i]),
                            (int) Math.round(blue[i]))))
                    .append(str.charAt(i));
        }

        return builder.toString();
    }

    public String hsvGradient(String str, Color from, Color to, Interpolator interpolator) {
        // returns a float-array where hsv[0] = hue, hsv[1] = saturation, hsv[2] = value/brightness
        final float[] hsvFrom = Color.RGBtoHSB(from.getRed(), from.getGreen(), from.getBlue(), null);
        final float[] hsvTo = Color.RGBtoHSB(to.getRed(), to.getGreen(), to.getBlue(), null);

        final double[] h = interpolator.interpolate(hsvFrom[0], hsvTo[0], str.length());
        final double[] s = interpolator.interpolate(hsvFrom[1], hsvTo[1], str.length());
        final double[] v = interpolator.interpolate(hsvFrom[2], hsvTo[2], str.length());

        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            builder.append(Color.getHSBColor((float) h[i], (float) s[i], (float) v[i])).append(str.charAt(i));
        }
        return builder.toString();
    }

    public String multiRgbGradient(String str, Color[] colors, double @Nullable [] portions, Interpolator interpolator) {
        final double[] p;
        if (portions == null) {
            p = new double[colors.length - 1];
            Arrays.fill(p, 1 / (double) p.length);
        } else {
            p = portions;
        }

        Preconditions.checkArgument(colors.length >= 2);
        Preconditions.checkArgument(p.length == colors.length - 1);

        final StringBuilder builder = new StringBuilder();
        int strIndex = 0;

        for (int i = 0; i < colors.length - 1; i++) {
            builder.append(rgbGradient(
                    str.substring(strIndex, strIndex + (int) (p[i] * str.length())),
                    colors[i],
                    colors[i + 1],
                    interpolator));
            strIndex += (int) (p[i] * str.length());
        }
        return builder.toString();
    }

    public String multiHsvQuadraticGradient(String str, boolean first) {
        final StringBuilder builder = new StringBuilder();

        builder.append(hsvGradient(
                str.substring(0, (int) (0.2 * str.length())),
                Color.RED,
                Color.GREEN,
                (from, to, max) -> this.quadratic(from, to, max, first)
        ));

        for (int i = (int) (0.2 * str.length()); i < (int) (0.8 * str.length()); i++) {
            builder.append(Color.GREEN).append(str.charAt(i));
        }

        builder.append(hsvGradient(
                str.substring((int) (0.8 * str.length())),
                Color.GREEN,
                Color.RED,
                (from, to, max) -> this.quadratic(from, to, max, !first)
        ));

        return builder.toString();

    }

    private String colorToHex(Color color) {
        return String.format("§x§%x§%x§%x§%x§%x§%x",
                (color.getRed() >> 4) & 0xF, color.getRed() & 0xF,
                (color.getGreen() >> 4) & 0xF, color.getGreen() & 0xF,
                (color.getBlue() >> 4) & 0xF, color.getBlue() & 0xF
        );
    }

    @FunctionalInterface
    interface Interpolator {
        double[] interpolate(double from, double to, int max);
    }

    public static class LinearInterpolator implements Interpolator {
        public double[] interpolate(double from, double to, int max) {
            final double[] res = new double[max];
            for (int i = 0; i < max; i++) {
                res[i] = from + i * ((to - from) / (max - 1));
            }
            return res;
        }
    }
}
