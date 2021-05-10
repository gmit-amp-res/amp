package ie.gmit.bio;

public enum ReadingFrame {
	ONE (1),
	TWO (2),
	THREE (3),
	REVERSED_ONE (4),
	REVERSED_TWO (5),
	REVERSED_THREE (6);
	
	private final int frame;

    private ReadingFrame(int frame) {
        this.frame = frame;
    }
	
    public int getReadingFrame() {
        return this.frame;
    }
}