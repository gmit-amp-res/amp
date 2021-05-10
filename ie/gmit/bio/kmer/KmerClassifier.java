package ie.gmit.bio.kmer;

import ie.gmit.bio.AbstractFastaParser;
import ie.gmit.bio.AntimicrobialPeptide;
import ie.gmit.bio.Sequence;
import ie.gmit.bio.SequenceType;
import ie.gmit.bio.kmer.metrics.MajorityCountMetric;

public class KmerClassifier {
	private KmerDatabase db;
	private AminoAcidAlphabet alphabet;
	private long[] seeds; 
	
	public KmerClassifier(AminoAcidAlphabet alphabet) {
		super();
		this.alphabet = alphabet;
		seeds = KmerUtils.getEncodedSeeds(alphabet);
		db = new KmerDatabase(seeds, new MajorityCountMetric()); //Configure the database
	}
	
	public ClassifierResult[] classify(Sequence s) throws Exception{
		if (s.getType() != SequenceType.PROTEIN) throw new Exception("Invalid protein sequence encoding.");
		
		KmerHashMap query = new KmerHashMap(seeds);
		
		long[] kmers = KmerUtils.encode(s.getSequence(), alphabet);
		for (long kmer : kmers) {
			long frequency = query.get(kmer) + 1; 
			query.put(kmer, frequency);
		}
		return db.classify(query);
	}
	
	public void buildSubjectDatabase(String directory) throws Exception {
		AntimicrobialPeptide[] amps = AntimicrobialPeptide.values(); //Get the set of AMPs 
		KmerAMPParser parser;
		for (AntimicrobialPeptide amp : amps) { //For each AMP
			if (amp == AntimicrobialPeptide.Unknown) continue;
			
			KmerHashMap map = new KmerHashMap(seeds); //Create a KmerHashMap 
			parser = new KmerAMPParser(SequenceType.PROTEIN, map);
			parser.parse("./amps/" + amp.name() + ".fasta");
			db.add(amp, map); //Add to database
		}
	}
	
	private class KmerAMPParser extends AbstractFastaParser{
		private KmerHashMap map;
		
		public KmerAMPParser(SequenceType type, KmerHashMap map) {
			super(type);
			this.map = map;
		}
	
		@Override
		public void process() throws Exception {
			Sequence s = super.getFASTASequence();

			long[] kmers = KmerUtils.encode(s.getSequence(), alphabet);
			for (long kmer : kmers) {
				long frequency = map.get(kmer) + 1; 
				map.put(kmer, frequency);
			}
		}
	}	
}