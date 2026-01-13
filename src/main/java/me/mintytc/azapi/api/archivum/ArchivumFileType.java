package me.mintytc.azapi.api.archivum;

import org.jetbrains.annotations.NotNull;

/**
 * Abstract class for defining file types in Archivum.
 * Supports exact filenames or extension-based matching.
 *
 * @since 1.0.0-R0.1
 */
public abstract class ArchivumFileType {

	private final String match;
	private final boolean exactMatch;

	/**
	 * @param match      The filename or extension to match
	 * @param exactMatch True if this is an exact filename match, false if it is an extension
	 */
	public ArchivumFileType(@NotNull String match, boolean exactMatch) {
		this.match = match.toLowerCase();
		this.exactMatch = exactMatch;
	}

	/**
	 * Returns the string this type matches (exact name or extension).
	 */
	public String match() {
		return match;
	}

	/**
	 * True if this type matches exact filename.
	 */
	public boolean isExactMatch() {
		return exactMatch;
	}

	/**
	 * Name of the file type.
	 */
	public abstract String name();

	/**
	 * Called when ArchivumFileType is registered/initialized.
	 * Good for preparing defaults or static resources.
	 */
	public abstract void start();

	/**
	 * Called whenever an Archivum of this file type is loaded (e.g., constructor or reload()).
	 */
	public abstract void load(@NotNull Archivum archivum);

	/**
	 * Called whenever an Archivum of this file type is unloaded (e.g., plugin shutdown or manual unload()).
	 */
	public abstract void unload(@NotNull Archivum archivum);

	/**
	 * Called whenever the Archivum file is saved.
	 */
	public abstract void onSave(@NotNull Archivum archivum);

}
