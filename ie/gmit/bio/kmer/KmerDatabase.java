package ie.gmit.bio.kmer;

import java.util.*;
import java.io.Serializable;
import ie.gmit.bio.AntimicrobialPeptide;
import ie.gmit.bio.kmer.metrics.KmerMetrics;
public class KmerDatabase implements Serializable{
	private static final long serialVersionUID = 777L;
	private Map<AntimicrobialPeptide, KmerHashMap> db = new TreeMap<>();
	private long[] seeds;
	private KmerMetrics metrics;
	
	public KmerDatabase(long[] seeds, KmerMetrics metrics) {
		super();
		this.seeds = seeds;
		this.metrics = metrics;
	}

	public void add(AntimicrobialPeptide amp, KmerHashMap map) {
		db.put(amp, map);
	}
	
	
	//This implementation is based on k-mer ranking
	public ClassifierResult[] classify(KmerHashMap query) {
		var res = new ArrayList<ClassifierResult>();

	
		db.keySet().forEach(e -> res.add(
				new ClassifierResult(e, metrics.getDistance(seeds, query, db.get(e))))
		);

		return res.stream()
				.filter(n -> n.metric() > 0)
				.sorted(metrics.getResultComparator())
				.toArray(ClassifierResult[]::new);
	}
	
	
	public ClassifierResult[] classifyByHash(KmerHashMap query) {
		List<ClassifierResult> res = new ArrayList<>();
		Set<AntimicrobialPeptide> amps = db.keySet();
		for (AntimicrobialPeptide amp : amps) {
			KmerHashMap khm = db.get(amp);
			
			long frequency = 0;
			long[] keys = query.keys();
			for (long key : keys) { 
				frequency += khm.get(key); 
			}
			
			if (frequency > 0) {
				res.add(new ClassifierResult(amp, Double.valueOf(frequency)));
			}
		}
		return res.stream()
				.filter(n -> n.metric() > 0)
				.sorted(metrics.getResultComparator())
				.toArray(ClassifierResult[]::new);
		
	}

}