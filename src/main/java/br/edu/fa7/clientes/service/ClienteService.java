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
		carregarEnderecos(cliente);
		entityManager.persist(cliente);
	}

	private void carregarEnderecos(Cliente cliente) {
		if(cliente.getEnderecos() != null) {
			for(Endereco endereco : cliente.getEnderecos()) {
				endereco.setCliente(cliente);
			}
		}
	}

	@Transactional
	public void update(Long id, Cliente cliente) {
		cliente.setId(id);
		carregarEnderecos(cliente);
		entityManager.merge(cliente);
	}

	@Transactional
	public void delete(Long id) {
		Cliente cliente = find(id);
		entityManager.remove(cliente);
	}

	@SuppressWarnings("unchecked")
	public List<Endereco> findEnderecosByCliente(Long id) {
		Query query = entityManager.createQuery("from Endereco e where e.cliente.id=:idCliente");
		query.setParameter("idCliente", id);
		return query.getResultList();
	}

	public Endereco findEnderecoByCliente(Long id, Long eid) {
		Query query = entityManager.createQuery("from Endereco e where e.cliente.id=:idCliente and e.id=:id");
		query.setParameter("idCliente", id);
		query.setParameter("id", eid);
		return (Endereco) query.getSingleResult();
	}
	
}
