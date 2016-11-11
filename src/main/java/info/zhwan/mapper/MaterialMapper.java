package info.zhwan.mapper;
import java.util.List;

import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

import info.zhwan.core.ListFieldSetMapper;
import info.zhwan.domain.Material;

@Component
public class MaterialMapper implements ListFieldSetMapper<Material> {

	@Override
	public Material mapFieldSet(List<FieldSet> fieldSets) {

		FieldSet fieldSet = fieldSets.get(0);
		
		Material material = new Material();
		material.setMaterialId(fieldSet.readString(0));
		material.setRecipeId(fieldSet.readString(1));
		material.setItem1(fieldSet.readString(2));
		material.setItem2(fieldSet.readString(3));
		material.setItem3(fieldSet.readString(4));
		return material;
	}
	
	@Override
	public String name() {
		return "MATERIAL_DATA_BEGIN";
	}
}
