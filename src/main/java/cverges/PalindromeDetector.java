package cverges;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Detects palindromes.  A palindrome is a a word or phrase that is the
 * same when read both forward and backward.
 *
 * <p>
 *   While the default settings are set to cover most possible inputs,
 *   the exposed control flags allow you to tweak the functionality for
 *   your particular use case.
 * </p>
 * 
 * <p>
 *   <b>Known errata:</b><br />
 *   <ul>
 *     <li>
 *       Does not work with multibyte characters, such as Unicode emojis,
 *       due to Java's lacking comprehensive support for these characters
 *     </li>
 *   </ul>
 * </p>
 */
@Builder
@Data
@Slf4j
public final class PalindromeDetector {

	/**
	 * When looking for the next character to compare, skip whitespace.
	 * @see {@link StringUtils#isWhitespace()}
	 */
	@Builder.Default
	private boolean skipWhitespace = true;

	/**
	 * Remove diacritics ("accent marks") prior to evaluating the string.
	 * @see {@link StringUtils#stripAccents()}
	 */
	@Builder.Default
	private boolean removeDiacritics = true;

	/**
	 * Compare characters in a case insensitive manner.
	 */
	@Builder.Default
	private boolean caseInsensitive = true;

	/**
	 * Remove punctuation prior to checking the string.
	 */
	@Builder.Default
	private boolean removePunctuation = true;

	/**
	 * Used in conjunction with the {@link #removePunctuation} setting,
	 * defines the characters that are considered punctuation marks.
	 * By default, this uses the POSIX regular express &quot;punct&quot;
	 * class.
	 */
	@Builder.Default
	private String punctuationMarks = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

	/**
	 * Treat a null input as a palindrome.
	 */
	@Builder.Default
	private boolean nullOkay = true;

	/**
	 * Treat a blank string input as a palindrome.
	 */
	@Builder.Default
	private boolean blankStringOkay = true;

	public boolean check(final String input) {
		if (input == null) {
			return this.nullOkay;
		}

		if (this.blankStringOkay && input.length() == 0) {
			return true;
		}

		int i = 0;
		int j = input.length();

		boolean wasSomethingChanged = false;

		for (; i < j; i++, j--) {
			log.trace("i={}, j={}", i, j);

			String a = input.substring(i, i + 1);
			log.trace("a={}", a);
			do {
				wasSomethingChanged = false;

				while (this.skipWhitespace && StringUtils.isWhitespace(a)) {
					log.trace("whitespace!");

					i++;
					log.trace("i={}, j={}", i, j);

					// Exit early if we move past the other pointer
					if (i >= j) {
						return true;
					}

					a = input.substring(i, i + 1);
					log.trace("a={}", a);

					wasSomethingChanged = true;
				}

				while (this.removePunctuation && this.punctuationMarks.contains(a)) {
					log.trace("punctuation!");

					i++;
					log.trace("i={}, j={}", i, j);

					// Exit early if we move past the other pointer
					if (i >= j) {
						return true;
					}

					a = input.substring(i, i + 1);
					log.trace("a={}", a);

					wasSomethingChanged = true;
				}
			} while (wasSomethingChanged == true);
			if (this.removeDiacritics) {
				a = StringUtils.stripAccents(a);
			}
			if (this.caseInsensitive) {
				a = a.toLowerCase();
			}

			String b = input.substring(j - 1, j);
			log.trace("b={}", b);
			do {
				wasSomethingChanged = false;
				while (this.skipWhitespace && StringUtils.isWhitespace(b)) {
					log.trace("whitespace!");

					j--;
					log.trace("i={}, j={}", i, j);

					// Exit early if we move past the other pointer
					if (i >= j) {
						return true;
					}

					b = input.substring(j - 1, j);
					log.trace("b={}", b);

					wasSomethingChanged = true;
				}

				while (this.removePunctuation && this.punctuationMarks.contains(b)) {
					log.trace("punctuation!");

					j--;
					log.trace("i={}, j={}", i, j);

					// Exit early if we move past the other pointer
					if (i >= j) {
						return true;
					}

					b = input.substring(j - 1, j);
					log.trace("b={}", b);

					wasSomethingChanged = true;
				}
			} while (wasSomethingChanged == true);
			if (this.removeDiacritics) {
				b = StringUtils.stripAccents(b);
			}
			if (this.caseInsensitive) {
				b = b.toLowerCase();
			}

			log.trace("a={}, b={}", a, b);

			if (!a.equals(b)) {
				return false;
			}
		}

		return true;
	}

}