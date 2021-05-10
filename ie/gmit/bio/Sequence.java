package ie.gmit.bio;

import java.util.*;

public class Sequence {
	private static final int FASTA_LINE_WIDTH = 70;
	private CharSequence name;
	private SequenceType type = SequenceType.NUCLEOTIDE;
	private List<Character> letters = new ArrayList<>();
	
	public Sequence() {
		super();
	}
	
	public Sequence(CharSequence name) {
		this();
		this.name = name;
	}
	
	public Sequence(CharSequence name, SequenceType type) {
		this(name);
		this.type = type;
	}
	
	public CharSequence getName() {
		return name;
	}

	public void setName(CharSequence name) {
		this.name = name;
	}

	public SequenceType getType() {
		return type;
	}

	public void setType(SequenceType type) {
		this.type = type;
	}

	public void append(CharSequence subsequence) {
		for (int i = 0; i < subsequence.length(); i++) {
			if (subsequence.charAt(i) != '*') append(subsequence.charAt(i));
		}
	}
	
	public void append(char element) {
		letters.add(element);
	}
	
	public CharSequence getSequence() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < letters.size(); i++) {
			builder.append(letters.get(i));
		}
		return builder.toString();
	}
	
	public CharSequence toString(ReadingFrame frame) {
		StringBuilder builder = new StringBuilder();
		builder.append('>');
		builder.append(this.name);
		builder.append(" /ReadingFrame-" + frame.getReadingFrame() + "\n");
		
		CharSequence cs = Translator.translate(letters, frame);
		int counter = 0;
		for (int i = 0; i < cs.length(); i++) {
			builder.append(cs.charAt(i));
			counter++;
			if (counter > 0 && counter % FASTA_LINE_WIDTH == 0) builder.append('\n'); 			
		}
		
		builder.append('\n');
		builder.append('\n');

		return builder.toString();
	}
	
	
	public CharSequence toWordString(ReadingFrame frame, int minlength) {
		CharSequence cs = Translator.translate(letters, frame);
		StringBuilder sb = new StringBuilder();
		
		StringBuilder builder = new StringBuilder();
		String tmpName = this.name + "/ReadingFrame-" + frame.getReadingFrame();
		
		int start = 1;
		for (int i = 0; i < cs.length(); i++) {
			char next = cs.charAt(i);
			
			if (next == '\n') continue;
			
			if (next == '\u002A') {
				if (builder.length() >= minlength) {
					tmpName = tmpName + "/" + start + "-" + (start + builder.length() - 1);
					sb.append(">" + tmpName + "\n");
					sb.append(builder.toString() + "\n\n");
				}
				
				//Reset
				start = i + 2;
				builder.setLength(0);
				tmpName = this.name + "/ReadingFrame-" + frame.getReadingFrame();
			}else {
				builder.append(next);
			}
		}
		
		if (builder.length() >= minlength) {
			tmpName = tmpName + "/" + start + "-" + (start + builder.length() - 1);
			sb.append(">" + tmpName + "\n");
			sb.append(builder.toString() + "\n\n");
		}
		return sb.toString();
	}
}