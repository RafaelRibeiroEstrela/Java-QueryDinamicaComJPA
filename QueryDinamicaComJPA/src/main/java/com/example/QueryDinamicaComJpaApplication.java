package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.models.Pessoa;
import com.example.repositories.PessoaRepository;

@SpringBootApplication
public class QueryDinamicaComJpaApplication implements CommandLineRunner{
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private EntityManager em;

	public static void main(String[] args) {
		SpringApplication.run(QueryDinamicaComJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Pessoa pessoa1 = new Pessoa(1L, "Carlos", "carlos@gmail.com", "04457898741", LocalDate.parse("10/10/1990", dtf));
		Pessoa pessoa2 = new Pessoa(2L, "Maria", "maria@gmail.com", "04457898742", LocalDate.parse("15/08/1995", dtf));
		Pessoa pessoa3 = new Pessoa(3L, "Laura", "laura@gmail.com", "04457898743", LocalDate.parse("28/04/2001", dtf));
		
		pessoaRepository.saveAll(Arrays.asList(pessoa1, pessoa2, pessoa3));
		
		//System.out.println(pessoaRepository.findByParam(em, null, pessoa1.getEmail(), null));
		System.out.println(pessoaRepository.findByParam(em, null, null, null, null, "08"));
		
		System.out.println(pessoaRepository.getResult());
		
		
	}

}
