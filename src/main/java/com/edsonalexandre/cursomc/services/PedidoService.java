package com.edsonalexandre.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.edsonalexandre.cursomc.domain.Cliente;
import com.edsonalexandre.cursomc.domain.ItemPedido;
import com.edsonalexandre.cursomc.domain.PagamentoComBoleto;
import com.edsonalexandre.cursomc.domain.Pedido;
import com.edsonalexandre.cursomc.domain.enuns.EstadoPagamento;
import com.edsonalexandre.cursomc.repositories.ItemPedidoRepository;
import com.edsonalexandre.cursomc.repositories.PagamentoRepository;
import com.edsonalexandre.cursomc.repositories.PedidoRepository;
import com.edsonalexandre.cursomc.security.UserSS;
import com.edsonalexandre.cursomc.services.exceptions.AuthorizationException;
import com.edsonalexandre.cursomc.services.exceptions.ObjectNotFoundExcepition;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundExcepition(
				"Objeto não encontrado. Id: "+id+" Tipo: "+Pedido.class.getName()
				));
	}
	
	public Pedido inserir(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);		
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamento(pagto, obj.getInstante());			
		}
		repository.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido item : obj.getItens()) {
			item.setDesconto(0.0);
			item.setProduto(produtoService.find(item.getProduto().getId()));
			item.setPreco(item.getProduto().getPreco());			
			item.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
		UserSS user= UserService.authenticated();
		
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Cliente cliente = clienteService.find(user.getId());
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
		return repository.findByCliente(cliente, pageRequest);
	}
	
}
