package info.zhwan;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.PatternMatchingCompositeLineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import info.zhwan.core.HeaderFilter;
import info.zhwan.core.ListFieldSetMapperHolder;

/**
 * 
 * 의아 스럽지만, {@link Configuration}을 해야 만 {@link EnableBatchProcessing}에 등록된 batch bean들이 등록된다.
 * @author zhwan
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	ListFieldSetMapperHolder listFieldSetMapperHolder;
	
	@Bean
	public <T>ItemReader<T> reader() {
		
		HeaderFilter headerFilter = new HeaderFilter() {
			private Set<String> prefixes = 
					Stream.of("MATERIAL_ID", "MACHINE_ID")
					.collect(Collectors.toSet());
			@Override
			public boolean contains(String prefix) {
				return prefixes.contains(prefix);
			}
		};
		
		MultilineItemReader<T> reader = new MultilineItemReader<>();
		reader.setItemReader(fileItemReader());
		reader.setListFieldSetMapperHolder(listFieldSetMapperHolder);
		reader.setHeaderFilter(headerFilter);
		return reader;
	}
	
	@SuppressWarnings({ "serial", "unchecked" })
	@Bean
	public <T> FlatFileItemReader<T> fileItemReader() {
		FlatFileItemReader<T> reader = new FlatFileItemReader<T>();
		reader.setResource(new FileSystemResource("./src/test/resources/big-multiline.csv"));
		reader.setLineMapper(new DefaultLineMapper<T>() {
			{
				setLineTokenizer(new PatternMatchingCompositeLineTokenizer() {{
						setTokenizers(new HashMap<String, LineTokenizer>() {{
								put("HEADER_BEGIN", new FixedLengthTokenizer() {{
									setColumns(new Range[] { new Range(1, 12) });
								}});
								put("HEADER_END", new FixedLengthTokenizer() {{
									setColumns(new Range[] { new Range(1, 10) });
								}});
								put("MATERIAL_DATA_BEGIN", new FixedLengthTokenizer() {{
									setColumns(new Range[] { new Range(1, 19) });
								}});
								put("MATERIAL_DATA_END", new FixedLengthTokenizer() {{
									setColumns(new Range[] { new Range(1, 17) });
								}});
								put("MACHINE_MATERIAL_DATA_BEGIN", new FixedLengthTokenizer() {{
									setColumns(new Range[] { new Range(1, 27) });
								}});
								put("MACHINE_MATERIAL_DATA_END", new FixedLengthTokenizer() {{
									setColumns(new Range[] { new Range(1, 25) });
								}});
								put("DEFECT_DATA_BEGIN", new FixedLengthTokenizer() {{
									setColumns(new Range[] { new Range(1, 17) });
								}});
								put("DEFECT_DATA_END", new FixedLengthTokenizer() {{
									setColumns(new Range[] { new Range(1, 15) });
								}});
								put("*", new DelimitedLineTokenizer());
							}
						});}
				});
				setFieldSetMapper((FieldSetMapper<T>) new PassThroughFieldSetMapper());
			}
		});
		return reader;
	}
	
	@Bean
    public <T>ItemWriter<T> writer() {
        return new MultilineItemWriter<>();
    }
	
	@Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("multilineJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .chunk(1)
                .reader(reader())
                .writer(writer())
                .stream(fileItemReader())
                .build();
    }
}
