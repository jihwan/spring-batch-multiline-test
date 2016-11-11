package info.zhwan.mapper;
import java.util.List;

import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import info.zhwan.core.ListFieldSetMapper;
import info.zhwan.domain.Header;

@Component
public class HeaderMapper implements ListFieldSetMapper<Header> {
	
	private final String DELIMITER = ":";

	@Override
	public Header mapFieldSet(List<FieldSet> fieldSets) {
		
		Header header = new Header();
		
		for (int i = 0; i < fieldSets.size(); i++) {
			String item = StringUtils.delimitedListToStringArray(fieldSets.get(i).readString(0), DELIMITER)[0].trim();
			String itemVal = StringUtils.delimitedListToStringArray(fieldSets.get(i).readString(0), DELIMITER)[1].trim();
			
			if("MACHINE_TYPE".equals(item)) {
				header.setMachineType(itemVal);
			}
			else if("MACHINE_ID".equals(item)) {
				header.setMachineId(itemVal);
			}
			else if("FILE_CREATED_TIME".equals(item)) {
				header.setFileCreatedTime(itemVal);
			}
		}
		
		return header;
	}

	@Override
	public String name() {
		return "HEADER_BEGIN";
	}
}
