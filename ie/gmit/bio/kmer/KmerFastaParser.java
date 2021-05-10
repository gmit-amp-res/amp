package ie.gmit.bio.kmer;

import ie.gmit.bio.AbstractFastaParser;
import ie.gmit.bio.Sequence;
import ie.gmit.bio.SequenceType;

public class KmerFastaParser extends AbstractFastaParser{
	private AminoAcidAlphabet alphabet;
	private KmerHashMap map;
	
	public KmerFastaParser(SequenceType type, AminoAcidAlphabet alphabet, KmerHashMap map) {
		super(type);
		this.alphabet = alphabet;
		this.map = map;
	}

	@Override
	public void process() throws Exception {
		Sequence s = super.getFASTASequence();
		long[] kmers = KmerUtils.encode(s.getSequence(), alphabet);

		//Get rid of all this crap and use the KmerDatabase instead
		for (long kmer : kmers) {
			long frequency = map.get(kmer) + 1; 
			map.put(kmer, frequency);
		}
	}
}