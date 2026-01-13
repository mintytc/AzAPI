package me.mintytc.azapi.api.output.format.replacer;

/**
 * @since 1.0.0-R0.1
 *
 */
public enum Closure {

	AMPERSAND('&', '&'),
	ARROWS('<', '>'),
	ARROWS_INVERTED('>', '<'),
	AT_SIGN('@', null),
	BRACKETS_CURLY('{', '}'),
	BRACKETS_ROUND('(', ')'),
	BRACKETS_SQUARE('[', ']'),
	DOLLAR('$', '$'),
	EXCLAMATION('!', null),
	HASHTAG('#', null),
	NONE(null, null),
	PERCENT('%', '%'),
	PERIOD('.', null),
	QUESTION_MARK('?', null),
	SEMI_COLON(null, ';');

	public final Character head, tail;

	Closure(Character head, Character tail) {
		this.head = head;
		this.tail = tail;
	}
}
