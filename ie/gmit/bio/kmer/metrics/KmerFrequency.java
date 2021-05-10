package ie.gmit.bio.kmer.metrics;

import java.io.Serializable;

@SuppressWarnings("preview")
public record KmerFrequency (long key, long frequency) implements Serializable{
	private static final long serialVersionUID = 777L;
}