package ie.gmit.bio.validate;

import java.text.DecimalFormat;
import java.util.Arrays;

import ie.gmit.bio.AntimicrobialPeptide;

public class MultiClassConfusionMatrix{
	private double[][] matrix;
	private CharSequence[] amps;
	private DecimalFormat df = new DecimalFormat("##0.00#");
	private double matrixTotal = Double.MIN_VALUE;
	private double matrixMCC = Double.MIN_VALUE;


	public MultiClassConfusionMatrix() {
		amps = Arrays.stream(AntimicrobialPeptide.values()).map(n -> n.name()).sorted(String::compareTo).toArray(String[]::new);
		matrix = new double[amps.length][amps.length];
	}

	public synchronized void update(CharSequence predicted, CharSequence actual) {
		matrix[getIndex(predicted)][getIndex(actual)]++;
	}
	
	private ConfusionMatrixElement[] process() {
		this.matrixTotal = this.getMatrixTotal();
		
		ConfusionMatrixElement[] elements = new ConfusionMatrixElement[amps.length];
		for (int index = 0; index < amps.length; index++) {
			elements[index] = new ConfusionMatrixElement(amps[index], index);

		}
		
		computeMultiClassMCC(elements); //Compute the MCC scores for each class
		
		return elements;
	}
	
	/*	The Matthews Correlation Coefficient is given by 
	     MCC =                 C * S - (Tvec * Pvec) 
	           ----------------------------------------------------
	           SQRT(S^2 = (Pvec * Pvec) * SQRT(S^2 = (Tvec * Tvec)
	     
	     S = The total number of samples, i.e. the sum of the matrix or matrixTotal
	     C = The number of times each class was predicted, i.e. all the TPs
	     Pvec = The number of times the specified class was predicted, i.e. the class's TPs + FNs
	     Tvec = The number of times the specified class actually occurred, i.e. the class's TPs + FPs
	 */
	private void computeMultiClassMCC(ConfusionMatrixElement[] elements) {
		double Sq = matrixTotal * matrixTotal;
		double CS = Arrays.stream(elements).map(n -> n.tp).reduce(0.0d, (n, m) -> n + m) * matrixTotal;
		double t = 0.0d, p = 0.0d, tt = 0.0d, pp = 0.0d, tp = 0.0d;
		
		for (ConfusionMatrixElement e : elements) {
			t = (e.tp + e.fp); 
			p = (e.tp + e.fn);
			
			tt += (t * t);
			pp += (p * p);
			tp += (t * p);
		}
		matrixMCC = (CS - tp) / (Math.sqrt(Sq - pp) * Math.sqrt(Sq - tt));
	}
	
	private double getMatrixTotal() {
		double total = 0;
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				total += matrix[row][col];
			}
		} 
		return total;
	}
	
	private int getIndex(CharSequence amp) {
		return Arrays.binarySearch(amps, amp);
	}
	
	public void debug() {
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				System.out.print(matrix[row][col] + ",");
			}
			System.out.println();
		}
	}
	
	public MatrixStats getMatrixStats() {
		double tp = 0, fp = 0, tn = 0, fn = 0, tempf = 0, mf1 = 0, wf1 = 0, samples = 0;
		
		ConfusionMatrixElement[] elements = process();
		for (ConfusionMatrixElement e : elements) {
			tp += e.tp;
			fp += e.fp;
			tn += e.tn;
			fn += e.fn;
			
			tempf = Metrics.getF1Score(e.tp, e.tn, e.fp, e.fn);
			wf1 += (tempf * (e.tp + e.fn));
			samples += (e.tp + e.fn);
			
			mf1 += tempf;
		}
		
		mf1 = mf1 / (double) elements.length;
		wf1 = wf1 / (double) samples;
		
		return new MatrixStats(tp, fp, tn, fn, matrixMCC, mf1, wf1);
	}
	
	public static record MatrixStats (double tp, double fp, double tn, double fn, double  mcc, double mf1, double wf1) {}
	
	public String toString(AntimicrobialPeptideStats[] ampRecords) {
		StringBuilder sb = new StringBuilder();
		
		MultiClassConfusionMatrix.MatrixStats stats = getMatrixStats(); 
		sb.append("MacroSensitivity,MacroSpecificity,MacroAccurracy,MultiClass-MCC,MacroF1,MacroWF1,MacroTP,MacroTN,MacroFP,MacroFN,,,\n");
		sb.append(
				df.format(Metrics.getSensitivity(stats.tp(), stats.fn())) + "," + 
				df.format(Metrics.getSpecificity(stats.tn(), stats.fp())) + "," + 
				df.format(Metrics.getAccurracy(stats.tp(), stats.tn(), stats.fp(), stats.fn())) + "," + 
				df.format(stats.mcc()) + "," +
				df.format(stats.mf1()) + "," +
				df.format(stats.wf1()) + "," +
				df.format(stats.tp()) + "," +
				df.format(stats.tn()) + "," + 
				df.format(stats.fp()) + "," + 
				df.format(stats.fn()) + "\n"
		);
		
		sb.append("Label,TP,FP,TN,FN,Specificity,Sensitivity,Precision,Accurracy,MCC,F1 Score,#Samples,Average Length\n");
		
		ConfusionMatrixElement[] elements = process();
		for (ConfusionMatrixElement e : elements) {
			AntimicrobialPeptideStats stat = getStats(ampRecords, e.label);
			sb.append(e);
			
			if (stat != null) {
				sb.append(Double.valueOf(stat.samples()).intValue() + ",");
				sb.append(Double.valueOf(stat.averageLength()).intValue());
			}else {
				sb.append("0,0.0");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	//Ugly, but works and will do fine for a small number of categories...
	private AntimicrobialPeptideStats getStats(AntimicrobialPeptideStats[] records, CharSequence label) {
		for (AntimicrobialPeptideStats apn : records) {
			if (apn.amp().name().equals(label)) {
				return apn;
			}
		}
		return null;
	}
		
	private class ConfusionMatrixElement{
		private CharSequence label;
		private int index;
		private double tp, fp, tn, fn, mcc;
		
		public ConfusionMatrixElement(CharSequence label, int index) {
			this.label = label;
			this.index = index;
			
			computeTP();
			computeFN();
			computeFP();
			computeTN();
		}
		
		private void computeTP() {
			tp = matrix[index][index];
		}
		
		private void computeFN() {
			for (int row = 0; row < matrix.length; row++) {
				if (row != index) fn += matrix[row][index];
			}
		}
		
		private void computeFP() {
			for (int col = 0; col < matrix[index].length; col++) {
				if (col != index) fp += matrix[index][col];
			}
		}
		
		private void computeTN() {
			tn = matrixTotal - (tp + fn + fp); 
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(label + ",");
			sb.append(tp + "," + fp + "," + tn + "," + fn + ",");
			sb.append(df.format(Metrics.getSpecificity(tn, fp)) + ",");
			sb.append(df.format(Metrics.getSensitivity(tp, fn)) + ",");
			sb.append(df.format(Metrics.getPrecision(tp, fp)) + ",");
			sb.append(df.format(Metrics.getAccurracy(tp, tn, fp, fn)) + ",");
			sb.append(df.format(mcc) + ",");
			sb.append(df.format(Metrics.getF1Score(tp, tn, fp, fn)) + ",");
			//sb.append(df.format(Metrics.getConfusionEntropy(tp, tn, fp, fn)) + ",");
			return sb.toString();
		}
	}
	
	public static void main(String[] args) {
		new MultiClassConfusionMatrix();
	}
}