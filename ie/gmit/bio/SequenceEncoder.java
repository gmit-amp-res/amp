package ie.gmit.bio;

public class SequenceEncoder {
	private static final int SHIFT = 64; //Shift A->1 and Z->26
	private static final double ASTERISK = 42.0d;
	private static final double MAX = 26.000000001d; //Squashes all values between 0 and 0.9999999999
	
	//A=65, Z=90, *=42
	
	public double[][] encode(CharSequence sequence, int k) {
		int tilings = k - sequence.length() <= 0 ? 1 : k - (sequence.length() - 1);
		
		double[][] encoded = new double[tilings][k];
		//System.out.println(encoded.length + "->" + encoded[0].length + "===>" + sequence.length());
		
		int index = 0;
		for (int row = 0; row < encoded.length; row++) {
			for (int col = row; col < sequence.length() + row; col++) {
				encoded[row][col] = encode(sequence.charAt(index));
				index++;
			}
			index = 0;
		}
		
		debug(encoded);
		return encoded;
	}
	
	private double encode(double value) {
		return value == ASTERISK ?  0.00d : (value - SHIFT) / MAX;
	}
	
	private void debug(double[][] encoded) {
		for (int row = 0; row < encoded.length; row++) {
			for (int col = 0; col < encoded[row].length; col++) {
				System.out.print(encoded[row][col] + ",");
			}
			System.out.println();
		}
	}
}
