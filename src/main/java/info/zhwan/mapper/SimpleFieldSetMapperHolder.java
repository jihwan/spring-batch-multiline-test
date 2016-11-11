package info.zhwan.mapper;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.zhwan.core.ListFieldSetMapper;
import info.zhwan.core.ListFieldSetMapperHolder;
import info.zhwan.core.UnsupportedListFieldMapperException;
import lombok.extern.slf4j.Slf4j;

@Component @Slf4j
public class SimpleFieldSetMapperHolder implements ListFieldSetMapperHolder {
	
	private Map<String, ListFieldSetMapper<?>> map = new HashMap<>();
	
	@Autowired
	public SimpleFieldSetMapperHolder(ListableBeanFactory beanFactory) {
		
		@SuppressWarnings("rawtypes")
		Map<String, ListFieldSetMapper> dataFieldSetMappers = 
				BeanFactoryUtils.beansOfTypeIncludingAncestors(beanFactory, ListFieldSetMapper.class);
	
		for(ListFieldSetMapper<?> dataFieldSetMapper : dataFieldSetMappers.values()) {
			map.put(dataFieldSetMapper.name(), dataFieldSetMapper);
		}
	}
	
	@Override
	public ListFieldSetMapper<?> dataFieldSetMapper(String name) throws UnsupportedListFieldMapperException {
		
		if (!map.containsKey(name)) {
			log.error("{} is not support", name);
			throw new UnsupportedListFieldMapperException(name + " is not support");
		}

		return map.get(name);
	}
}
