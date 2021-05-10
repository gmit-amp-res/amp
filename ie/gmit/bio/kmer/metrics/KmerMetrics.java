package ie.gmit.bio.kmer.metrics;

import java.io.Serializable;
import java.util.Comparator;

import ie.gmit.bio.kmer.*;

public interface KmerMetrics extends Serializable{
	double getDistance(long[] seeds, KmerHashMap query, KmerHashMap subject);
	Comparator<ClassifierResult> getResultComparator();
}

