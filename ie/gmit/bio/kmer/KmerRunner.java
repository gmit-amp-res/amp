package ie.gmit.bio.kmer;

import ie.gmit.bio.AntimicrobialPeptide;
import ie.gmit.bio.SequenceType;
import ie.gmit.bio.kmer.metrics.MajorityCountMetric;
public class KmerRunner {
	public static void main(String[] args) throws Exception {
		AminoAcidAlphabet alphabet = AminoAcidAlphabet.Murphy15	; //Initialise the default AA alphabet
		long[] seeds = KmerUtils.getEncodedSeeds(alphabet); //Encode the seeds for the specified alphabet
		KmerDatabase db = new KmerDatabase(seeds, new MajorityCountMetric()); //Configure the database
		
		AntimicrobialPeptide[] amps = AntimicrobialPeptide.values(); //Get the set of AMPs 
		for (AntimicrobialPeptide amp : amps) { //For each AMP
			if (amp == AntimicrobialPeptide.Unknown) continue;
			
			System.out.print("[INFO] Creating k-mer database for " + amp.name() + "...");
			KmerHashMap map = new KmerHashMap(seeds); //Create a KmerHashMap 
			KmerFastaParser kfp = new KmerFastaParser(SequenceType.PROTEIN, alphabet, map);
			kfp.parse("./amps/" + amp.name() + ".fasta");
			System.out.print(" " + map.size() + " k-mers generated. ");
			System.out.println("Done.");

			db.add(amp, map); 
		}
		
		KmerUtils.saveKmerDatabase(db, "./kmer-dbs/kmers-" + alphabet.name() + ".db");
		
		KmerHashMap map = new KmerHashMap(seeds);
		KmerFastaParser parser = new KmerFastaParser(SequenceType.PROTEIN, alphabet, map);
		parser.parse("./test.fasta");
		
		ClassifierResult[] results = db.classify(map); //This works very well.
		
		System.out.println("-------- RESULTS ---------");
		System.out.println("The sequence belongs to one of the following AMP families:");
		for (ClassifierResult r : results) {
			System.out.println(r.amp() + "=>" + r.metric());
		}		
	}
}