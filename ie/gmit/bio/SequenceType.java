package ie.gmit.bio;

public enum SequenceType {
	NUCLEOTIDE (1),
	PROTEIN (2);
	
	private final int type;

    private SequenceType(int frame) {
        this.type = frame;
    }
	
    public int getSequenceType() {
        return this.type;
    }
}