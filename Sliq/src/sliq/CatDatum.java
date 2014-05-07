package sliq;

public class CatDatum implements Datum {
	private int rowIndex;
	private String value;
	
	public CatDatum(int rowIndex, String value) {
		this.rowIndex = rowIndex;
		this.value = value;
	}

	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public int compareTo(Datum otherDatum) {
		if (otherDatum.getClass() != this.getClass()) {
			return 2;
		}
		return this.value.compareTo((String) otherDatum.getValue());
	}
}
