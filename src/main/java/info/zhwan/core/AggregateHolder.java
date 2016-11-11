package info.zhwan.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.transform.FieldSet;

import lombok.Getter;
import lombok.Setter;

public class AggregateHolder {

	@Getter
	private List<FieldSet> records = new ArrayList<>();
	
	// 탈진, 고갈
	@Getter @Setter
	private boolean exhausted = false;
	
	@Getter @Setter
	private String begin;
	
	public void addRecord(FieldSet record) {
		if ( record.getFieldCount() == 0 ) {
			return;
		}
		this.records.add(record);
	}
	
	public void doFilterHead(HeaderFilter headerFilter) {
		
		if (this.records.size() == 0) {
			return;
		}
		
		String prefix = this.records.get(0).readString(0);
		
		if (headerFilter.contains(prefix)) {
			this.records.remove(0);
		}
	}
	
	public boolean isOverSize() {
		return records.size() >= 1000 ? true : false;
	}

	public boolean isBlank() {
		if (getRecords().size() == 0 && ("".equals(begin) || begin == null)) {
			return true;
		}
		
		return false;
	}
}
