package info.zhwan;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.transform.FieldSet;

import info.zhwan.core.AggregateHolder;
import info.zhwan.core.Blank;
import info.zhwan.core.HeaderFilter;
import info.zhwan.core.ListFieldSetMapperHolder;
import lombok.Setter;

public class MultilineItemReader<T> implements ItemReader<T>, StepExecutionListener {

	@Setter
	private ItemReader<FieldSet> itemReader;
	
	@Setter
	private ListFieldSetMapperHolder listFieldSetMapperHolder;
	
	@Setter
	private HeaderFilter headerFilter;
	
	@Setter
	private String begin = "BEGIN";
	
	@Setter
	private String end = "END";
	
	private StepExecution stepExecution;
	
	@SuppressWarnings("unchecked")
	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		AggregateHolder holder = new AggregateHolder();
		ExecutionContext executionContext = stepExecution.getExecutionContext();
		while (process(itemReader.read(), holder)) {
			
			// BEGIN-END 내부 데이터가 많을 경우 bigin 정보가 없으므로 이에 대한 처리를 해 준다.
			if ("".equals(holder.getBegin()) || holder.getBegin() == null) {
				holder.setBegin(executionContext.get(begin).toString());
			}
			
			// 데이터가 1000건이 넘는다면, write 한다.
			if (holder.isOverSize()) {
				executionContext.put(begin, holder.getBegin());
				break;
			}
			
			continue;
		}

		if (!holder.isExhausted()) {
			holder.doFilterHead(headerFilter);
			
			if (holder.isBlank()) {
				return (T)new Blank();
			}
			
			Object object  = 
					listFieldSetMapperHolder
					.dataFieldSetMapper(holder.getBegin())
					.mapFieldSet(holder.getRecords());
			return (T)object;
		}
		else {
			return null;
		}
	}
	
	public boolean process(FieldSet fieldSet, AggregateHolder holder) {

		// finish processing if we hit the end of file
		if (fieldSet == null) {
			holder.setExhausted(true);
			return false;
		}
		
		// blank or CRLF
		if(fieldSet.getFieldCount() == 0) {
			return false;
		}
		
		String line = fieldSet.readString(0).toUpperCase();

		if(line.endsWith(this.begin)) {
			holder.setBegin(line);
			return true;
		}
		
		if(line.endsWith(this.end)) {
			return false;
		}
		
		holder.addRecord(fieldSet);
		return true;
	}
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}
}
