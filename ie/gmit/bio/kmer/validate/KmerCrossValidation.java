package ie.gmit.bio.kmer.validate;


/*
 * KmerCrossValidation
 * --------------------
 * This class is responsible for training and testing the k-mer classifier. The 
 * class reads in the set of AMP sequences from the ./amps/ directory and creates
 * a k-mer database for each AMP family. The system uses n-fold cross validation
 * by slicing TRAINING_TEST_RATIO of the AMP sequences into training and testing
 * data. The results are written out to the ./test/ directory, with a separate
 * file of statistics for each alphabet.
 */

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import ie.gmit.bio.AbstractFastaParser;
import ie.gmit.bio.AntimicrobialPeptide;
import ie.gmit.bio.Sequence;
import ie.gmit.bio.SequenceType;
import ie.gmit.bio.kmer.AminoAcidAlphabet;
import ie.gmit.bio.kmer.KmerDatabase;
import ie.gmit.bio.kmer.KmerHashMap;
import ie.gmit.bio.kmer.KmerUtils;
import ie.gmit.bio.kmer.ClassifierResult;
import ie.gmit.bio.kmer.metrics.MajorityCountMetric;
import ie.gmit.bio.validate.*;

public class KmerCrossValidation {
	private List<AntimicrobialPeptideStats> ampRecords = new ArrayList<>(); //Used only for stats and F1 score
	private MultiClassConfusionMatrix matrix = new MultiClassConfusionMatrix();
	private static final String OUT_DIR = "./temp/";
	private static final String TEST_FILE_NAME = "test.tmp";
	private File tstFile; 
	private FileWriter fw;
	private int n;
	private AminoAcidAlphabet alphabet;
	private long[] seeds; 
	private int TRAINING_TEST_RATIO = 8; //9:1 Training:Test Data
	
	public KmerCrossValidation(int n, AminoAcidAlphabet alphabet) throws Exception {
		this.n = n;
		this.alphabet = alphabet;
		seeds = KmerUtils.getEncodedSeeds(alphabet); 
		tstFile = new File(OUT_DIR + TEST_FILE_NAME);
	}
	
	public void parse() throws Exception {
		AntimicrobialPeptide[] amps = AntimicrobialPeptide.values(); //Get the set of AMPs 
		KmerCrossValidationFastaParser parser;
		
		/*
		 * Create n test fixtures by constructing n different subject databases from 
		 * TRAINING_TEST_RATIO of the known AMP sequences available. The remaining ratio 
		 * of sequences are saved to a test file that is then parsed and compared, 
		 * sequence-by-sequence against the database.
		 */
		for (int i = 0; i < n; i++) {
			//System.out.println("Round [" + (i + 1) + "] Starting...");
			fw = new FileWriter(tstFile);

			KmerDatabase db = new KmerDatabase(seeds, new MajorityCountMetric()); //Configure the database
			for (AntimicrobialPeptide amp : amps) { //For each AMP
				if (amp == AntimicrobialPeptide.Unknown) continue;
				KmerHashMap map = new KmerHashMap(seeds); //Create a KmerHashMap 
				parser = new KmerCrossValidationFastaParser(SequenceType.PROTEIN, amp, alphabet, map);
				parser.parse("./amps/" + amp.name() + ".fasta");
				db.add(amp, map); //Add to database
				ampRecords.add(parser.getAntimicrobialPeptideStats());
			}
			//System.out.println("\t Subject database built");
			
			
			//Execute the query tests against the test subject database using the same alphabet
			//System.out.println("\t Executing tests");
			KmerTestFastaParser cvTestParser = new KmerTestFastaParser(SequenceType.PROTEIN, db);
			cvTestParser.parse(tstFile.getAbsolutePath());
			
			//Tidy up and move on to next test fixture
			//System.out.println("\t Tearing down fixture...");
			cleanup();
		}
		
		FileWriter summary = new FileWriter(new File(OUT_DIR + alphabet.name() + ".csv"), false);
		//matrix.debug();
		summary.write(matrix.toString(ampRecords.stream().toArray(AntimicrobialPeptideStats[]::new)));
		summary.flush();
		summary.close();
		
		System.out.println(alphabet.name());
	}
	
