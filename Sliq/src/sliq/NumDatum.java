package sliq;

public class NumDatum implements Datum {
	private int rowIndex;
	private Double value;
	
	public NumDatum(int rowIndex, double value) {
		this.rowIndex = rowIndex;
		this.value = value;
	}
	
	@Override
	public int getRowIndex() {
		return rowIndex;
	}
	
	@Override
	public Double getValue() {
		return value;
	}
	
	@Override
	public int compareTo(Datum otherDatum) {
		if (otherDatum.getClass() != this.getClass()) {
			return 2;
		}
		return this.value.compareTo((Double) otherDatum.getValue());
	}
}
