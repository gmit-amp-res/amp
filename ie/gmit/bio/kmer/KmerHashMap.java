package ie.gmit.bio.kmer;

import java.io.Serializable;
import java.util.*;

import ie.gmit.bio.kmer.metrics.KmerFrequency;
public class KmerHashMap implements Serializable{
	private static final long serialVersionUID = 777L;
	private float loadFactor;
	private long[] seeds;	
	private KmerMapEntry[] buckets;
	private KmerMapEntry head;
	private KmerMapEntry entry;
	private int bucketIndex;
	private int size;
	
	public KmerHashMap(long[] seeds) {
		this(16, seeds, 0.99f);
	}

	public KmerHashMap(long[] seeds, float loadFactor) {
		this(16, seeds, loadFactor);
	}
	
	public KmerHashMap(int capacity, long[] seeds, float loadFactor) {
		super();
		this.seeds = seeds;
		this.loadFactor = loadFactor;
		buckets = new KmerMapEntry[capacity];
	}
	
	public long get(long key){
		long frequency = 0;
		for (long seed : seeds) {
			bucketIndex = hash(key, seed) % buckets.length;
			if (bucketIndex < 0) bucketIndex = -bucketIndex;
			entry = buckets[bucketIndex];
			
			if (entry != null){
				do{
					if (isEqual(key, entry.getKey(), seed)) {
						return entry.getFrequency();
					}
					entry = entry.getNext();
				}while(entry != null);
			}
		}
		return frequency;
	}
	
	public void put(long key, long frequency){
		put(buckets, key, frequency);
	}
	
	private void put(KmerMapEntry[] entries, long key, long frequency){
		if (entries == buckets) checkCapacity();
		
		for (long seed : seeds) {
			bucketIndex = hash(key, seed) % entries.length;
			if (bucketIndex < 0) bucketIndex = -bucketIndex;
			
			head = entries[bucketIndex];
			entry = head;
			
			if (entry == null){ //New insertion
				entries[bucketIndex] = new KmerMapEntry(this, key, frequency);
				if (entries == buckets) size++;
				return;
			}else{
				do{
					if (isEqual(entry.getKey(), key, seed)) {
						entry.setValue(frequency); 
						return;
					}
					
					if (!entry.hasNext()){ 
						entry.setNext(new KmerMapEntry(this, key, frequency));
						if (entries == buckets) size++;
						return;
					}
					entry = entry.getNext();
				}while(entry != null);
			}
		}
	}
	
	public long[] keys() {
		long[] keys = new long[size];
		int index = 0;
		for (KmerMapEntry entry : buckets) {
			while (entry != null) {
				keys[index] = entry.getKey();
				index++;
				
				entry = entry.getNext();
			}
		}
		return keys;
	}
	
	private List<KmerFrequency> getSortedKmerFrequencies() {
		List<KmerFrequency> list = new ArrayList<>();
		for (KmerMapEntry entry : buckets) {
			while (entry != null) {
				list.add(new KmerFrequency(entry.getKey(), entry.getFrequency()));
				entry = entry.getNext();
			}
		}
		return list;
	}
	
	public KmerFrequency[] getSortedKmerFrequencies(int limit) {
		return getSortedKmerFrequencies()
				.stream()
				.sorted((n, m) -> -Long.compare(n.frequency(), m.frequency()))
				.limit(limit)
				.toArray(KmerFrequency[]::new);
	}

	private void checkCapacity() {
		if (size / buckets.length > loadFactor) resize();
	}
	
	private void resize() {
		KmerMapEntry[] tmp = new KmerMapEntry[(buckets.length * 3) / 2];

		for (KmerMapEntry entry : buckets) {
			if (entry != null) {
				put(tmp, entry.getKey(), entry.getFrequency());
			}
		}
		buckets = tmp;
	}
	
	private int hash(long key, long seed){
		int hash = 1;
		int shift = 2;
		int radix = 1 << shift;
		long mask = radix - 1;
		long maskedKey = key & seed;
		
		int counter = 1;
		do{
			hash = 31 * hash + (counter * (int)(maskedKey & mask));
			maskedKey >>>= shift;
			counter++;
		}while (maskedKey != 0);

		hash ^= (hash >>> 20) ^ (hash >>> 12);		
		return hash ^ (hash >>> 7) ^ (hash >>> 4);
	}
	
	private boolean isEqual(long s, long t, long seed){
		return (s & seed) == (t & seed);
	}
	
	public int getCapacity(){
		return buckets.length;
	}

	public int size(){
		return this.size;
	}

	public void clear(){
		buckets = new KmerMapEntry[1];
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		for (KmerMapEntry entry : buckets) {
			while (entry != null) {
				sb.append(entry.getKey() + "=" + entry.getFrequency() + ",");
				entry = entry.getNext();
			}
		}
		sb.append("}");
		return sb.toString();
	}

	private class KmerMapEntry implements Serializable{
		private static final long serialVersionUID = 777L;
		private long key;
		private long frequency;
		private KmerMapEntry next;
		
		public KmerMapEntry(KmerHashMap kmerHashMap, long key, long frequency) {
			super();
			this.key = key;
			this.frequency = frequency;
		}
		
		public long getKey() {
			return key;
		}
		
		public long getFrequency() {
			return frequency;
		}
		
		public void setValue(long frequency) {
			this.frequency = frequency;
		}

		public KmerMapEntry getNext() {
			return next;
		}

		public void setNext(KmerMapEntry next) {
			this.next = next;
		}

		public boolean hasNext(){
			return next != null;
		}
	}
}