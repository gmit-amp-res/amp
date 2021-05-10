package ie.gmit.bio.validate;

/*
 *  This record is used solely to provide additional AMP information for
 *  the computation of the F1 score and other stats.
 */
import ie.gmit.bio.AntimicrobialPeptide;

public record AntimicrobialPeptideStats (AntimicrobialPeptide amp, double samples, double averageLength) implements Comparable<AntimicrobialPeptideStats>{

	@Override
	public int compareTo(AntimicrobialPeptideStats other) {
		return Double.compare(this.samples(), other.samples());
	}
}