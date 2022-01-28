package com.example;

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
		
		Pessoa pessoa1 = new Pessoa(1L, "Carlos", "carlos@gmail.com", "05616139157");
		Pessoa pessoa2 = new Pessoa(2L, "Maria", "maria@gmail.com", "05616139156");
		Pessoa pessoa3 = new Pessoa(3L, "Laura", "laura@gmail.com", "05616139155");
		
		pessoaRepository.saveAll(Arrays.asList(pessoa1, pessoa2, pessoa3));
		
		System.out.println(pessoaRepository.findByParam(em, null, pessoa1.getEmail(), null));
		
		
		
		
	}

}
