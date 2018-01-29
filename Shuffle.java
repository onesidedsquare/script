package com.blangblue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
/**
 * 
 * @author steven.rogers
 *
 * QUADTRIBLE CHECK YOUR DATA BEFORE PROCESSING, RMF and STIG must align
 * But they will not always have the same number of controls
 * Output should have the same number line as in the RMF controls file
 *
 */
public class Shuffle {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Map<String, String> rmfMap = new HashMap<String, String>();
		final String newline = "\n";
		final String split = "###";
		try {
			
			File rmfFile = new File("C:\\Users\\user\\Desktop\\rmf_controls.csv");
			CSVReader rmfReader = new CSVReader(new FileReader(rmfFile));
			String[] rmfNextLine;
			while ((rmfNextLine = rmfReader.readNext()) != null) {
				String iaControl = rmfNextLine[0].trim();
				String note = rmfNextLine[1].trim();
				rmfMap.put(iaControl, note);
				
			}//while ((rmfNextLine = rmfReader.readNext()) != null) {
			
			System.out.println("Digested RMF Contorls");
			
			File stigFile = new File("C:\\Users\\user\\Desktop\\stig_controls.csv");
			CSVReader stigReader = new CSVReader(new FileReader(stigFile));
			String[] stigNextLine;
			while ((stigNextLine = stigReader.readNext()) != null) {
				String iaControl = stigNextLine[0].trim();
				String note = stigNextLine[1].trim();
				
				if(rmfMap.get(iaControl) != null){
					String dupeNote = rmfMap.get(iaControl);
					if(!note.contains(dupeNote)) {
						dupeNote = dupeNote + newline + note;
					} //if(!note.contains(dupeNote)) {
					rmfMap.replace(iaControl, dupeNote);
				  } //if(rmfMap.get(iaControl) != null){
				
			}//while ((stigNextLine = stigReader.readNext()) != null) {
			
			System.out.println("STIG controls shuffled into RMF controls");
			
			CSVWriter contorlsWriter = new CSVWriter(new FileWriter("controlsOutput.csv"), ',');
			Iterator<Entry<String, String>> entriesToDoc = rmfMap.entrySet().iterator();
			
			while (entriesToDoc.hasNext()) {
			  Entry<String, String> thisEntry = (Entry<String, String>) entriesToDoc.next();
			  String key = (String) thisEntry.getKey();
			  String value = (String) thisEntry.getValue();
			  String together = key + split + value;
			  contorlsWriter.writeNext(together.split(split));
			}//while (entriesToDoc.hasNext()) {
			
			contorlsWriter.close();
			System.out.println("see print out \"controlsOutput.csv\"");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//try

	}//public static void main(String[] args) {

}//public class Shuffle {
