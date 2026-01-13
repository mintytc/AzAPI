package me.mintytc.azapi.api.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @since 1.0.0-R0.1
 *
 */
public class UString {

	private UString() {
	}

	/* ---------------------------------------------------
	 * Basic Checks
	 * --------------------------------------------------- */

	/**
	 * Checks whether a string is null or empty.
	 *
	 * @param s the string to check
	 * @return true if null or empty
	 * @since 1.0.0-R0.1
	 */
	public static boolean isEmpty(java.lang.String s) {
		return s == null || s.isEmpty();
	}

	/**
	 * Checks whether a string is null, empty, or whitespace.
	 *
	 * @param s the string to check
	 * @return true if blank
	 * @since 1.0.0-R0.1
	 */
	public static boolean isBlank(java.lang.String s) {
		return s == null || s.trim().isEmpty();
	}

	/**
	 * Returns the string if it's not blank, otherwise returns a fallback.
	 *
	 * @param s        the main value
	 * @param fallback fallback if blank
	 * @return result string
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String orDefault(java.lang.String s, java.lang.String fallback) {
		return isBlank(s) ? fallback : s;
	}

	/* ---------------------------------------------------
	 * Capitalisation
	 * --------------------------------------------------- */

	/**
	 * Capitalises the first character of a string safely.
	 *
	 * @param s the input
	 * @return capitalised string
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String capitalise(java.lang.String s) {
		if (isEmpty(s)) return s;
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	/**
	 * Lowercases the first character of a string safely.
	 *
	 * @param s the input
	 * @return decapitalised string
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String decapitalise(java.lang.String s) {
		if (isEmpty(s)) return s;
		return Character.toLowerCase(s.charAt(0)) + s.substring(1);
	}

	/* ---------------------------------------------------
	 * Joining & Repeating
	 * --------------------------------------------------- */

	/**
	 * Joins a collection of items using a delimiter.
	 *
	 * @param items     items to join
	 * @param delimiter between items
	 * @return joined string
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String join(Collection<?> items, java.lang.String delimiter) {
		return items.stream().map(java.lang.String::valueOf).collect(Collectors.joining(delimiter));
	}

	/**
	 * Centers the given text within a string of the specified length.
	 * If the text is longer than the specified length, it is returned unchanged.
	 * Pads with spaces on both sides to make the text appear centered.
	 *
	 * @param str    The text to center.
	 * @param length The total length of the resulting string, including padding.
	 * @return A string with the text centered and padded with spaces.
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String center(java.lang.String str, int length) {
		if (str == null) return null;
		str = str.trim();
		int padding = Math.max(0, length - str.length());
		int padStart = padding / 2;
		int padEnd = padding - padStart;
		return repeat(" ", padStart) + str + repeat(" ", padEnd);
	}

	/**
	 * Centers the given text within a string of the specified length.
	 * If the text is longer than the specified length, it is returned unchanged.
	 * Pads with spaces on both sides to make the text appear centered.
	 *
	 * @param str    The text to center.
	 * @param outer  The text surrounding the centered text.
	 * @param length The total length of the resulting string, including padding.
	 * @return A string with the text centered and padded with spaces.
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String center(java.lang.String str, java.lang.String outer, int length) {
		if (str == null) return null;
		int padding = Math.max(0, length - str.length());
		int padStart = padding / 2;
		int padEnd = padding - padStart;
		return repeat(outer, padStart) + str + repeat(outer, padEnd);
	}

	/**
	 * Repeats a string n times.
	 *
	 * @param s     the string
	 * @param times number of repetitions
	 * @return repeated string
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String repeat(java.lang.String s, int times) {
		if (times <= 0) return "";
		StringBuilder finalString = new StringBuilder();
		for (int i = 0; i < times; i++) {
			finalString.append(s);
		}
		return finalString.toString();
	}

	/* ---------------------------------------------------
	 * Padding
	 * --------------------------------------------------- */

	/**
	 * Pads a string on the left.
	 *
	 * @param s      the input
	 * @param length desired length
	 * @param pad    padding character
	 * @return padded string
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String padLeft(java.lang.String s, int length, char pad) {
		if (s.length() >= length) return s;
		return repeat(java.lang.String.valueOf(pad), length - s.length()) + s;
	}

	/**
	 * Pads a string on the right.
	 *
	 * @param s      the input
	 * @param length desired length
	 * @param pad    padding character
	 * @return padded string
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String padRight(java.lang.String s, int length, char pad) {
		if (s.length() >= length) return s;
		return s + repeat(java.lang.String.valueOf(pad), length - s.length());
	}

	/* ---------------------------------------------------
	 * Safe Operations
	 * --------------------------------------------------- */

