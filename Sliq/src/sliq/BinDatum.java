package sliq;

public class BinDatum implements Datum {
	private int rowIndex;
	private Boolean value;
	
	public BinDatum(int rowIndex, boolean value) {
		this.rowIndex = rowIndex;
		this.value = value;
	}
	
	@Override
	public int getRowIndex() {
		return rowIndex;
	}
	
	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public int compareTo(Datum otherDatum) {
		if (otherDatum.getClass() != this.getClass()) {
			return 2;
		}
		return this.value.compareTo((Boolean) otherDatum.getValue());
	}
}
