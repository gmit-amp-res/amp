package ie.gmit.bio.validate;

public class Metrics {
	public static final double getSpecificity(double tn, double fp) {
		return tn > 0.0d? tn/(tn + fp) : 0.0d;
	}

	public static final double getSensitivity(double tp, double fn) {
		return tp > 0.0d? tp/(tp + fn) : 0.0d;
	}

	public static final double getRecall(double tp, double fn) { //Recall = Sensitivity
		return getSensitivity(tp, fn);
	}

	public static final double getPrecision(double tp, double fp) { //Precision = TP/(TP+FP) 
		return tp > 0.0d? tp / (tp + fp) : 0.0d;
	}

	public static final double getAccurracy(double tp, double tn, double fp, double fn) {
		return tp > 0.0d? (tp + tn)/(tp + tn + fp + fn) : 0.0d;
	}

	//Matthews Correlation Coefficient. See MultiClassConfusionMatrix for the multiclass case
	public static final double getMCC(double tp, double tn, double fp, double fn) {
		if (tp == 0 || tn == 0 || fp == 0 || fn == 0) {
			return ((tp * tn) - (fp * fn));
		}else {
			return ((tp * tn) - (fp * fn))/Math.sqrt((tp + fp) * (tp + fn) * (tn + fp) * (tn + fn));
		}
	}

	
	/*
	 * F1-score = 2 × (precision × recall)/(precision + recall)
	 */
	public static final double getF1Score(double tp, double tn, double fp, double fn) {
		return tp > 0.0d? 2 * ((getPrecision(tp, fp) * getRecall(tp, fn))/((getPrecision(tp, fp) + getRecall(tp, fn)))) : 0.0d;  
	}

	
	
	/*
	 * Reference
	 * ----------------
	 * Jurman G, Riccadonna S, Furlanello C. A comparison of MCC and CEN error 
	 * measures in multi-class prediction. PloS one. 2012 Aug 8;7(8):e41882.
	 */
	public static final double getConfusionEntropy(double tp, double tn, double fp, double fn) {
		return tp > 0.0d? ((fn + fp) * (Math.log((Math.pow((tp + tn + fp + fn), 2.0d) - Math.pow((tp - tn), 2.0d))) / Math.log(2.0d))
				/ 2.0d * (tp+tn+fp+fn))
				
				-
				
				((fn * (Math.log(fn) / Math.log(2))) + (fp * (Math.log(fp) / Math.log(2)))
				/ (tp + tn + fp + fn))
		
		 : 0.0d;
	}
}