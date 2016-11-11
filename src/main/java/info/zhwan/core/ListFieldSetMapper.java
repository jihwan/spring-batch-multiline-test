package info.zhwan.core;
import java.util.List;

import org.springframework.batch.item.file.transform.FieldSet;

public interface ListFieldSetMapper<T> {
	
	T mapFieldSet(List<FieldSet> fieldSets);
	
	String name();
}
