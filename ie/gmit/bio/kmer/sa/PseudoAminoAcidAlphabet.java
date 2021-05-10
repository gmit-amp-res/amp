package ie.gmit.bio.kmer.sa;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PseudoAminoAcidAlphabet {
	private static char[] AA_SYMBOLS = "ACDEFGHIKLMNPQRSTVWY".toCharArray();
	private static Random rand = ThreadLocalRandom.current();
	private static final int MAX_AA_ALPHABET_INDEX = 19; 
	
	static {
		fisherYeatsShuffle(AA_SYMBOLS);
	}
	
	private static void fisherYeatsShuffle(char[] key) {
		int index;
		Random random = ThreadLocalRandom.current();
		for (int i = key.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			if (index != i) {
				key[index] ^= key[i];
				key[i] ^= key[index];
				key[index] ^= key[i];
			}
		}
	}
	
	public static Object[][] generateAlphabet(int partitions){
		Object[][] temp = new Object[][] { { 'A', -1 }, { 'G', -1 }, { 'T', -1 }, { 'S', -1 }, { 'N', -1 }, { 'Q', -1 }, { 'D', -1 },
			{ 'E', -1 }, { 'H', -1 }, { 'R', -1 }, { 'K', -1 }, { 'P', -1 }, { 'C', -1 }, { 'M', -1 }, { 'F', -1 },
			{ 'I', -1 }, { 'L', -1 }, { 'V', -1 }, { 'W', -1 }, { 'Y', -1 }, };
			
		//Assign at least one letter to a partition
		for (int i = 0; i < partitions; i++) {
			temp[rand.nextInt(temp.length - 1)][1] = i; //Assign to a random group
		}
		
		for (int row = 0; row < temp.length; row++) { //Loop over all letters (no point in optimising this)
			if (Integer.parseInt(temp[row][1].toString()) == -1) {
				temp[row][1] = rand.nextInt(partitions); //Assign to a random group
			}
		}
		
		//Write out shuffled AA letters across col 0 of 2D array 	
		for (int i = 0; i < AA_SYMBOLS.length; i++) temp[i][0] = AA_SYMBOLS[i];
		
		return temp;
	}
	
	public static Object[][] shuffleAlphabet(Object[][] alphabet){
		int num = rand.nextInt(100) + 1; //Generate a random number from 1 -> 100
		
		return switch(num) { 
			/*
			 * 90% => Swap a pair of letters between groups (symbols))
			 * 6% => Swap the group of a pair of letters
			 * 2% => Reverse the ordering of letters but keep group order
			 * 2% => reverse the ordering of groups but keep letter order
			 */
			case 1, 2, 3, 4, 5, 6 -> swapLetterPairGroup(alphabet, rand.nextInt(MAX_AA_ALPHABET_INDEX), rand.nextInt(MAX_AA_ALPHABET_INDEX));
			case 7, 8 -> reverseGroups(alphabet);
			case 9, 10 -> reverseLetters(alphabet);
			default -> swapLetterPair(alphabet, rand.nextInt(MAX_AA_ALPHABET_INDEX), rand.nextInt(MAX_AA_ALPHABET_INDEX));
		};
	}
	
	public static Object[][] swapLetterPair(Object[][] alphabet, int one, int two) { //90%
		return swap(alphabet, one, two, 0); //Letters are in col 0	
	}
	
	public static Object[][] swapLetterPairGroup(Object[][] alphabet, int one, int two) { //6%
		return swap(alphabet, one, two, 1); //Letters are in col 1	
	}

	private static Object[][] swap(Object[][] alphabet, int one, int two, int col) { 
		Object temp = alphabet[one][col];
		alphabet[one][col] = alphabet[two][col];
		alphabet[two][col] = temp;
		return alphabet;
	}
	
	public static Object[][] reverseLetters(Object[][] alphabet) { //2%
		return reverseColumn(alphabet, 0); //Letters are in col 0	
	}
	
	public static Object[][] reverseGroups(Object[][] alphabet) { //2%
		return reverseColumn(alphabet, 1); //Groups are in col 1
	}
	
	private static Object[][] reverseColumn(Object[][] alphabet, int col) {
		Object temp = 0;
		for (int row = 0; row < alphabet.length / 2; row++) {
			temp = alphabet[row][col];
			alphabet[row][col] = alphabet[alphabet.length - (row + 1)][col];
			alphabet[alphabet.length - (row + 1)][col] = temp;
		}
		return alphabet;
	}

	public static Map<Character, Integer> getAlphabetMap(Object[][] alphabet){
		return Stream.of(alphabet).collect(Collectors.toMap(data -> (Character) data[0], data -> (Integer) data[1]));
	}
	
	public static Object[][] getAlphabetArray(Map<Character, Integer> alphabet){
		return alphabet.entrySet()
				       .stream()
				       .map(e -> new Object[]{e.getKey(), e.getValue()})
				       .toArray(Object[][]::new);
	}
	
	//Test that methods work correctly...
	public static void main(String[] args) {
		System.out.println("1) Generate Alphabet");
		Object[][] obj = PseudoAminoAcidAlphabet.generateAlphabet(5);
		System.out.println(PseudoAminoAcidAlphabet.getAlphabetMap(obj));

		System.out.println("2) Swap Letter Pair");
		obj = PseudoAminoAcidAlphabet.swapLetterPair(obj, rand.nextInt(MAX_AA_ALPHABET_INDEX), rand.nextInt(MAX_AA_ALPHABET_INDEX));
		System.out.println(PseudoAminoAcidAlphabet.getAlphabetMap(obj));
		
		System.out.println("3) Swap Letter Pair Group");
		obj = PseudoAminoAcidAlphabet.swapLetterPairGroup(obj, rand.nextInt(MAX_AA_ALPHABET_INDEX), rand.nextInt(MAX_AA_ALPHABET_INDEX));
		System.out.println(PseudoAminoAcidAlphabet.getAlphabetMap(obj));

		System.out.println("4) Reverse Order of Letters");
		obj = PseudoAminoAcidAlphabet.reverseLetters(obj);
		System.out.println(PseudoAminoAcidAlphabet.getAlphabetMap(obj));

		System.out.println("4) Reverse Order of Letters");
		obj = PseudoAminoAcidAlphabet.reverseGroups(obj);
		System.out.println(PseudoAminoAcidAlphabet.getAlphabetMap(obj));
	}
}