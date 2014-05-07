package sliq;

public interface Datum extends Comparable<Datum> {
	int getRowIndex();
	Object getValue();
	int compareTo(Datum otherDatum);
}
