package br.edu.fa7.clientes.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.fa7.clientes.model.Cliente;
import br.edu.fa7.clientes.model.Endereco;

@Service
public class ClienteService {
	
	@Autowired
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Cliente> findAll() {
		return entityManager.createQuery("from Cliente").getResultList();
	}

	public Cliente find(Long id) {
		return entityManager.find(Cliente.class, id);
	}

	@Transactional
	public void save(Cliente cliente) {
		entityManager.persist(cliente);
	}

	@Transactional
	public void update(Long id, Cliente cliente) {
		cliente.setId(id);
		entityManager.merge(cliente);
	}

	@Transactional
	public void delete(Long id) {
		Cliente cliente = find(id);
		entityManager.remove(cliente);
	}

	@SuppressWarnings("unchecked")
	public List<Endereco> findEnderecosByCliente(Long id) {
		Query query = entityManager.createQuery("select c.enderecos from Cliente c where c.id=:idCliente");
		query.setParameter("idCliente", id);
		return query.getResultList();
	}

	public Endereco findEnderecoByCliente(Long id, Long eid) {
		Query query = entityManager.createQuery("select e from Cliente c join c.enderecos e where c.id=:idCliente and e.id=:id");
		query.setParameter("idCliente", id);
		query.setParameter("id", eid);
		return (Endereco) query.getSingleResult();
	}
	
}
