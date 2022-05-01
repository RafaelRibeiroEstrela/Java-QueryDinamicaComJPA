package com.example.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.models.Pessoa;


@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	@Query("SELECT SUBSTRING(pe.dataNascimento, 6, 2) FROM Pessoa pe ")
	List<String> getResult();
	
	//SUBSTRING(string, posicaoInicial, quantidadeDeElementos)
	
	//Utilizando filtros v1
	@Query("SELECT pe FROM Pessoa pe "
			+ "WHERE (:nome IS NULL OR UPPER(pe.nome) LIKE '%' || UPPER(:nome) || '%') "
			+ "AND (:email IS NULL OR pe.email = :email) "
			+ "AND (:cpf IS NULL OR pe.cpf = :cpf) "
			+ "AND (:ano IS NULL OR SUBSTRING(pe.dataNascimento, 1, 4) = :ano) "
			+ "AND (:mes IS NULL OR SUBSTRING(pe.dataNascimento, 6, 2) = :mes) ")
	List<Pessoa> findByParam(String nome, String email, String cpf, String ano, String mes);

	//Utilizando filtros v2
	default List<Pessoa> findByParam(EntityManager em, String nome, String email, String cpf, String ano, String mes) {

		String query = "SELECT pe FROM Pessoa pe ";

		String condicao = "WHERE ";

		if (nome != null && !nome.isEmpty()) {
			query += condicao + "pe.nome like %:nome% ";
			condicao = "AND ";
		}

		if (email != null && !email.isEmpty()) {
			query += condicao + "LOWER(pe.email) = LOWER(:email) ";
			condicao = "AND ";
		}

		if (cpf != null && !cpf.isEmpty()) {
			query += "pe.cpf = :cpf ";
			condicao = "AND ";
		}
		
		if (ano != null && !ano.isEmpty()) {
			query += condicao + "SUBSTRING(pe.dataNascimento, 1, 4) = :ano ";
			condicao = "AND ";
		}
		
		if (mes != null && !mes.isEmpty()) {
			query += condicao + "SUBSTRING(pe.dataNascimento, 6, 2) = :mes ";
			condicao = "AND ";
		}
		
		TypedQuery<Pessoa> pq = em.createQuery(query, Pessoa.class);
		
		if (nome != null && !nome.isEmpty()) {
			pq.setParameter("nome", nome);
		}
		
		if (email != null && !email.isEmpty()) {
			pq.setParameter("email", email);
		}
		
		if (cpf != null && !cpf.isEmpty()) {
			pq.setParameter("cpf", cpf);
		}
		
		if (ano != null && !ano.isEmpty()) {
			pq.setParameter("ano", ano);
		}
		
		if (mes != null && !mes.isEmpty()) {
			pq.setParameter("mes", mes);
		}

		return pq.getResultList();

	}

}
