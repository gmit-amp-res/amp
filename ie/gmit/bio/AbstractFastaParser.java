package ie.gmit.bio;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public abstract class AbstractFastaParser {
	private Sequence sequence = null;
	private SequenceType type = null;
	
	public AbstractFastaParser(SequenceType type) {
		super();
		this.type = type;
	}

	public void parse(String file) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		String line = null;
		while ((line = br.readLine()) != null) {
			if (line.startsWith(">")) {
				if (sequence != null) process();
				sequence = new Sequence(line.substring(1, line.length()), type);
			}else {
				sequence.append(line.toUpperCase());
			}
		}
		process();
		br.close();
	}
	
	public Sequence getFASTASequence() {
		return this.sequence;
	}
	
	public abstract void process() throws Exception;
}
