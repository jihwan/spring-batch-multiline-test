package info.zhwan.mapper;

import java.util.List;

import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import info.zhwan.core.ListFieldSetMapper;
import info.zhwan.domain.MachineMaterial;

@Component
public class MachineMaterialMapper implements ListFieldSetMapper<MachineMaterial> {
	
	@Override
	public MachineMaterial mapFieldSet(List<FieldSet> fieldSets) {
		
		FieldSet fieldSet = fieldSets.get(0);
		
		MachineMaterial machineMaterial = new MachineMaterial();
		machineMaterial.setMachineId(fieldSet.readString(0));
		machineMaterial.setMaterialId(fieldSet.readString(1));
		machineMaterial.setStartTime(fieldSet.readString(2));
		machineMaterial.setEndTime(fieldSet.readString(3));
		machineMaterial.setItem1(fieldSet.readString(4));
		machineMaterial.setItem2(fieldSet.readString(5));
		machineMaterial.setItem3(fieldSet.readString(6));
		return machineMaterial;
	}
	
	@Override
	public String name() {
		return "MACHINE_MATERIAL_DATA_BEGIN";
	}
}