	/**
	 * Performs a substring operation without throwing errors.
	 *
	 * @param s     input string
	 * @param start start index
	 * @param end   end index
	 * @return safe substring
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String safeSubstring(java.lang.String s, int start, int end) {
		if (s == null) return null;
		int safeStart = Math.max(0, Math.min(start, s.length()));
		int safeEnd = Math.max(safeStart, Math.min(end, s.length()));
		return s.substring(safeStart, safeEnd);
	}

	/**
	 * Removes all spaces from a string.
	 *
	 * @param s input
	 * @return string without spaces
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String removeSpaces(java.lang.String s) {
		return s == null ? null : s.replace(" ", "");
	}

	/* ---------------------------------------------------
	 * Regex Helpers
	 * --------------------------------------------------- */

	/**
	 * Checks if a string fully matches a regex.
	 *
	 * @param s     input
	 * @param regex regex
	 * @return true if match
	 * @since 1.0.0-R0.1
	 */
	public static boolean matches(java.lang.String s, java.lang.String regex) {
		return s != null && s.matches(regex);
	}

	/**
	 * Finds the first regex match in a string.
	 *
	 * @param s     input
	 * @param regex regex
	 * @return first match or null
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String findFirst(java.lang.String s, java.lang.String regex) {
		if (s == null) return null;
		Matcher m = Pattern.compile(regex).matcher(s);
		return m.find() ? m.group() : null;
	}

	/**
	 * Replaces all regex matches with a replacement.
	 *
	 * @param s           input
	 * @param regex       regex
	 * @param replacement replacement
	 * @return replaced string
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String replaceRegex(java.lang.String s, java.lang.String regex, java.lang.String replacement) {
		if (s == null) return null;
		return s.replaceAll(regex, replacement);
	}

	/* ---------------------------------------------------
	 * Case Converters
	 * --------------------------------------------------- */

	/**
	 * Converts a string to snake_case.
	 *
	 * @param s input
	 * @return snake_case
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String toSnakeCase(java.lang.String s) {
		if (isBlank(s)) return s;
		return s.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase().replace(" ", "_");
	}

	/**
	 * Converts a string to kebab-case.
	 *
	 * @param s input
	 * @return kebab-case
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String toKebabCase(java.lang.String s) {
		if (isBlank(s)) return s;
		return s.replaceAll("([a-z])([A-Z])", "$1-$2").toLowerCase().replace(" ", "-");
	}

	/**
	 * Converts a string to camelCase.
	 *
	 * @param s input
	 * @return camelCase
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String toCamelCase(java.lang.String s) {
		if (isBlank(s)) return s;

		java.lang.String[] parts = s.toLowerCase().split("[ _-]+");
		if (parts.length == 0) return "";

		StringBuilder builder = new StringBuilder(parts[0]);
		for (int i = 1; i < parts.length; i++) {
			builder.append(capitalise(parts[i]));
		}
		return builder.toString();
	}

	/**
	 * Converts a string to PascalCase.
	 *
	 * @param s input
	 * @return PascalCase
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String toPascalCase(java.lang.String s) {
		if (isBlank(s)) return s;

		java.lang.String[] parts = s.toLowerCase().split("[ _-]+");
		StringBuilder builder = new StringBuilder();

		for (java.lang.String part : parts) {
			builder.append(capitalise(part));
		}
		return builder.toString();
	}

	/* ---------------------------------------------------
	 * Extra Utilities
	 * --------------------------------------------------- */

	/**
	 * Returns true if the string contains digits only.
	 *
	 * @param s input
	 * @return true if numeric
	 * @since 1.0.0-R0.1
	 */
	public static boolean isNumeric(java.lang.String s) {
		return s != null && s.matches("\\d+");
	}

	/**
	 * Counts occurrences of a character in a string.
	 *
	 * @param s input
	 * @param c character to count
	 * @return number of occurrences
	 * @since 1.0.0-R0.1
	 */
	public static int countChar(java.lang.String s, char c) {
		if (s == null) return 0;
		int count = 0;
		for (char ch : s.toCharArray()) {
			if (ch == c) count++;
		}
		return count;
	}

	/**
	 * Reverses a string.
	 *
	 * @param s input
	 * @return reversed string
	 * @since 1.0.0-R0.1
	 */
	public static java.lang.String reverse(java.lang.String s) {
		return s == null ? null : new StringBuilder(s).reverse().toString();
	}
}