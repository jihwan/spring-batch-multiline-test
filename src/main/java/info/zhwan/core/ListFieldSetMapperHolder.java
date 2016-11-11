package info.zhwan.core;

public interface ListFieldSetMapperHolder {

	ListFieldSetMapper<?> dataFieldSetMapper(String name) throws UnsupportedListFieldMapperException;
}
