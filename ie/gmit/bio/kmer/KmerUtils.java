package ie.gmit.bio.kmer;

import java.io.*;
import java.util.*;

public class KmerUtils {
	private static AminoAcidEncoder encoder = AminoAcidEncoder.getInstance();
	private static final String[] SEEDS = {
		"110110101000111",	//l=15, w=9. 	Choi et al (2004).
		"110101100010111", 	//l=15, w=9. 	Choi et al (2004).
		"110110010100111",	//l=15, w=9. 	Choi et al (2004).
		"1101100011010111",	//l=16, w=10.	Choi et al (2004).
		"1110010100110111",	//l=16, w=10. 	Choi et al (2004).
		"111101011101111"	//l=15, w=12. 	Buchfink (2015)
	};
	
	public static long[] encode(CharSequence sequence, AminoAcidAlphabet alphabet) {
		AminoAcidEncoder encoder = AminoAcidEncoder.getInstance();

		int shift = (int) Math.ceil(Math.log(alphabet.getSymbolsCount()) / Math.log(2));
		int blockSize = (int) Math.floor(64.0d / shift);
		List<Long> kmers = new ArrayList<>();

		StringBuilder buffer = new StringBuilder(sequence);
		long index = 0; //Current index of the k-mer
		long packed = 0;
		int symbol = 0;

		do{
			index++;
			if (index == 1){ //At the start of a sequence
				if (blockSize >= buffer.length()) {
					packed = getKmerAsLong(buffer.toString(), shift,alphabet); //Convert the sequence of k characters into a long
				}else {
					packed = getKmerAsLong(buffer.substring(0, blockSize), shift,alphabet); //Convert the sequence of k characters into a long
					buffer.delete(0, blockSize); //Remove the k-mer from the buffer
				}
			}else{	
				symbol = encoder.encode(Character.toUpperCase(buffer.charAt(0)), alphabet);
				packed <<= shift; // Left shift encoding bits
				packed |= symbol;
				buffer.delete(0, 1); //Delete next character from buffer
			}
			kmers.add(packed);
		}while (buffer.length() >= blockSize);

		return kmers.stream().mapToLong(Long::longValue).toArray();
	}
	
	public static long getKmerAsLong(String kmer, int shift, AminoAcidAlphabet alphabet) {
		long packed = 0x0000000000000000L;
		int symbol = 0;
		for (int i = 0; i < kmer.length(); i++){
			symbol =  encoder.encode(Character.toUpperCase(kmer.charAt(i)), alphabet);
			packed <<= shift; // Left shift encoding bits
			packed |= symbol;	
		}
		
		return packed;
	}

	public static long encodeSeed(String seed, AminoAcidAlphabet alphabet) throws Exception{
		int shift = (int) Math.ceil(Math.log(alphabet.getSymbolsCount()) / Math.log(2));
		int matchBits = Math.max((int) Math.pow(shift, 2.0) - 1, 1);
		if (seed.length() > 64/shift) throw new Exception("Seed too long for alphabet.");
		
		long packed = 0x0000000000000000L;
		char c = '0';
		for (int i = 0; i < seed.length(); i++){
			c = seed.charAt(i); 
			packed <<= shift; 
			if (c == '1') packed |= matchBits;		
		}
		while (packed >= 0) {
			packed <<= 1;
		}
		return packed;
	}
	
	public static long[] getEncodedSeeds(AminoAcidAlphabet alphabet){
		List<Long> list = new ArrayList<>();
		
		for (String s: SEEDS) {
			try {
				list.add(encodeSeed(s, alphabet));
			}catch(Exception e) {
				//Ignore. Seed was too long to use...
			}
		
		}
		return list.stream().mapToLong(Long::longValue).toArray();
	}
	
	
	public static void saveKmerDatabase(KmerDatabase db, String name) throws Exception{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(name)));
		out.writeObject(db);
		out.close();
	}
	
	public static KmerDatabase loadKmerDatabase(String name) throws Exception{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(name)));
		KmerDatabase db = (KmerDatabase) in.readObject();
		in.close();
		return db;
	}
}