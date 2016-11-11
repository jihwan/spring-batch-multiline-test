package info.zhwan;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import info.zhwan.core.Blank;
import info.zhwan.domain.CommonInfo;
import info.zhwan.domain.Defect;
import info.zhwan.domain.Header;
import info.zhwan.domain.MachineMaterial;
import info.zhwan.domain.Material;

public class MultilineItemWriter<T> implements ItemWriter<T>, StepExecutionListener {
	
	protected StepExecution stepExecution;
	
	@Override
	public void write(List<? extends T> items) throws Exception {
		
		if ( stepExecution.getCommitCount() == 0 ) {
			stepExecution.getExecutionContext().put(CommonInfo.COMMON_INFO, new CommonInfo());
		}
		
		CommonInfo commonInfo = 
				CommonInfo.class.cast(stepExecution.getExecutionContext().get(CommonInfo.COMMON_INFO));
		
		System.err.println( "============== start ");
		for (T object : items) {
			if (object instanceof Blank) {
				continue;
			}
			
			if(object instanceof Header) {
				commonInfo.setHeader((Header)object);
				continue;
			}
			
			if(object instanceof Material) {
				commonInfo.setMaterial((Material)object);
				continue;
			}
			
			if(object instanceof MachineMaterial) {
				commonInfo.setMachineMaterial((MachineMaterial)object);
				// To Do Job
				System.err.println( commonInfo );
				continue;
			}
			
			if (object instanceof Defect) {
				Defect defect = Defect.class.cast(object);
				// To Do Job
				System.err.println( defect.size() + "\t"  +commonInfo );
				continue;
			}
		}
		System.err.println( "============== end ");
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
