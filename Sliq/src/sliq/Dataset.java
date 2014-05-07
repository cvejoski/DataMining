package sliq;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Dataset {
	
	private String[] columnNames;
	private String[] columnTypes;
	private ArrayList<ArrayList<Datum>> data;
	private boolean[] targets;
	
	
	Dataset(String fileName) throws Exception {
		readData(fileName);
		sortNumericColumns();
	}
	
	private void readData(String fileName) throws Exception {
		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(fileName), Charset.defaultCharset());
		int recordsCount = lines.size() - 1;
		int attrCount = lines.get(0).split(",").length - 1;
		
		columnNames = new String[attrCount + 1];
		columnTypes = new String[attrCount];
		data = new ArrayList<ArrayList<Datum>>(attrCount);
		targets = new boolean[recordsCount];
		
		processFileHeader(lines.get(0), recordsCount);
		
		for (int i = 1, ii = lines.size(); i < ii; ++i) {
			processFileDataLine(lines.get(i), i);
		}
	}
	
	private void processFileHeader(String header, int linesCount) throws Exception {
		String[] attributes = header.split(",");
		
		for (int i = 0, ii = attributes.length - 1; i < ii; ++i) {
			String[] temp = attributes[i].split(":");
			
			columnNames[i] = temp[0];
			if (temp[1].compareTo("n") == 0 &&
					temp[1].compareTo("c") == 0 &&
					temp[1].compareTo("b") == 0 &&
					temp[1].compareTo("t") == 0) {
				throw new Exception("Invalid data type declaration in file header: \"" + temp[1] + "\".\n");
			}
			
			data.add(new ArrayList<Datum>(linesCount));
			
			columnTypes[i] = temp[1];
		}
		
		String[] temp = attributes[attributes.length - 1].split(":");
		columnNames[columnNames.length - 1] = temp[0];
	}
	
	private void processFileDataLine(String line, int lineNumber) throws Exception {
		String[] values = line.split(",");
		
		if (values.length != columnTypes.length + 1) {
			throw new Exception("Incompatible data in file at line " + lineNumber + ".\n");
		}
		
		for (int i = 0, ii = values.length - 1; i < ii; ++i) {
			switch (columnTypes[i]) {
			case "n":
				data.get(i).add(new NumDatum(lineNumber, Double.parseDouble(values[i])));
				break;
			case "c":
				data.get(i).add(new CatDatum(lineNumber, values[i]));
				break;
			case "b":
				data.get(i).add(new BinDatum(lineNumber, values[i].compareTo("yes") == 0 ? true : false));
				break;
			}
		}
		targets[targets.length - 1] = values[values.length - 1].compareTo("yes") == 0 ? true : false;
	}
	
	private void sortNumericColumns() {
		for (int i = 0, ii = data.size(); i < ii; ++i) {
			if (columnTypes[i].compareTo("n") == 0) {
				Collections.sort(data.get(i));
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuffer strb = new StringBuffer();
		
		for (int j = 0, jj = columnNames.length; j < jj; ++j) {
			String type = columnTypes.length > j ? columnTypes[j] : "t";
			strb.append(columnNames[j] + ":" + type + ",");
		}
		
		strb.append("\n");
		
		for (int i = 0, ii = targets.length; i < ii; ++i) {
			for (int j = 0, jj = data.size(); j < jj; ++j) {
				strb.append(data.get(j).get(i).getValue() + ",");
			}
			strb.append("\n");
		}
		
		return strb.toString();
	}

	public static void main(String[] args) throws Exception {
		Dataset d = new Dataset("data_exercise_2.csv");
		System.out.print(d);
	}

}
