package ie.gmit.bio.kmer.metrics;

import java.util.Comparator;

import ie.gmit.bio.kmer.*;

public class MajorityCountMetric implements KmerMetrics{
	private static final long serialVersionUID = 777L;

	@Override
	public double getDistance(long[] seeds, KmerHashMap query, KmerHashMap subject) {
		double totalMatches = 0;
		//Get the top n query k-mers in descending order
		KmerFrequency[] qf = query.getSortedKmerFrequencies(query.size());
		KmerFrequency[] sf = subject.getSortedKmerFrequencies(subject.size());
		for (int i = 0; i < qf.length; i++) { //Iterate over them
			totalMatches += getKmerMatchCount(seeds, qf[i].key(), sf);
		}
		return totalMatches;
	}

	private int getKmerMatchCount(long[] seeds, long query, KmerFrequency[] kmers) {
		int matches = 0;
		for (int rank = 0; rank < kmers.length; rank++) {
			for (long seed : seeds) {
				long q = query & seed;
				long s = kmers[rank].key() & seed;
				if (s == q) matches++;
			}
		}
		return matches;
	}
	
	@Override
	public Comparator<ClassifierResult> getResultComparator() {
		return (n, m) -> -Double.compare(n.metric(), m.metric());
	}
}