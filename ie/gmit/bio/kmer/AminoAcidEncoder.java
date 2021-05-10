package ie.gmit.bio.kmer;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AminoAcidEncoder {
	private static AminoAcidEncoder alphabet = new AminoAcidEncoder();
	private Map<AminoAcidAlphabet, Map<Character, Integer>> table = new TreeMap<>();
						 
	private AminoAcidEncoder() {
		init();
	}
	
	public static AminoAcidEncoder getInstance() {
		return alphabet;
	}
	
	public int encode(char c, AminoAcidAlphabet alphabet) {
		return table.get(alphabet).getOrDefault(c, -1);
	}
	
	//Test purposes only...
	public void addEncodedAlphabet(AminoAcidAlphabet alphabet, Map<Character, Integer> encoding) {
		table.put(alphabet, encoding);
	}
	
	public Map<Character, Integer> getEncodedAlphabet(AminoAcidAlphabet alphabet) {
		return table.get(alphabet);
	}
	
	
	private void init() {
		/*
		 * Reduced 2 Letter (1 bit) Amino Acid Alphabet from Chan et al (1989) and Lau
		 * et al (1989) (polar: AGTSNQDEHRKP), H (hydrophobic: CMFILVWY)
		 */
		Map<Character, Integer> polarHydophobic2 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'G', 0 }, { 'T', 0 }, { 'S', 0 }, { 'N', 0 }, { 'Q', 0 }, { 'D', 0 },
						{ 'E', 0 }, { 'H', 0 }, { 'R', 0 }, { 'K', 0 }, { 'P', 0 }, { 'C', 1 }, { 'M', 1 }, { 'F', 1 },
						{ 'I', 1 }, { 'L', 1 }, { 'V', 1 }, { 'W', 1 }, { 'Y', 1 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 2 Letter (1 bit) Amino Acid Alphabet from Wang and Wang (1999)
		 * [CMFILVWY, ATHGPRDESNQK]
		 */
		Map<Character, Integer> wang2 = Stream
				.of(new Object[][] { { 'C', 0 }, { 'M', 0 }, { 'F', 0 }, { 'I', 0 }, { 'L', 0 }, { 'V', 0 }, { 'W', 0 },
						{ 'Y', 0 }, { 'A', 1 }, { 'T', 1 }, { 'H', 1 }, { 'G', 1 }, { 'P', 1 }, { 'R', 1 }, { 'D', 1 },
						{ 'E', 1 }, { 'S', 1 }, { 'N', 1 }, { 'Q', 1 }, { 'K', 1 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		/*
		 * Reduced 3 Letter (2 bit) Amino Acid Alphabet from Li et al (2003) 
		 * [CFYWMLIV, GPATS, NHQEDRK]
		 */
		Map<Character, Integer> li3 = Stream
				.of(new Object[][] { { 'C', 0 }, { 'F', 0 }, { 'Y', 0 }, { 'W', 0 }, { 'M', 0 }, { 'L', 0 }, { 'I', 0 },
						{ 'V', 0 }, { 'G', 1 }, { 'P', 1 }, { 'A', 1 }, { 'T', 1 }, { 'S', 1 }, { 'N', 2 }, { 'H', 2 },
						{ 'Q', 2 }, { 'E', 2 }, { 'D', 2 }, { 'R', 2 }, { 'K', 2 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 3 Letter (2 bit) Amino Acid Alphabet from Wang and Wang (1999)
		 * [CMFILVWY, ATHGPR, DESNQK]
		 */
		Map<Character, Integer> wang3 = Stream
				.of(new Object[][] { { 'C', 0 }, { 'M', 0 }, { 'F', 0 }, { 'I', 0 }, { 'L', 0 }, { 'V', 0 }, { 'W', 0 },
						{ 'Y', 0 }, { 'A', 1 }, { 'T', 1 }, { 'H', 1 }, { 'G', 1 }, { 'P', 1 }, { 'R', 1 }, { 'D', 2 },
						{ 'E', 2 }, { 'S', 2 }, { 'N', 2 }, { 'Q', 2 }, { 'K', 2 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 4 Letter (2 bit) Amino Acid Alphabet from Li et al (2003) [CFYW,
		 * MLIV, GPATS, NHQEDRK]
		 */
		Map<Character, Integer> li4 = Stream
				.of(new Object[][] { { 'C', 0 }, { 'F', 0 }, { 'Y', 0 }, { 'W', 0 }, { 'M', 1 }, { 'L', 1 }, { 'I', 1 },
						{ 'V', 1 }, { 'G', 2 }, { 'P', 2 }, { 'A', 2 }, { 'T', 2 }, { 'S', 2 }, { 'N', 3 }, { 'H', 3 },
						{ 'Q', 3 }, { 'E', 3 }, { 'D', 3 }, { 'R', 3 }, { 'K', 3 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 4 Letter (2 bit) Amino Acid Alphabet from Murphy et al (2000)
		 * [(LVIMC),(AGSTP), (FYW), (EDNQKRH)]
		 */
		Map<Character, Integer> murphy4 = Stream
				.of(new Object[][] { { 'L', 0 }, { 'V', 0 }, { 'I', 0 }, { 'M', 0 }, { 'C', 0 }, { 'A', 1 }, { 'G', 1 },
						{ 'S', 1 }, { 'T', 1 }, { 'P', 1 }, { 'F', 2 }, { 'Y', 2 }, { 'W', 2 }, { 'E', 3 }, { 'D', 3 },
						{ 'N', 3 }, { 'Q', 3 }, { 'K', 3 }, { 'R', 3 }, { 'H', 3 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 5 Letter (3 bit) Amino Acid Alphabet from Li et al (2003) [CFYW,
		 * MLIV, G, PATS, NHQEDRK]
		 */
		Map<Character, Integer> li5 = Stream
				.of(new Object[][] { { 'C', 0 }, { 'F', 0 }, { 'Y', 0 }, { 'W', 0 }, { 'M', 1 }, { 'L', 1 }, { 'I', 1 },
						{ 'V', 1 }, { 'G', 2 }, { 'P', 3 }, { 'A', 3 }, { 'T', 3 }, { 'S', 3 }, { 'N', 4 }, { 'H', 4 },
						{ 'Q', 4 }, { 'E', 4 }, { 'D', 4 }, { 'R', 4 }, { 'K', 4 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 5 Letter (3 bit) Amino Acid Alphabet from Murphy et al (2000)
		 * [(LVIMC), (ASGTP), (FYW), (EDNQ), (KRH)]
		 */
		Map<Character, Integer> murphy5 = Stream
				.of(new Object[][] { { 'L', 0 }, { 'V', 0 }, { 'I', 0 }, { 'M', 0 }, { 'C', 0 }, { 'A', 1 }, { 'S', 1 },
						{ 'G', 1 }, { 'T', 1 }, { 'P', 1 }, { 'F', 2 }, { 'Y', 2 }, { 'W', 2 }, { 'E', 3 }, { 'D', 3 },
						{ 'N', 3 }, { 'Q', 3 }, { 'K', 4 }, { 'R', 4 }, { 'H', 4 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 5 Letter (3 bit) Amino Acid Alphabet for physico-chemical properties from EMBL Heidelberg.
		 * [(Aliphatic: IVL), R (aRomatic: FYWH), C (Charged: KRDE), T (Tiny: GACS), D(Diverse: TMQNP)]
		 */
		Map<Character, Integer> physicoChemical5 = Stream
				.of(new Object[][] { { 'I', 0 }, { 'V', 0 }, { 'L', 0 }, { 'F', 1 }, { 'Y', 1 }, { 'W', 1 }, { 'H', 1 },
						{ 'K', 2 }, { 'R', 2 }, { 'D', 2 }, { 'E', 2 }, { 'G', 3 }, { 'A', 3 }, { 'C', 3 }, { 'S', 3 },
						{ 'T', 4 }, { 'M', 4 }, { 'Q', 4 }, { 'N', 4 }, { 'P', 4 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 5 Letter (3 bit) Amino Acid Alphabet from Wang and Wang (1999)
		 * [CMFILVWY, ATH, GP, DE, SNQRK]
		 */
		Map<Character, Integer> wang5 = Stream
				.of(new Object[][] { { 'C', 0 }, { 'M', 0 }, { 'F', 0 }, { 'I', 0 }, { 'L', 0 }, { 'V', 0 }, { 'W', 0 },
						{ 'Y', 0 }, { 'A', 1 }, { 'T', 1 }, { 'H', 1 }, { 'G', 2 }, { 'P', 2 }, { 'D', 3 }, { 'E', 3 },
						{ 'S', 4 }, { 'N', 4 }, { 'Q', 4 }, { 'R', 4 }, { 'K', 4 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced variant 5 Letter (3 bit) Amino Acid Alphabet from Wang and Wang
		 * (1999) [CMFI, LVWY, ATGS, NQDE, HPRK]
		 */
		Map<Character, Integer> wang5Variant = Stream
				.of(new Object[][] { { 'C', 0 }, { 'M', 0 }, { 'F', 0 }, { 'I', 0 }, { 'L', 1 }, { 'V', 1 }, { 'W', 1 },
						{ 'Y', 1 }, { 'A', 2 }, { 'T', 2 }, { 'G', 2 }, { 'S', 2 }, { 'N', 3 }, { 'Q', 3 }, { 'D', 3 },
						{ 'E', 3 }, { 'H', 4 }, { 'P', 4 }, { 'R', 4 }, { 'K', 4 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 6 Letter (3 bit) Amino Acid Alphabet from Murphy et al (2000)
		 * [(LVIM), (ASGT), (PHC), (FYW), (EDNQ), (KR)]
		 */
		Map<Character, Integer> murphy6 = Stream
				.of(new Object[][] { { 'L', 0 }, { 'V', 0 }, { 'I', 0 }, { 'M', 0 }, { 'A', 1 }, { 'S', 1 }, { 'G', 1 },
						{ 'T', 1 }, { 'P', 2 }, { 'H', 2 }, { 'C', 2 }, { 'F', 3 }, { 'Y', 3 }, { 'W', 3 }, { 'E', 4 },
						{ 'D', 4 }, { 'N', 4 }, { 'Q', 4 }, { 'K', 5 }, { 'R', 5 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 6 Letter (3 bits) Amino Acid Alphabet for physico-chemical properties from EMBL Heidelberg. 
		 * [Aliphatic(IVL), Aromatic(FYWH),PosCharged(KR),NegCharged(DE),Tiny(GACS), Diverse(TMQNP)]
		 */
		Map<Character, Integer> physicoChemical6 = Stream
				.of(new Object[][] { { 'I', 0 }, { 'V', 0 }, { 'L', 0 }, { 'F', 1 }, { 'Y', 1 }, { 'W', 1 }, { 'H', 1 },
						{ 'K', 2 }, { 'R', 2 }, { 'D', 3 }, { 'E', 3 }, { 'G', 4 }, { 'A', 4 }, { 'C', 4 }, { 'S', 4 },
						{ 'T', 5 }, { 'M', 0 }, { 'Q', 0 }, { 'N', 0 }, { 'P', 0 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 8 Letter (4 bit) Amino Acid Alphabet from Murphy et al (2000) 
		 * [H, P, (LVIMC), (AG), (ST), (FYW), (EDNQ), (KR)]
		 */
		Map<Character, Integer> murphy8 = Stream
				.of(new Object[][] { { 'H', 0 }, { 'P', 1 }, { 'L', 2 }, { 'V', 2 }, { 'I', 2 }, { 'M', 2 }, { 'C', 2 },
						{ 'A', 3 }, { 'G', 3 }, { 'S', 4 }, { 'T', 4 }, { 'F', 5 }, { 'Y', 5 }, { 'W', 5 }, { 'E', 6 },
						{ 'D', 6 }, { 'N', 6 }, { 'Q', 6 }, { 'K', 7 }, { 'R', 7 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 10 Letter (4 bit) Amino Acid Alphabet from Li et al (2003) 
		 * [C, FYW, ML, IV, G, P, ATS, NH, QED, RK]
		 */
		Map<Character, Integer> li10 = Stream
				.of(new Object[][] { { 'C', 0 }, { 'F', 1 }, { 'Y', 1 }, { 'W', 1 }, { 'M', 2 }, { 'L', 2 }, { 'I', 3 },
						{ 'V', 3 }, { 'G', 4 }, { 'P', 5 }, { 'A', 6 }, { 'T', 6 }, { 'S', 6 }, { 'N', 7 }, { 'H', 7 },
						{ 'Q', 8 }, { 'E', 8 }, { 'D', 8 }, { 'R', 9 }, { 'K', 9 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 10 Letter (4 bit) Amino Acid Alphabet from Murphy et al (2000) [A, C,
		 * G, H, P, (LVIM), (ST), (FYW), (EDNQ), (KR)]
		 */
		Map<Character, Integer> murphy10 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'C', 1 }, { 'G', 2 }, { 'H', 3 }, { 'P', 4 }, { 'L', 5 }, { 'V', 5 },
						{ 'I', 5 }, { 'M', 5 }, { 'S', 6 }, { 'T', 6 }, { 'F', 7 }, { 'Y', 7 }, { 'W', 7 }, { 'E', 8 },
						{ 'D', 8 }, { 'N', 8 }, { 'Q', 8 }, { 'K', 9 }, { 'R', 9 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 11 Letter (4 bit) Amino Acid Alphabet from Buchfink et al (2015)
		 * [KREDQN][C][G][H][ILV][M][F][Y][W][P][STA]
		 */
		Map<Character, Integer> buchfink11 = Stream
				.of(new Object[][] { { 'K', 0 }, { 'R', 0 }, { 'E', 0 }, { 'D', 0 }, { 'Q', 0 }, { 'N', 0 }, { 'C', 1 },
						{ 'G', 2 }, { 'H', 3 }, { 'I', 4 }, { 'L', 4 }, { 'V', 4 }, { 'M', 5 }, { 'F', 6 }, { 'Y', 7 },
						{ 'W', 8 }, { 'P', 9 }, { 'S', 10 }, { 'T', 10 }, { 'A', 10 } })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		
		/*
		 * Reduced 12 Letter (4 bit) Amino Acid Alphabet from Murphy et al (2000)
		 * [(LVIM), (C), (A), (G), (ST), (P), (FY), (W), (EQ), (DN), (KR), (H)]
		 */
		Map<Character, Integer> murphy12 = Stream
				.of(new Object[][] { { 'L', 0 }, { 'V', 0 }, { 'I', 0 }, { 'M', 0 }, { 'C', 1 }, { 'A', 2 }, { 'G', 3 },
						{ 'S', 4 }, { 'T', 4 }, { 'P', 5 }, { 'F', 6 }, { 'Y', 6 }, { 'W', 7 }, { 'E', 8 }, { 'Q', 8 },
						{ 'D', 9 }, { 'N', 9 }, { 'K', 10 }, { 'R', 10 }, { 'H', 11 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 15 Letter (4 bit) Amino Acid Alphabet from Murphy et al (2000) 
		 * [A, C, D, E, G, H, N, P, Q, S, T, W, (LVIM), (FY), (KR)]
		 */
		Map<Character, Integer> murphy15 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'C', 1 }, { 'D', 2 }, { 'E', 3 }, { 'G', 4 }, { 'H', 5 }, { 'N', 6 },
						{ 'P', 7 }, { 'Q', 8 }, { 'S', 9 }, { 'T', 10 }, { 'W', 11 }, { 'L', 12 }, { 'V', 12 },
						{ 'I', 12 }, { 'M', 12 }, { 'F', 13 }, { 'Y', 13 }, { 'K', 14 }, { 'R', 14 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		/*
		 * Reduced 18 Letter (5 bit) Amino Acid Alphabet from Murphy et al (2000) [(LM),
		 * (VI), (C), (A), (G), (S), (T), (P), (F), (Y), (W), (E), (D), (N), (Q), (K),
		 * (R), (H)]
		 */
		Map<Character, Integer> murphy18 = Stream
				.of(new Object[][] { { 'L', 0 }, { 'M', 0 }, { 'V', 1 }, { 'I', 1 }, { 'C', 2 }, { 'A', 3 }, { 'G', 4 },
						{ 'S', 5 }, { 'T', 6 }, { 'P', 7 }, { 'F', 8 }, { 'Y', 9 }, { 'W', 10 }, { 'E', 11 },
						{ 'D', 12 }, { 'N', 13 }, { 'Q', 14 }, { 'K', 15 }, { 'R', 16 }, { 'H', 17 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		/*
		 * Standard 20 letter (5 bit) UPAC Amino Acid Alphabet
		 */
		Map<Character, Integer> iUPAC20 = Stream
				.of(new Object[][] { { 'L', 0 }, { 'M', 1 }, { 'V', 2 }, { 'I', 3 }, { 'C', 4 }, { 'A', 5 }, { 'G', 6 },
						{ 'S', 7 }, { 'T', 8 }, { 'P', 9 }, { 'F', 10 }, { 'Y', 11 }, { 'W', 12 }, { 'E', 13 },
						{ 'D', 14 }, { 'N', 15 }, { 'Q', 16 }, { 'K', 17 }, { 'R', 18 }, { 'H', 19 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		table.put(AminoAcidAlphabet.PolarHydophobic2, polarHydophobic2);
		table.put(AminoAcidAlphabet.Wang2, wang2);
		table.put(AminoAcidAlphabet.Li3, li3);
		table.put(AminoAcidAlphabet.Wang3, wang3);
		table.put(AminoAcidAlphabet.Li4, li4);
		table.put(AminoAcidAlphabet.Murphy4, murphy4);
		table.put(AminoAcidAlphabet.Li5, li5);
		table.put(AminoAcidAlphabet.Murphy5, murphy5);
		table.put(AminoAcidAlphabet.PhysicoChemical5, physicoChemical5);
		table.put(AminoAcidAlphabet.Wang5, wang5);
		table.put(AminoAcidAlphabet.Wang5Variant, wang5Variant);		
		table.put(AminoAcidAlphabet.Murphy6, murphy6);
		table.put(AminoAcidAlphabet.PhysicoChemical6, physicoChemical6);
		table.put(AminoAcidAlphabet.Murphy8, murphy8);
		table.put(AminoAcidAlphabet.Li10, li10);
		table.put(AminoAcidAlphabet.Murphy10, murphy10);
		table.put(AminoAcidAlphabet.Buchfink11, buchfink11);
		table.put(AminoAcidAlphabet.Murphy12, murphy12);
		table.put(AminoAcidAlphabet.Murphy15, murphy15);
		table.put(AminoAcidAlphabet.Murphy18, murphy18);
		table.put(AminoAcidAlphabet.IUPAC20, iUPAC20);
		
		
		//**************************************************************************************************
		//                      Alphabets Generated by Simulated Annealing
		//   Alphabets are named with the prefix "sa" followed by the manner in which the starting state 
		//   was generated. For example, for sa_Murphy4, the starting alphabet was taken from the Murphy4 
		//   encoding and for sa_Random4, the starting state was randomly generated with 4 partitions.
		//**************************************************************************************************
		
		//SA alphabets based on existing reduced encoding alphabets
		Map<Character, Integer> sa_Murphy4 = Stream
				.of(new Object[][] { { 'A', 1 }, { 'C', 2 }, { 'D', 3 }, { 'E', 3 }, { 'F', 0 }, { 'G', 1 }, { 'H', 3 },
						{ 'I', 0 }, { 'K', 1 }, { 'L', 3 }, { 'M', 1 }, { 'N', 3 }, { 'P', 0 }, { 'Q', 3 }, { 'R', 0 },
						{ 'S', 0 }, { 'T', 2 }, { 'V', 3 }, { 'W', 1 }, { 'Y', 2 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Murphy5 = Stream
				.of(new Object[][] { { 'A', 1 }, { 'C', 3 }, { 'D', 0 }, { 'E', 1 }, { 'F', 0 }, { 'G', 1 }, { 'H', 1 },
						{ 'I', 0 }, { 'K', 4 }, { 'L', 2 }, { 'M', 0 }, { 'N', 3 }, { 'P', 4 }, { 'Q', 2 }, { 'R', 3 },
						{ 'S', 2 }, { 'T', 4 }, { 'V', 3 }, { 'W', 0 }, { 'Y', 1 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));		
		
		Map<Character, Integer> sa_Murphy6 = Stream
				.of(new Object[][] { { 'A', 2 }, { 'C', 4 }, { 'D', 3 }, { 'E', 0 }, { 'F', 4 }, { 'G', 1 }, { 'H', 3 },
						{ 'I', 1 }, { 'K', 1 }, { 'L', 2 }, { 'M', 4 }, { 'N', 0 }, { 'P', 5 }, { 'Q', 0 }, { 'R', 1 },
						{ 'S', 4 }, { 'T', 2 }, { 'V', 0 }, { 'W', 3 }, { 'Y', 5 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Murphy8 = Stream
				.of(new Object[][] { { 'A', 5 }, { 'C', 4 }, { 'D', 6 }, { 'E', 2 }, { 'F', 2 }, { 'G', 2 }, { 'H', 5 },
						{ 'I', 6 }, { 'K', 5 }, { 'L', 0 }, { 'M', 2 }, { 'N', 6 }, { 'P', 6 }, { 'Q', 7 }, { 'R', 3 },
						{ 'S', 4 }, { 'T', 2 }, { 'V', 3 }, { 'W', 1 }, { 'Y', 7 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Murphy10 = Stream
				.of(new Object[][] { { 'A', 8 }, { 'C', 9 }, { 'D', 8 }, { 'E', 2 }, { 'F', 5 }, { 'G', 5 }, { 'H', 7 },
						{ 'I', 8 }, { 'K', 3 }, { 'L', 4 }, { 'M', 7 }, { 'N', 5 }, { 'P', 6 }, { 'Q', 9 }, { 'R', 1 },
						{ 'S', 0 }, { 'T', 8 }, { 'V', 6 }, { 'W', 5 }, { 'Y', 7 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Murphy12 = Stream
				.of(new Object[][] { { 'A', 2 }, { 'C', 11 }, { 'D', 1 }, { 'E', 5 }, { 'F', 7 }, { 'G', 9 },
						{ 'H', 8 }, { 'I', 8 }, { 'K', 6 }, { 'L', 9 }, { 'M', 4 }, { 'N', 0 }, { 'P', 0 }, { 'Q', 0 },
						{ 'R', 6 }, { 'S', 10 }, { 'T', 4 }, { 'V', 3 }, { 'W', 10 }, { 'Y', 0 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Murphy15 = Stream
				.of(new Object[][] { { 'A', 11 }, { 'C', 14 }, { 'D', 7 }, { 'E', 6 }, { 'F', 10 }, { 'G', 12 },
						{ 'H', 1 }, { 'I', 13 }, { 'K', 13 }, { 'L', 12 }, { 'M', 8 }, { 'N', 4 }, { 'P', 9 },
						{ 'Q', 5 }, { 'R', 2 }, { 'S', 12 }, { 'T', 3 }, { 'V', 14 }, { 'W', 0 }, { 'Y', 12 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Wang2 = Stream
				.of(new Object[][] { { 'A', 1 }, { 'C', 1 }, { 'D', 1 }, { 'E', 1 }, { 'F', 0 }, { 'G', 1 }, { 'H', 0 },
						{ 'I', 0 }, { 'K', 1 }, { 'L', 1 }, { 'M', 1 }, { 'N', 0 }, { 'P', 0 }, { 'Q', 0 }, { 'R', 0 },
						{ 'S', 1 }, { 'T', 0 }, { 'V', 1 }, { 'W', 1 }, { 'Y', 1 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Wang3 = Stream
				.of(new Object[][] { { 'A', 2 }, { 'C', 0 }, { 'D', 0 }, { 'E', 1 }, { 'F', 2 }, { 'G', 1 }, { 'H', 0 },
						{ 'I', 2 }, { 'K', 0 }, { 'L', 1 }, { 'M', 2 }, { 'N', 2 }, { 'P', 0 }, { 'Q', 1 }, { 'R', 0 },
						{ 'S', 2 }, { 'T', 0 }, { 'V', 1 }, { 'W', 1 }, { 'Y', 0 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Wang5 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'C', 2 }, { 'D', 4 }, { 'E', 0 }, { 'F', 3 }, { 'G', 4 }, { 'H', 1 },
						{ 'I', 0 }, { 'K', 1 }, { 'L', 4 }, { 'M', 0 }, { 'N', 1 }, { 'P', 0 }, { 'Q', 0 }, { 'R', 2 },
						{ 'S', 0 }, { 'T', 4 }, { 'V', 4 }, { 'W', 3 }, { 'Y', 0 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Wang5Variant = Stream
				.of(new Object[][] { { 'A', 3 }, { 'C', 0 }, { 'D', 1 }, { 'E', 4 }, { 'F', 2 }, { 'G', 3 }, { 'H', 1 },
						{ 'I', 2 }, { 'K', 3 }, { 'L', 2 }, { 'M', 4 }, { 'N', 4 }, { 'P', 4 }, { 'Q', 1 }, { 'R', 2 },
						{ 'S', 0 }, { 'T', 3 }, { 'V', 1 }, { 'W', 0 }, { 'Y', 0 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Li3 = Stream
				.of(new Object[][] { { 'A', 1 }, { 'C', 0 }, { 'D', 2 }, { 'E', 1 }, { 'F', 2 }, { 'G', 0 }, { 'H', 1 },
						{ 'I', 0 }, { 'K', 0 }, { 'L', 2 }, { 'M', 0 }, { 'N', 2 }, { 'P', 2 }, { 'Q', 0 }, { 'R', 0 },
						{ 'S', 2 }, { 'T', 1 }, { 'V', 0 }, { 'W', 1 }, { 'Y', 2 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Li4 = Stream
				.of(new Object[][] { { 'A', 3 }, { 'C', 0 }, { 'D', 1 }, { 'E', 2 }, { 'F', 2 }, { 'G', 3 }, { 'H', 1 },
						{ 'I', 2 }, { 'K', 0 }, { 'L', 0 }, { 'M', 3 }, { 'N', 3 }, { 'P', 3 }, { 'Q', 2 }, { 'R', 2 },
						{ 'S', 3 }, { 'T', 0 }, { 'V', 1 }, { 'W', 1 }, { 'Y', 3 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Li10 = Stream
				.of(new Object[][] { { 'A', 6 }, { 'C', 4 }, { 'D', 9 }, { 'E', 9 }, { 'F', 2 }, { 'G', 1 }, { 'H', 3 },
						{ 'I', 5 }, { 'K', 8 }, { 'L', 2 }, { 'M', 7 }, { 'N', 8 }, { 'P', 6 }, { 'Q', 6 }, { 'R', 7 },
						{ 'S', 3 }, { 'T', 8 }, { 'V', 1 }, { 'W', 0 }, { 'Y', 1 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Buchfink11 = Stream
				.of(new Object[][] { { 'A', 5 }, { 'C', 4 }, { 'D', 7 }, { 'E', 10 }, { 'F', 0 }, { 'G', 4 },
						{ 'H', 0 }, { 'I', 2 }, { 'K', 3 }, { 'L', 0 }, { 'M', 10 }, { 'N', 10 }, { 'P', 1 },
						{ 'Q', 0 }, { 'R', 0 }, { 'S', 0 }, { 'T', 8 }, { 'V', 9 }, { 'W', 4 }, { 'Y', 6 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		
		//**** Random Alphabets ****
		Map<Character, Integer> sa_Random2 = Stream
				.of(new Object[][] { { 'A', 1 }, { 'C', 0 }, { 'D', 1 }, { 'E', 1 }, { 'F', 0 }, { 'G', 0 }, { 'H', 1 },
						{ 'I', 1 }, { 'K', 0 }, { 'L', 1 }, { 'M', 1 }, { 'N', 1 }, { 'P', 1 }, { 'Q', 0 }, { 'R', 0 },
						{ 'S', 1 }, { 'T', 1 }, { 'V', 0 }, { 'W', 0 }, { 'Y', 1 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random3 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'C', 2 }, { 'D', 2 }, { 'E', 2 }, { 'F', 1 }, { 'G', 1 }, { 'H', 1 },
						{ 'I', 2 }, { 'K', 1 }, { 'L', 2 }, { 'M', 1 }, { 'N', 1 }, { 'P', 1 }, { 'Q', 2 }, { 'R', 1 },
						{ 'S', 0 }, { 'T', 2 }, { 'V', 2 }, { 'W', 2 }, { 'Y', 0 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random4 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'C', 2 }, { 'D', 0 }, { 'E', 3 }, { 'F', 0 }, { 'G', 2 }, { 'H', 0 },
						{ 'I', 3 }, { 'K', 2 }, { 'L', 1 }, { 'M', 2 }, { 'N', 2 }, { 'P', 2 }, { 'Q', 2 }, { 'R', 0 },
						{ 'S', 3 }, { 'T', 1 }, { 'V', 0 }, { 'W', 1 }, { 'Y', 3 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random5 = Stream
				.of(new Object[][] { { 'A', 4 }, { 'C', 2 }, { 'D', 2 }, { 'E', 1 }, { 'F', 3 }, { 'G', 1 }, { 'H', 2 },
						{ 'I', 4 }, { 'K', 0 }, { 'L', 4 }, { 'M', 3 }, { 'N', 3 }, { 'P', 3 }, { 'Q', 3 }, { 'R', 3 },
						{ 'S', 3 }, { 'T', 2 }, { 'V', 2 }, { 'W', 3 }, { 'Y', 4 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random6 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'C', 5 }, { 'D', 4 }, { 'E', 5 }, { 'F', 2 }, { 'G', 0 }, { 'H', 2 },
						{ 'I', 0 }, { 'K', 3 }, { 'L', 5 }, { 'M', 3 }, { 'N', 5 }, { 'P', 0 }, { 'Q', 5 }, { 'R', 4 },
						{ 'S', 3 }, { 'T', 0 }, { 'V', 1 }, { 'W', 2 }, { 'Y', 5 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random7 = Stream
				.of(new Object[][] { { 'A', 6 }, { 'C', 6 }, { 'D', 5 }, { 'E', 0 }, { 'F', 1 }, { 'G', 3 }, { 'H', 3 },
						{ 'I', 4 }, { 'K', 6 }, { 'L', 0 }, { 'M', 1 }, { 'N', 5 }, { 'P', 0 }, { 'Q', 2 }, { 'R', 1 },
						{ 'S', 2 }, { 'T', 6 }, { 'V', 0 }, { 'W', 4 }, { 'Y', 1 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random8 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'C', 2 }, { 'D', 2 }, { 'E', 4 }, { 'F', 6 }, { 'G', 5 }, { 'H', 2 },
						{ 'I', 5 }, { 'K', 6 }, { 'L', 7 }, { 'M', 1 }, { 'N', 6 }, { 'P', 3 }, { 'Q', 1 }, { 'R', 3 },
						{ 'S', 6 }, { 'T', 3 }, { 'V', 3 }, { 'W', 1 }, { 'Y', 3 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random9 = Stream
				.of(new Object[][] { { 'A', 4 }, { 'C', 3 }, { 'D', 7 }, { 'E', 5 }, { 'F', 6 }, { 'G', 2 }, { 'H', 3 },
						{ 'I', 6 }, { 'K', 0 }, { 'L', 6 }, { 'M', 3 }, { 'N', 6 }, { 'P', 4 }, { 'Q', 7 }, { 'R', 8 },
						{ 'S', 3 }, { 'T', 8 }, { 'V', 8 }, { 'W', 1 }, { 'Y', 8 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random10 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'C', 5 }, { 'D', 9 }, { 'E', 2 }, { 'F', 3 }, { 'G', 0 }, { 'H', 9 },
						{ 'I', 8 }, { 'K', 5 }, { 'L', 4 }, { 'M', 3 }, { 'N', 9 }, { 'P', 6 }, { 'Q', 0 }, { 'R', 3 },
						{ 'S', 8 }, { 'T', 6 }, { 'V', 7 }, { 'W', 1 }, { 'Y', 9 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random11 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'C', 9 }, { 'D', 3 }, { 'E', 3 }, { 'F', 8 }, { 'G', 7 }, { 'H', 6 },
						{ 'I', 6 }, { 'K', 4 }, { 'L', 0 }, { 'M', 5 }, { 'N', 0 }, { 'P', 10 }, { 'Q', 8 }, { 'R', 8 },
						{ 'S', 1 }, { 'T', 10 }, { 'V', 7 }, { 'W', 10 }, { 'Y', 8 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random12 = Stream
				.of(new Object[][] { { 'A', 4 }, { 'C', 7 }, { 'D', 7 }, { 'E', 9 }, { 'F', 7 }, { 'G', 11 },
						{ 'H', 3 }, { 'I', 2 }, { 'K', 6 }, { 'L', 6 }, { 'M', 8 }, { 'N', 1 }, { 'P', 0 }, { 'Q', 10 },
						{ 'R', 4 }, { 'S', 0 }, { 'T', 0 }, { 'V', 8 }, { 'W', 5 }, { 'Y', 4 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random13 = Stream
				.of(new Object[][] { { 'A', 9 }, { 'C', 12 }, { 'D', 1 }, { 'E', 1 }, { 'F', 0 }, { 'G', 4 },
						{ 'H', 9 }, { 'I', 4 }, { 'K', 10 }, { 'L', 5 }, { 'M', 7 }, { 'N', 0 }, { 'P', 1 },
						{ 'Q', 10 }, { 'R', 6 }, { 'S', 5 }, { 'T', 2 }, { 'V', 11 }, { 'W', 2 }, { 'Y', 5 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random14 = Stream
				.of(new Object[][] { { 'A', 0 }, { 'C', 0 }, { 'D', 9 }, { 'E', 6 }, { 'F', 11 }, { 'G', 13 },
						{ 'H', 8 }, { 'I', 13 }, { 'K', 5 }, { 'L', 12 }, { 'M', 2 }, { 'N', 6 }, { 'P', 10 },
						{ 'Q', 4 }, { 'R', 0 }, { 'S', 9 }, { 'T', 1 }, { 'V', 5 }, { 'W', 2 }, { 'Y', 7 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random15 = Stream
				.of(new Object[][] { { 'A', 12 }, { 'C', 9 }, { 'D', 6 }, { 'E', 9 }, { 'F', 6 }, { 'G', 1 },
						{ 'H', 13 }, { 'I', 11 }, { 'K', 9 }, { 'L', 10 }, { 'M', 9 }, { 'N', 6 }, { 'P', 11 },
						{ 'Q', 14 }, { 'R', 12 }, { 'S', 8 }, { 'T', 13 }, { 'V', 13 }, { 'W', 7 }, { 'Y', 1 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		Map<Character, Integer> sa_Random16 = Stream
				.of(new Object[][] { { 'A', 14 }, { 'C', 13 }, { 'D', 11 }, { 'E', 11 }, { 'F', 13 }, { 'G', 1 },
						{ 'H', 6 }, { 'I', 5 }, { 'K', 2 }, { 'L', 15 }, { 'M', 4 }, { 'N', 3 }, { 'P', 1 },
						{ 'Q', 14 }, { 'R', 8 }, { 'S', 7 }, { 'T', 0 }, { 'V', 11 }, { 'W', 4 }, { 'Y', 6 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//Load simulated annealing generated alphabets into memory
		table.put(AminoAcidAlphabet.SA_Murphy4, sa_Murphy4);
		table.put(AminoAcidAlphabet.SA_Murphy5, sa_Murphy5);
		table.put(AminoAcidAlphabet.SA_Murphy6, sa_Murphy6);
		table.put(AminoAcidAlphabet.SA_Murphy8, sa_Murphy8);
		table.put(AminoAcidAlphabet.SA_Murphy10, sa_Murphy10);
		table.put(AminoAcidAlphabet.SA_Murphy12, sa_Murphy12);
		table.put(AminoAcidAlphabet.SA_Murphy15, sa_Murphy15);
		table.put(AminoAcidAlphabet.SA_Wang2, sa_Wang2);
		table.put(AminoAcidAlphabet.SA_Wang3, sa_Wang3);
		table.put(AminoAcidAlphabet.SA_Wang5, sa_Wang5);
		table.put(AminoAcidAlphabet.SA_Wang5Variant, sa_Wang5Variant);
		table.put(AminoAcidAlphabet.SA_Li3, sa_Li3);
		table.put(AminoAcidAlphabet.SA_Li4, sa_Li4);
		table.put(AminoAcidAlphabet.SA_Li10, sa_Li10);
		table.put(AminoAcidAlphabet.SA_Buchfink11, sa_Buchfink11);
		table.put(AminoAcidAlphabet.SA_Random2, sa_Random2);
		table.put(AminoAcidAlphabet.SA_Random3, sa_Random3);
		table.put(AminoAcidAlphabet.SA_Random4, sa_Random4);
		table.put(AminoAcidAlphabet.SA_Random5, sa_Random5);
		table.put(AminoAcidAlphabet.SA_Random6, sa_Random6);
		table.put(AminoAcidAlphabet.SA_Random7, sa_Random7);
		table.put(AminoAcidAlphabet.SA_Random8, sa_Random8);
		table.put(AminoAcidAlphabet.SA_Random9, sa_Random9);
		table.put(AminoAcidAlphabet.SA_Random10, sa_Random10);
		table.put(AminoAcidAlphabet.SA_Random11, sa_Random11);
		table.put(AminoAcidAlphabet.SA_Random12, sa_Random12);
		table.put(AminoAcidAlphabet.SA_Random13, sa_Random13);
		table.put(AminoAcidAlphabet.SA_Random14, sa_Random14);
		table.put(AminoAcidAlphabet.SA_Random15, sa_Random15);
		table.put(AminoAcidAlphabet.SA_Random16, sa_Random16);
		
		
		//**************************************************************************************************
		//                  Alphabets Designed for use with Specific AMPs (January 2021)	

		//15 letter Lectin Alphabet 
		//[(M), (HN), (LQ), (S), (I), (TYCK) (V), (D), (A), (P), (G), (E), (R), (W), (F)]
		Map<Character, Integer> ampLectinOptimised15 = Stream
				.of(new Object[][] { { 'L', 2 }, { 'M', 0 }, { 'V', 6 }, { 'I', 4 }, { 'C', 5 }, { 'A', 8 }, { 'G', 10 },
						{ 'S', 3 }, { 'T', 5 }, { 'P', 9 }, { 'F', 14 }, { 'Y', 5 }, { 'W', 13 }, { 'E', 11 },
						{ 'D', 7 }, { 'N', 1 }, { 'Q', 2 }, { 'K', 5 }, { 'R', 12 }, { 'H', 1 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		
		//15 letter Histone Alphabet 
		//[(M), (K), (S), (PE), (R), (DT), (L), (I), (Q), (F), (G),  (C), (HAV), (W), (NY)]
		Map<Character, Integer> ampHistoneOptimised15 = Stream
				.of(new Object[][] { { 'L', 6 }, { 'M', 0 }, { 'V', 12 }, { 'I', 7 }, { 'C', 11 }, { 'A', 12 }, { 'G', 10 },
						{ 'S', 2 }, { 'T', 5 }, { 'P', 3 }, { 'F', 9 }, { 'Y', 14 }, { 'W', 13 }, { 'E', 3 },
						{ 'D', 5 }, { 'N', 14 }, { 'Q', 8 }, { 'K', 1 }, { 'R', 4 }, { 'H', 12 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		
		//13 letter Snakin Alphabet 
		//[(M), (LKA), (G), (D), (I), (SQNH), (PC), (E), (T), (W), (Y), (R), (FV)]
		Map<Character, Integer> ampSnakinOptimised13 = Stream
				.of(new Object[][] { { 'L', 1 }, { 'M', 0 }, { 'V', 12 }, { 'I', 4 }, { 'C', 6 }, { 'A', 1 }, { 'G', 2 },
						{ 'S', 5 }, { 'T', 8 }, { 'P', 6 }, { 'F', 12 }, { 'Y', 10 }, { 'W', 9 }, { 'E', 7 },
						{ 'D', 3 }, { 'N', 5 }, { 'Q', 5 }, { 'K', 1 }, { 'R', 11 }, { 'H', 5 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		//13 letter Thionin Alphabet 
		//[ (M), (AG), (TS), (V), (PI), (RKL), (C), (D), (NEQ), (Y), (W), (F), (H)]
		Map<Character, Integer> ampThioninOptimised13 = Stream
				.of(new Object[][] { { 'L', 5 }, { 'M', 0 }, { 'V', 3 }, { 'I', 4 }, { 'C', 6 }, { 'A', 1 }, { 'G', 1 },
						{ 'S', 2 }, { 'T', 2 }, { 'P', 4 }, { 'F', 11 }, { 'Y', 9 }, { 'W', 10 }, { 'E', 8 },
						{ 'D', 7 }, { 'N', 8 }, { 'Q', 8 }, { 'K', 5 }, { 'R', 5 }, { 'H', 12 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//13 letter Brevinin Alphabet 
		//[(GM), (PTF), (KV), (L), (A), (S), (C), (E), (R), (ND), (IQH), (Y), (W)]
		Map<Character, Integer> ampBrevininOptimised13 = Stream
				.of(new Object[][] { { 'L', 3 }, { 'M', 0 }, { 'V', 2 }, { 'I', 10 }, { 'C', 6 }, { 'A', 4 },
						{ 'G', 0 }, { 'S', 5 }, { 'T', 1 }, { 'P', 1 }, { 'F', 1 }, { 'Y', 11 }, { 'W', 12 },
						{ 'E', 7 }, { 'D', 9 }, { 'N', 9 }, { 'Q', 10 }, { 'K', 2 }, { 'R', 8 }, { 'H', 10 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//11 letter Temporin Alphabet 
		//[(MFW), (PGQ), (L), (SC), (IDN), (E), (K), (RVA), (Y), (H), (T)]
		Map<Character, Integer> ampTemporinOptimised11 = Stream
				.of(new Object[][] { { 'L', 2 }, { 'M', 0 }, { 'V', 7 }, { 'I', 4 }, { 'C', 3 }, { 'A', 7 }, { 'G', 1 },
						{ 'S', 3 }, { 'T', 10 }, { 'P', 1 }, { 'F', 0 }, { 'Y', 8 }, { 'W', 0 }, { 'E', 5 }, { 'D', 4 },
						{ 'N', 4 }, { 'Q', 1 }, { 'K', 6 }, { 'R', 7 }, { 'H', 9 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));		
		
		
		//9 letter Thionin Alphabet 
		//[(MF), (L), (K), (AGTI), (VCYP), (QSR), (EDN), (H), (W)]
		Map<Character, Integer> ampThioninOptimised9 = Stream
				.of(new Object[][] { { 'L', 1 }, { 'M', 0 }, { 'V', 4 }, { 'I', 3 }, { 'C', 4 }, { 'A', 3 }, { 'G', 3 },
						{ 'S', 5 }, { 'T', 3 }, { 'P', 4 }, { 'F', 0 }, { 'Y', 4 }, { 'W', 8 }, { 'E', 6 }, { 'D', 6 },
						{ 'N', 6 }, { 'Q', 5 }, { 'K', 2 }, { 'R', 5 }, { 'H', 7 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));		
		
		
		table.put(AminoAcidAlphabet.AMP_OPTIMISED_LECTIN_15, ampLectinOptimised15);
		table.put(AminoAcidAlphabet.AMP_OPTIMISED_HISTONE_15, ampHistoneOptimised15);
		table.put(AminoAcidAlphabet.AMP_OPTIMISED_SNAKIN_13, ampSnakinOptimised13);
		table.put(AminoAcidAlphabet.AMP_OPTIMISED_THIONIN_13, ampThioninOptimised13);
		
		table.put(AminoAcidAlphabet.AMP_OPTIMISED_BREVININ_13, ampBrevininOptimised13);
		table.put(AminoAcidAlphabet.AMP_OPTIMISED_TEMPORIN_11, ampTemporinOptimised11);
		table.put(AminoAcidAlphabet.AMP_OPTIMISED_THIONIN_9, ampThioninOptimised9);
		
		
		
		//**************************************************************************************************
		//                  More Alphabets Designed for use with Specific AMPs (March 2021)		
		
		//2 letter Thionin Alphabet 
		//[(MFLKAGTIVCY), (PQSREDHW)]
		Map<Character, Integer> ampThioninVariationI2 = Stream
				.of(new Object[][] { { 'A', 0}, { 'C', 0}, { 'D', 1}, { 'E', 1}, { 'F', 0}, { 'G', 0}, { 'H', 1},
						{ 'I', 0}, { 'K', 0}, { 'L', 0}, { 'M', 0}, { 'N', 1}, { 'P', 1}, { 'Q', 1}, { 'R', 1},
						{ 'S', 1}, { 'T', 0}, { 'V', 0}, { 'W', 1}, { 'Y', 0}, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//2 letter Thionin Alphabet 
		//[(MFLKAGTI), (VCYPQSREDNHW)] 
		Map<Character, Integer> ampThioninVariationII2 = Stream
				.of(new Object[][] { { 'A', 0}, { 'C', 1}, { 'D', 1}, { 'E', 1}, { 'F', 0}, { 'G', 0}, { 'H', 1},
						{ 'I', 0}, { 'K', 0}, { 'L', 0}, { 'M', 0}, { 'N', 1}, { 'P', 1}, { 'Q', 1}, { 'R', 1},
						{ 'S', 1}, { 'T', 0}, { 'V', 1}, { 'W', 1}, { 'Y', 1}, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//4 letter Thionin Alphabet 
		//[(MF), (LKAGTI), (VCYPQSR), (EDNHW)] 
		Map<Character, Integer> ampThioninVariationIII4 = Stream
				.of(new Object[][] { { 'A', 1}, { 'C', 2}, { 'D', 3}, { 'E', 3}, { 'F', 0}, { 'G', 1}, { 'H', 3},
						{ 'I', 1}, { 'K', 1}, { 'L', 1}, { 'M', 0}, { 'N', 3}, { 'P', 2}, { 'Q', 2}, { 'R', 2},
						{ 'S', 2}, { 'T', 1}, { 'V', 2}, { 'W', 3}, { 'Y', 2}, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//5 letter Thionin Alphabet 
		//[(L), (K), (AGTI), (VCYPQSREDN), (HWMF)] 
 		Map<Character, Integer> ampThioninVariationIV5 = Stream
				.of(new Object[][] { { 'A', 2}, { 'C', 3}, { 'D', 3}, { 'E', 3}, { 'F', 4}, { 'G', 2}, { 'H', 4},
						{ 'I', 2}, { 'K', 1}, { 'L', 0}, { 'M', 4}, { 'N', 3}, { 'P', 3}, { 'Q', 3}, { 'R', 3},
						{ 'S', 3}, { 'T', 2}, { 'V', 3}, { 'W', 4}, { 'Y', 3}, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//5 letter Thionin Alphabet 
		//[(MF), (LKAGTI), (VCYP), (QRSEDN), (HW)] 
		Map<Character, Integer> ampThioninVariationV5 = Stream
				.of(new Object[][] { { 'A', 2}, { 'C', 3}, { 'D', 4}, { 'E', 4}, { 'F', 0}, { 'G', 2}, { 'H', 1},
						{ 'I', 2}, { 'K', 2}, { 'L', 2}, { 'M', 0}, { 'N', 4}, { 'P', 3}, { 'Q', 4}, { 'R', 4},
						{ 'S', 4}, { 'T', 2}, { 'V', 3}, { 'W', 1}, { 'Y', 3}, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//2 letter Temporin Alphabet 
		//[(MFWPGQLSCIDNEK), (RVAYHT)]
		Map<Character, Integer> ampTemporinVariationI2 = Stream
				.of(new Object[][] { { 'A', 1}, { 'C', 0}, { 'D', 0}, { 'E', 0}, { 'F', 0}, { 'G', 0}, { 'H', 1},
						{ 'I', 0}, { 'K', 0}, { 'L', 0}, { 'M', 0}, { 'N', 0}, { 'P', 0}, { 'Q', 0}, { 'R', 1},
						{ 'S', 0}, { 'T', 1}, { 'V', 1}, { 'W', 0}, { 'Y', 1}, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//3 letter Temporin Alphabet 
		//[(MFWGQ), (LSCIDNEK), (RVAYHT)]
		Map<Character, Integer> ampTemporinVariationII3 = Stream
				.of(new Object[][] { { 'A', 2}, { 'C', 1}, { 'D', 1}, { 'E', 1}, { 'F', 0}, { 'G', 0}, { 'H', 2},
						{ 'I', 1}, { 'K', 1}, { 'L', 1}, { 'M', 0}, { 'N', 1}, { 'P', 1}, { 'Q', 0}, { 'R', 2},
						{ 'S', 1}, { 'T', 2}, { 'V', 2}, { 'W', 0}, { 'Y', 2}, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		
		//3 letter Temporin Alphabet 
		//[(MFWTHY), (EKRVA), (PGQSCIDNL)] <--- Added L to PGQSCIDNL. Missing from alphabet...
		Map<Character, Integer> ampTemporinVariationIII3 = Stream
				.of(new Object[][] { { 'A', 1}, { 'C', 2}, { 'D', 2}, { 'E', 1}, { 'F', 0}, { 'G', 2}, { 'H', 0},
						{ 'I', 2}, { 'K', 1}, { 'L', 2}, { 'M', 0}, { 'N', 2}, { 'P', 2}, { 'Q', 2}, { 'R', 1},
						{ 'S', 2}, { 'T', 0}, { 'V', 1}, { 'W', 0}, { 'Y', 0}, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//6 letter Temporin Alphabet 
		//[(PGQ), (LSC), (IDNE), (K), (RVA), (MFWYHT)]
		Map<Character, Integer> ampTemporinVariationIV6 = Stream
				.of(new Object[][] { { 'A', 4}, { 'C', 1}, { 'D', 2}, { 'E', 2}, { 'F', 5}, { 'G', 0}, { 'H', 5},
						{ 'I', 2}, { 'K', 3}, { 'L', 1}, { 'M', 5}, { 'N', 2}, { 'P', 0}, { 'Q', 0}, { 'R', 4},
						{ 'S', 1}, { 'T', 5}, { 'V', 4}, { 'W', 5}, { 'Y', 5}, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		

		//5 letter Temporin Alphabet 
		//[(GQLSC), (PIDN), (K), (ERVA), (YHTMFW)] 
		Map<Character, Integer> ampTemporinVariationV5 = Stream
				.of(new Object[][] { { 'A', 3}, { 'C', 0}, { 'D', 1}, { 'E', 3}, { 'F', 4}, { 'G', 0}, { 'H', 4},
						{ 'I', 1}, { 'K', 2}, { 'L', 0}, { 'M', 4}, { 'N', 1}, { 'P', 1}, { 'Q', 0}, { 'R', 3},
						{ 'S', 0}, { 'T', 4}, { 'V', 3}, { 'W', 4}, { 'Y', 4}, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		
		table.put(AminoAcidAlphabet.THIONIN_VARIATION_I_2, ampThioninVariationI2);
		table.put(AminoAcidAlphabet.THIONIN_VARIATION_II_2, ampThioninVariationII2);
		table.put(AminoAcidAlphabet.THIONIN_VARIATION_III_4, ampThioninVariationIII4);
		table.put(AminoAcidAlphabet.THIONIN_VARIATION_IV_5, ampThioninVariationIV5);
		table.put(AminoAcidAlphabet.THIONIN_VARIATION_V_5, ampThioninVariationV5);

		table.put(AminoAcidAlphabet.TEMPORIN_VARIATION_I_2, ampTemporinVariationI2);
		table.put(AminoAcidAlphabet.TEMPORIN_VARIATION_II_3, ampTemporinVariationII3);
		table.put(AminoAcidAlphabet.TEMPORIN_VARIATION_III_3, ampTemporinVariationIII3);
		table.put(AminoAcidAlphabet.TEMPORIN_VARIATION_IV_6, ampTemporinVariationIV6);
		table.put(AminoAcidAlphabet.TEMPORIN_VARIATION_V_5, ampTemporinVariationV5);
		
		
		
		
		//**************************************************************************************************
		//         Alphabets Designed with JalView and Clustal (March 29th 2021)	
	
		//5 letter Thionin Alphabet 
		//[ (SV), (CL), (Y), (F), (I), (GRA), (P), (K), (T), (EQ), (M), (D), (N), (W), (H)]
		Map<Character, Integer> ampThioninClustal15 = Stream
				.of(new Object[][] { { 'A', 5 }, { 'G', 5 }, { 'T', 8 }, { 'S', 0 }, { 'N', 12 }, { 'Q', 9 },
						{ 'D', 11 }, { 'E', 9 }, { 'H', 14 }, { 'R', 5 }, { 'K', 7 }, { 'P', 6 }, { 'C', 1 },
						{ 'M', 10 }, { 'F', 3 }, { 'I', 4 }, { 'L', 1 }, { 'V', 0 }, { 'W', 13 }, { 'Y', 2 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//16 letter Temporin Alphabet 
		//[(M), (K), (GA), (S), (L), (F), (T), (I), (NH), (C), (QE), (R), (D), (P), (V), (WY)] 
		Map<Character, Integer> ampTemporinClustal16 = Stream
				.of(new Object[][] { { 'A', 2 }, { 'G', 2 }, { 'T', 6 }, { 'S', 3 }, { 'N', 8 }, { 'Q', 10 }, { 'D', 12 },
						{ 'E', 10 }, { 'H', 8 }, { 'R', 11 }, { 'K', 1 }, { 'P', 13 }, { 'C', 9 }, { 'M', 0 }, { 'F', 5 },
						{ 'I', 7 }, { 'L', 4 }, { 'V', 14 }, { 'W', 15 }, { 'Y', 15 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		table.put(AminoAcidAlphabet.THIONIN_CLUSTAL_15, ampThioninClustal15);
		table.put(AminoAcidAlphabet.TEMPORIN_CLUSTAL_16, ampTemporinClustal16);
		
		
		//**************************************************************************************************
		//         Alphabets Designed with JalView and Clustal (April 20th 2021)	
		
		//10 letter Thionin Alphabet 
		//[(SV), (CLT), (Y), (F), (I), (GRA), (P), (K), (EQ), (MDNWH)]
		Map<Character, Integer> ampThioninClustal10 = Stream
				.of(new Object[][] { { 'A', 5 }, { 'C', 1 }, { 'D', 9 }, { 'E', 8 }, { 'F', 3 }, { 'G', 5 }, { 'H', 9 },
						{ 'I', 4 }, { 'K', 7 }, { 'L', 1 }, { 'M', 9 }, { 'N', 9 }, { 'P', 6 }, { 'Q', 8 }, { 'R', 5 },
						{ 'S', 0 }, { 'T', 1 }, { 'V', 0 }, { 'W', 9 }, { 'Y', 2 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//12 letter Thionin Alphabet 
		//[(SV), (CLT), (Y), (F), (I), (GRA), (P), (K), (EQ), (M), (D), (NWH)]
		Map<Character, Integer> ampThioninClustal12 = Stream
				.of(new Object[][] { { 'A', 5 }, { 'C', 1 }, { 'D', 10 }, { 'E', 8 }, { 'F', 3 }, { 'G', 5 },
						{ 'H', 11 }, { 'I', 4 }, { 'K', 7 }, { 'L', 1 }, { 'M', 9 }, { 'N', 11 }, { 'P', 6 },
						{ 'Q', 8 }, { 'R', 5 }, { 'S', 0 }, { 'T', 1 }, { 'V', 0 }, { 'W', 11 }, { 'Y', 2 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));		
		
		
		//14 letter Thionin Alphabet 
		//[(SV), (CLT), (Y), (F), (I), (GRA), (P), (K), (EQ), (M), (D), (N), (W), (H)]
		Map<Character, Integer> ampThioninClustal14 = Stream
				.of(new Object[][] { { 'A', 5 }, { 'C', 1 }, { 'D', 10 }, { 'E', 8 }, { 'F', 3 }, { 'G', 5 },
						{ 'H', 13 }, { 'I', 4 }, { 'K', 7 }, { 'L', 1 }, { 'M', 9 }, { 'N', 11 }, { 'P', 6 },
						{ 'Q', 8 }, { 'R', 5 }, { 'S', 0 }, { 'T', 1 }, { 'V', 0 }, { 'W', 12 }, { 'Y', 2 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));		
		
		
		//10 letter Temporin Alphabet 
		//[(M), (FKSL), (GTA), (EQ), (I), (NH), (C), (RD), (P),(VWY)]
		Map<Character, Integer> ampTemporinClustal10 = Stream
				.of(new Object[][] { { 'A', 2 }, { 'C', 6 }, { 'D', 7 }, { 'E', 3 }, { 'F', 1 }, { 'G', 2 }, { 'H', 5 },
						{ 'I', 4 }, { 'K', 1 }, { 'L', 1 }, { 'M', 0 }, { 'N', 5 }, { 'P', 8 }, { 'Q', 3 }, { 'R', 7 },
						{ 'S', 1 }, { 'T', 2 }, { 'V', 9 }, { 'W', 9 }, { 'Y', 9 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
		
		
		//12 letter Temporin Alphabet 
		//[(M), (FKS), (L), (GTA), (EQ), (I), (NH), (C), (RD), (P), (V), (WY)]
		Map<Character, Integer> ampTemporinClustal12 = Stream
				.of(new Object[][] { { 'A', 3 }, { 'C', 7 }, { 'D', 8 }, { 'E', 4 }, { 'F', 1 }, { 'G', 3 }, { 'H', 6 },
						{ 'I', 5 }, { 'K', 1 }, { 'L', 2 }, { 'M', 0 }, { 'N', 6 }, { 'P', 9 }, { 'Q', 4 }, { 'R', 8 },
						{ 'S', 1 }, { 'T', 3 }, { 'V', 10 }, { 'W', 11 }, { 'Y', 11 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));

		
		//14 letter Temporin Alphabet 
		//[(M), (FK), (S), (L), (GTA), (EQ), (I), (NH), (C), (R), (D), (P), (V), (WY)]
		Map<Character, Integer> ampTemporinClustal14 = Stream
				.of(new Object[][] { { 'A', 4 }, { 'C', 8 }, { 'D', 10 }, { 'E', 5 }, { 'F', 1 }, { 'G', 4 },
						{ 'H', 7 }, { 'I', 6 }, { 'K', 1 }, { 'L', 3 }, { 'M', 0 }, { 'N', 7 }, { 'P', 11 }, { 'Q', 5 },
						{ 'R', 9 }, { 'S', 2 }, { 'T', 4 }, { 'V', 12 }, { 'W', 13 }, { 'Y', 13 }, })
				.collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));		
		
		
		table.put(AminoAcidAlphabet.THIONIN_CLUSTAL_10, ampThioninClustal10);
		table.put(AminoAcidAlphabet.THIONIN_CLUSTAL_12, ampThioninClustal12);
		table.put(AminoAcidAlphabet.THIONIN_CLUSTAL_14, ampThioninClustal14);
		table.put(AminoAcidAlphabet.TEMPORIN_CLUSTAL_10, ampTemporinClustal10);
		table.put(AminoAcidAlphabet.TEMPORIN_CLUSTAL_12, ampTemporinClustal12);
		table.put(AminoAcidAlphabet.TEMPORIN_CLUSTAL_14, ampTemporinClustal14);
	}
	
	public int getAlphabetCount() {
		return table.size();
	}
}