	private class KmerTestFastaParser extends AbstractFastaParser{
		private KmerDatabase db;
		
		public KmerTestFastaParser(SequenceType type,  KmerDatabase db) {
			super(type);
			this.db = db;
		}

		@Override
		public void process() throws Exception {
			Sequence s = super.getFASTASequence();
			KmerHashMap qMap = new KmerHashMap(seeds);
			
			long[] kmers = KmerUtils.encode(s.getSequence(), alphabet);
			for (long kmer : kmers) {
				long frequency = qMap.get(kmer) + 1; 
				qMap.put(kmer, frequency);
			}
			
			ClassifierResult[] results = db.classify(qMap);
			if (results.length > 0) {
				matrix.update(results[0].amp().name(), s.getName());
			}else {
				matrix.update(AntimicrobialPeptide.Unknown.name(), s.getName());
			}
		}
	}
	
	//Cross validation parser - reads the FASTA file and splits into testing and database sequences
	private class KmerCrossValidationFastaParser extends AbstractFastaParser{
		private double totalSamples = 0;
		private double totalSeqLen = 0;
		private AntimicrobialPeptide amp;
		private AminoAcidAlphabet alphabet; 
		private KmerHashMap map;
		
		public KmerCrossValidationFastaParser(SequenceType type, AntimicrobialPeptide amp, AminoAcidAlphabet alphabet, KmerHashMap map) {
			super(type);
			this.amp = amp;
			this.map = map;
			this.alphabet = alphabet;
		}

		@Override
		public void process() throws Exception {
			Sequence s = super.getFASTASequence();
			totalSamples++;
			totalSeqLen += s.getSequence().length();
			
			if (totalSamples % TRAINING_TEST_RATIO == 0) { //Write out every TRAINING_TEST_RATIO to a test file 
				writeTest(s.getSequence(), amp);
			}else { 
				long[] kmers = KmerUtils.encode(s.getSequence(), alphabet);
				for (long kmer : kmers) {
					long frequency = map.get(kmer) + 1; 
					map.put(kmer, frequency);
				}
			}
		}
		
		public AntimicrobialPeptideStats getAntimicrobialPeptideStats() {
			return new AntimicrobialPeptideStats(amp, totalSamples, totalSeqLen / totalSamples);
		}
	}
	
	private void writeTest(CharSequence cs, AntimicrobialPeptide amp) throws Exception{
		fw.write(">" + amp.name() + "\n");
		fw.write(cs.toString() + "\n");
	}
	
	public void cleanup() throws Exception{
		if (tstFile.exists()) {
			tstFile.delete();
		}
	}
	
	public static void main(String[] args) throws Exception {
		KmerCrossValidation cv = new KmerCrossValidation(10, AminoAcidAlphabet.TEMPORIN_CLUSTAL_14);
		cv.parse();
		
		/*
		AminoAcidAlphabet[] alphabets = AminoAcidAlphabet.values();
		for (AminoAcidAlphabet alphabet : alphabets) {
			if (alphabet.name().startsWith("SA_")) {
				KmerCrossValidation cv = new KmerCrossValidation(10, alphabet);
				cv.parse();
			}
		}
		*/
		
		/*
		AminoAcidAlphabet[] alphabets = AminoAcidAlphabet.values();
		for (AminoAcidAlphabet alphabet : alphabets) {
			if (alphabet == AminoAcidAlphabet.Murphy18) continue;
			if (alphabet == AminoAcidAlphabet.IUPAC20) continue;
			KmerCrossValidation cv = new KmerCrossValidation(10, alphabet);
			cv.parse();
		}
		*/
		System.out.println("Done!");
	}
}