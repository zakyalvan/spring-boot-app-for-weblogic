package com.jakartawebs.batch;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.jakartawebs.entity.Person;
import com.jakartawebs.service.PeopleService;

@Configuration
@EnableBatchProcessing
public class ImportPeopleJobConfiguration {
	@Bean
	ItemReader<Person> itemReader(LineMapper<Person> lineMapper) {
		FlatFileItemReader<Person> itemReader = new FlatFileItemReader<>();
		itemReader.setStrict(true);
		
		Resource flatFileResource = new ClassPathResource("people.csv");
		itemReader.setResource(flatFileResource);
		
		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}
	
	@Bean
	LineMapper<Person> lineMapper(LineTokenizer tokenizer, FieldSetMapper<Person> fieldSetMapper) {
		DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}
	
	@Bean
	LineTokenizer lineTokenizer() {
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setStrict(true);
		lineTokenizer.setNames(new String[] {"name", "email"});
		return lineTokenizer;
	}
	
	@Bean
	FieldSetMapper<Person> fieldSetMapper() {
		BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setStrict(true);
		fieldSetMapper.setTargetType(Person.class);
		return fieldSetMapper;
	}
	
	@Bean
	ItemWriter<Person> itemWriter(PeopleService peopleService) {
		return new ItemWriter<Person>() {
			@Override
			public void write(List<? extends Person> items) throws Exception {
				for(Person person : items) {
					peopleService.register(person);
				}
			}
		};
	}
	
	@Bean
	Step importStep(StepBuilderFactory steps, ItemReader<Person> itemReader, ItemWriter<Person> itemWriter) {
		return steps.get("import-people").<Person, Person>chunk(10).reader(itemReader).writer(itemWriter)
				//.faultTolerant().skipPolicy(new AlwaysSkipItemSkipPolicy())
				.build();
	}
	
	@Bean
	Job importJob(JobBuilderFactory jobs, Step step) {
		return jobs.get("import-job").start(step).build();
	}
}
