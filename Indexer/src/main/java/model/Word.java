package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Word {
	private String text;
	private Set<WordOccurrence> occurrences = new HashSet<>();

	public Word(String text, WordOccurrence occurrence) {
		this.text = text;
		this.occurrences.add(occurrence);
	}

	public Word(String text, Set<WordOccurrence> occurrences) {
		this.text = text;
		this.occurrences.addAll(occurrences);
	}

	public String getText() {
		return text;
	}

	public Set<WordOccurrence> getOccurrences() {
		return occurrences;
	}

	public void addOccurrence(WordOccurrence newOccurrence) {
		occurrences.add(newOccurrence);
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || getClass() != other.getClass()) return false;
		Word otherWord = (Word) other;
		return Objects.equals(text, otherWord.text);
	}

	@Override
	public int hashCode() {
		return Objects.hash(text);
	}


	public static class WordOccurrence implements Serializable {
		private final String bookID;
		private final Set<Integer> lines = new HashSet<>();
		private int frecuency;

		public WordOccurrence(String bookID, Integer line) {
			this.bookID = bookID;
			this.lines.add(line);
			this.frecuency = 1;
		}

		public String getBookID() {
			return bookID;
		}

		public Set<Integer> getLineOccurrences() {
			return this.lines;
		}

		public void addLineOccurrence(Integer line) {
			this.lines.add(line);
			this.frecuency++;
		}


		/* BORRAR

		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			WordOccurrence that = (WordOccurrence) obj;
			return Objects.equals(bookID, that.bookID);
		}

		@Override
		public int hashCode() {
			return Objects.hash(bookID);
		}

		 */
	}
}
