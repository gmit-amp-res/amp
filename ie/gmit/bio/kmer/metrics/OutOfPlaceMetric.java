package ie.gmit.bio.kmer.metrics;

import java.util.Comparator;

import ie.gmit.bio.kmer.*;

public class OutOfPlaceMetric implements KmerMetrics{
	private static final long serialVersionUID = 777L;
	private int top = 0;
	
	public OutOfPlaceMetric(int top) {
		super();
		this.top = top;
	}

	@Override
	public double getDistance(long[] seeds, KmerHashMap query, KmerHashMap subject) {
		double distance = 0;

		//Get the top n query k-mers in descending order
		KmerFrequency[] qf = query.getSortedKmerFrequencies(top);
		KmerFrequency[] sf = subject.getSortedKmerFrequencies(top);
		for (int rank = 0; rank < qf.length; rank++) { //Iterate over them
			distance += getKmerIndex(seeds, qf[rank].key(), sf) - rank;
		}
		
		//NB: scale the distance in proportion to the size of the subject db
		return (distance / Double.valueOf(subject.size()));
	}

	private int getKmerIndex(long[] seeds, long query, KmerFrequency[] kmers) {
		for (int rank = 0; rank < kmers.length; rank++) {
			for (long seed : seeds) {
				long q = query & seed;
				long s = kmers[rank].key() & seed;
				
				if (s == q) return rank;
			}
		}
		return kmers.length + 1;
	}

	@Override
	public Comparator<ClassifierResult> getResultComparator() {
		return (n, m) -> Double.compare(Math.abs(n.metric()), Math.abs(m.metric()));
	}
}