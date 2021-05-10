package ie.gmit.bio.kmer.sa;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import ie.gmit.bio.kmer.AminoAcidAlphabet;
import ie.gmit.bio.kmer.AminoAcidEncoder;
public class SimulatedAnnealing {
	private static final int MAX_TEMP = 30;
	private static final int MAX_TRANSITIONS = 100;
	private Random rand = ThreadLocalRandom.current();
	
	/*
	 	Simulated annealing algorithm for amino acid alphabets
	 	*******************************************************
	 	Generate a random amino alphabet with n partitions called parent
		Compute the Multi-class MCC value for parent
		for temp = 10 to 0 Step -1
		   for transitions = 50000 to 0 Step -1
		      Set child = shuffle(parent) //Make a small change to the alphabet
			 Compute the Multi-class MCC value for child
			 Set delta = MCC(child) - MCC(parent)
			 if delta > 0 Then //New alphabet better
			    Set parent = child
			 Else If delta < 0 Then //New alphabet worse
			    Set parent = child with probability e^(-delta / temp)
		   Next
		Next
	 */
	public void start(AminoAcidAlphabet alphabet, int alphabetSize) throws Exception {
		start(alphabet, PseudoAminoAcidAlphabet.generateAlphabet(alphabetSize));
	}
	
	public void start(AminoAcidAlphabet alphabet, Object[][] parent) throws Exception {
		
		double bestMCC = -1, mccParent = -1, mccChild = -1, delta = 0;
		SimulatedAnnealingCrossValidation cv = new SimulatedAnnealingCrossValidation();
		Object[][] best = null, child = null;

		mccParent = cv.getMCC(alphabet, parent);
		best = parent;
		bestMCC = mccParent;
		System.out.println(PseudoAminoAcidAlphabet.getAlphabetMap(parent));
		System.out.println("[INFO] MCC of starting alphabet: " + mccParent);
		
		
		for(int temp = MAX_TEMP; temp > 0; temp--){	
			System.out.println("[INFO] Temperature: " + temp);

			for(int transitions = MAX_TRANSITIONS; transitions > 0; transitions--){
				child = PseudoAminoAcidAlphabet.shuffleAlphabet(parent);
				mccChild = cv.getMCC(alphabet, child);
				
				delta = mccChild - mccParent;
				if(delta > 0){ //New key better
					parent = child;
					mccParent = mccChild;
				}else {
					if((Math.exp((delta / temp)) > rand.nextDouble())){ //Toss a coin...
						parent = child;
						mccParent = mccChild;
					}
				}
				
				if(mccParent > bestMCC){
					System.out.println("[INFO] Best MCC now: " + bestMCC);
					bestMCC = mccParent;
					best = parent;
					
					
					if(bestMCC > 0.99){
						temp = 0;
						transitions = 0;
					}
					
				}
				
				if (transitions % 10 == 0) {
					System.out.println("[" + transitions + "] " + bestMCC + "<===>" + mccChild);
				}
			}
		}
		
		System.out.println("[INFO] Finished...");
		System.out.println(PseudoAminoAcidAlphabet.getAlphabetMap(best));
	}

}