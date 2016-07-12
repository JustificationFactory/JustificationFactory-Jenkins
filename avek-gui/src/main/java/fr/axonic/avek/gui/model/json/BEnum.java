package fr.axonic.avek.gui.model.json;

import fr.axonic.avek.model.base.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class BEnum {
	List<BElem> range;
	String value;
	Format format;
	boolean editable;
	boolean mandatory;

	public BEnum() {}

	public List<String> getRange() {
		return range.stream().map(be -> be.name)
				.collect(Collectors.toList());
	}

	public void setRange(Object ... range) {
		List<BElem> ls = new ArrayList<>();

		for(Object o : range)
			ls.add(new BElem(o.toString()));

		this.range = ls;
	}
	public void setRange(List<String> range) {
		setRange(range.toArray());
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}


	private class BElem {
		String name;
		int ordinal;

		private BElem() {}

		public BElem(String s) {
			this.name = s;
			this.ordinal = -1;
		}
	}
}
