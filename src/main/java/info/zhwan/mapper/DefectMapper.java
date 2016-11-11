package info.zhwan.mapper;

import java.util.List;

import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import info.zhwan.core.ListFieldSetMapper;
import info.zhwan.domain.Defect;
import info.zhwan.domain.DefectItem;

@Component
public class DefectMapper implements ListFieldSetMapper<Defect> {

	@Override
	public Defect mapFieldSet(List<FieldSet> fieldSets) {
		
		Defect defect = new Defect();
		if(fieldSets.size() == 0) {
			return defect;
		}
		
		for (FieldSet fieldSet : fieldSets) {
			DefectItem item = new DefectItem();
			item.setMaterialId(fieldSet.readString(0));
			item.setX(fieldSet.readString(1));
			item.setY(fieldSet.readString(2));
			item.setJudge(fieldSet.readString(3));
			defect.add(item);
		}
		
		return defect;
	}

	@Override
	public String name() {
		return "DEFECT_DATA_BEGIN";
	}
}
