package com.example.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Pessoa;


@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	default List<Pessoa> findByParam(EntityManager em, String nome, String email, String cpf) {

		String query = "SELECT pe FROM Pessoa pe ";

		String condicao = "WHERE ";

		if (nome != null && !nome.isBlank()) {
			query += condicao + "pe.nome like %:nome% ";
			condicao = "AND ";
		}

		if (email != null && !email.isBlank()) {
			query += condicao + "LOWER(pe.email) = LOWER(:email)";
			condicao = "AND ";
		}

		if (cpf != null && !cpf.isBlank()) {
			query += "pe.cpf = :cpf ";
			condicao = "AND ";
		}
		
		TypedQuery<Pessoa> pq = em.createQuery(query, Pessoa.class);
		
		if (nome != null && !nome.isBlank()) {
			pq.setParameter("nome", nome);
		}
		
		if (email != null && !email.isBlank()) {
			pq.setParameter("email", email);
		}
		
		if (cpf != null && !cpf.isBlank()) {
			pq.setParameter("cpf", cpf);
		}

		return pq.getResultList();

	}

}
