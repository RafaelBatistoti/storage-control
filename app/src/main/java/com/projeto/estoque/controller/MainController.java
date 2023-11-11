package com.projeto.estoque.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projeto.estoque.dto.Item;
import com.projeto.estoque.dto.Pessoa;
import com.projeto.estoque.interfaces.IPessoaService;
import com.projeto.estoque.interfaces.ItemService;

@Controller
public class MainController {

	private String globalSearchField;
	private List<Item> listProduct, listProductNewQnt, listProductOrig;
	private Pessoa ps = new Pessoa();

	@Autowired
	private ItemService repoService;

	@Autowired
	private IPessoaService repoServices;

	@GetMapping("/")
	public String showMenu(Model model) {
		Item item = new Item();
		model.addAttribute("item", item);
		return "menu";
	}

	@GetMapping("/register")
	public String showForm(Model model) {
		Item item = new Item();

		executeCarrinho(model);

		model.addAttribute("item", item);
		return "register";
	}

	@PostMapping("/registerItem")
	public String registerItem(@ModelAttribute("item") Item item, Model model, RedirectAttributes attr) {

		repoService.saveItem(item);

		executeCarrinho(model);
		attr.addFlashAttribute("message", "Produto cadastrado com sucesso...");
		model.addAttribute("resultRegister", item);

		return "redirect:/register";

	}

	@GetMapping("/gerarEstoque")
	public String showAll(Model model) {
		model.addAttribute("item", repoService.getAllItems());

		executeCarrinho(model);

		return "gerar_estoque";
	}

	@GetMapping("/alterar")
	public String showFormAlterar(Model model) {
		Item item = new Item();

		executeCarrinho(model);

		model.addAttribute("item", item);
		return "alterar";
	}

	@PostMapping("/alterarItem")
	public String showFormAlterar(Model model, @ModelAttribute("item") Item item) {

		List<Item> listProducts = repoService.getAllItems();

		String code;
		String name;

		for (Item codeData : listProducts) {
			if (codeData.getCode().equals(item.getCode())) {
				code = codeData.getCode().toString();
				name = codeData.getName().toString();

				model.addAttribute("code", code);
				model.addAttribute("name", name);

				executeCarrinho(model);

				return "alterar";
			}

			continue;
		}

		model.addAttribute("resultNotFound", item);

		return "alterar";
	}
//comen
	@GetMapping("/updateItem/{id}")
	public String updateForm(@PathVariable(value = "id") String code, Model model) {
		Item item = repoService.getById(code);

		executeCarrinho(model);

		model.addAttribute("item", item);
		return "updated";
	}

	@PostMapping("/updateItem")
	public String updateItem(@ModelAttribute("item") Item item, Model model) {
		List<Item> listProducts = repoService.getAllItems();

		String itemQnt = null;

		for (Item codeData : listProducts) {

			if (codeData.getCode().equals(item.getCode())) {
				itemQnt = codeData.getQnt().toString();
				item.setQnt(itemQnt);
				repoService.saveItem(item);
				break;

			}
			continue;
		}

		model.addAttribute("resultUpdated", item);

		executeCarrinho(model);

		return "alterar";
	}

	@GetMapping("/filtro")
	public String showFiltro(Model model) {
		Item item = new Item();

		executeCarrinho(model);

		model.addAttribute("item", item);
		return "filtro";
	}

	@PostMapping("/filtroItem")
	public String filtroItem(Model model, @ModelAttribute("item") Item item, Item product) {
		String searchField = item.getCode().toLowerCase();

		List<Item> listProducts = repoService.getAllItems();
		List<Item> listProduct = itemLoop(searchField, listProducts);

		if (listProduct.isEmpty()) {
			model.addAttribute("filterNotFound", listProduct);
			return "filtro";
		}

		model.addAttribute("product", listProduct);

		return "filtro";
	}

	@GetMapping("/comecoVenda")
	public String vendaInicio(Model model) {

		Item item = new Item();

		executeCarrinho(model);

		model.addAttribute("item", item);
		return "inicioVenda";
	}

	@PostMapping("/comecoVendaPost")
	public String vendaInicioPost(Model model, @ModelAttribute("item") Item item) {

		String phone = item.getCode().toString();

		List<Pessoa> listProducts = repoServices.getAllPessoa();
		List<Pessoa> listPhone = new ArrayList<>();

		for (Pessoa phoneNum : listProducts) {

			if (phoneNum.getPhone().equals(phone)) {
				ps.setPhone(phone);
				listPhone.add(phoneNum);
				break;
			}

		}

		if (listPhone.isEmpty()) {
			ps.setPhone(phone);
			model.addAttribute("phoneEmpty", ps);
			return "bootstrapHeader/errorPhone";
		}

		item.setCode("");

		for (Pessoa phoneNum : listProducts) {
			ps.setPhone(phoneNum.getPhone().toString());
			ps.setName(phoneNum.getName().toString());

			if (phoneNum.getZipCode().equals(""))
				break;

			else
				ps.setZipCode(phoneNum.getZipCode().toString());

			ps.setCity(phoneNum.getCity().toString());
			ps.setNumber(phoneNum.getNumber().toString());
			ps.setComplement(phoneNum.getComplement().toString());
			ps.setState(phoneNum.getState().toString());
			ps.setStreet(phoneNum.getStreet().toString());
		}

		executeCarrinho(model);

		model.addAttribute("pessoaData", ps);

		model.addAttribute("item", item);

		return "venda";
	}

	@GetMapping("/venda")
	public String showvenda(Model model) {
		Item item = new Item();

		executeCarrinho(model);

		model.addAttribute("item", item);
		return "venda";
	}

	@PostMapping("/vendaItem")
	public String vendaItem(Model model, @ModelAttribute("item") Item item, Item product) {

		List<Item> listProducts = repoService.getAllItems();

		String searchField = null;
		String qnt = null;

		if (this.listProductNewQnt == null)
			this.listProductNewQnt = new ArrayList<>();

		try {
			searchField = item.getCode().toLowerCase();
			this.globalSearchField = searchField;

		} catch (Exception e) {
			qnt = item.getQnt();

			for (Item i : listProducts) {

				if (i.getCode().equals(this.globalSearchField)) {

					int novaQnt = Integer.parseInt(qnt);
					int oldQnt = Integer.parseInt(i.getQnt());

					if (novaQnt > oldQnt) {

						model.addAttribute("qntyhight", item);

						executeCarrinho(model);

						return "venda";
					}

				}

			}

			this.listProduct = itemLoopVenda(listProducts, qnt);
			this.listProductNewQnt.addAll(listProduct);

			model.addAttribute("product", this.listProductOrig);

			model.addAttribute("productVenda", this.listProductNewQnt);

			model.addAttribute("numberOrders", this.listProductNewQnt.size());

			executeCarrinho(model);

			return "venda";

		}

		if (!(searchField == null)) {
			for (Item code : listProducts) {

				if (searchField.contains(code.getCode())) {

					int qntIte = Integer.parseInt(code.getQnt());

					if (qntIte == 0) {
						model.addAttribute("qntyZero", product);

						listProduct = itemLoopVendaOrig(listProducts, qnt);

						model.addAttribute("product", listProduct);

						executeCarrinho(model);

						return "venda";
					}

					listProduct = itemLoopVendaOrig(listProducts, qnt);

					model.addAttribute("product", listProduct);

					executeCarrinho(model);
					return "venda";
				}
			}

			executeCarrinho(model);

			model.addAttribute("itemNotFound", item);

			return "venda";
		}

		return "venda";

	}

	@GetMapping("/carrinhoId/{id}")
	public String showcarrinhoId(@PathVariable(value = "id") String code, Model model) {

		ArrayList<Item> newCollection = new ArrayList<>(this.listProductNewQnt);

		for (Item list : this.listProductNewQnt) {

			String newList = list.toString();
			if (!newList.contains(code))
				continue;

			int i = 0;

			while (this.listProductNewQnt.size() > 0) {

				if (this.listProductNewQnt.get(i).toString().contains(list.toString())) {

					newCollection.remove(i);
					this.listProductNewQnt = new ArrayList<>(newCollection);
					break;
				}

				i++;

			}

			fecharVenda(model);

		}

		model.addAttribute("productVenda", newCollection);

		return "table_venda";
	}

	@GetMapping("/carrinho")
	public String showcarrinho(Model model) {

		executeCarrinho(model);

		model.addAttribute("pessoaData", ps);

		model.addAttribute("productVenda", this.listProductNewQnt);

		fecharVenda(model);

		return "table_venda";

	}

	@GetMapping("/alterarCarrinho/{id}")
	public String alterarCarrinho(@PathVariable(value = "id") String code, Model model) {
		Item product = repoService.getById(code);
		String newQnt = this.listProductNewQnt.get(0).getQnt();

		product.setQnt(newQnt);

		executeCarrinho(model);

		model.addAttribute("product", product);
		return "alterarCarrinho";
	}

	@PostMapping("/alterarCarrinho")
	public String alterarCarrinho(@ModelAttribute("item") Item product, Model model) {

		List<Item> listProducts = repoService.getAllItems();

		ArrayList<Item> newCollection = new ArrayList<>(this.listProductNewQnt);

		int newNum = Integer.parseInt(product.getQnt());

		if (listProductNewQnt.get(0).getQnt() != product.getQnt()) {

			for (Item items : listProducts) {

				if (!items.getCode().equals(newCollection.get(0).getCode())) {
					continue;
				}

				int oldNum = Integer.parseInt(items.getQnt());

				if (newNum > oldNum) {
					model.addAttribute("qntyhight", product);

					this.listProductNewQnt = new ArrayList<>(newCollection);

					executeCarrinho(model);

					model.addAttribute("productVenda", newCollection);
					return "table_venda";

				}

				break;
			}

		}

		if (!listProductNewQnt.get(0).getPrice().equals(product.getPrice())) {
			listProductNewQnt.get(0).setPrice(product.getPrice());
		}

		if (!listProductNewQnt.get(0).getQnt().equals(product.getQnt())) {
			listProductNewQnt.get(0).setQnt(product.getQnt());
		}

		this.listProductNewQnt = new ArrayList<>(newCollection);

		model.addAttribute("productVenda", newCollection);
		model.addAttribute("updatedOk", newCollection);
		executeCarrinho(model);

		fecharVenda(model);

		return "table_venda";

	}

	public void fecharVenda(Model model) {

		executeCarrinho(model);

		double totalPrice = 0;
		double soma = 0;

		List<Double> priceList = new ArrayList<Double>();
		List<Double> priceListTotal = new ArrayList<Double>();

		for (Item items : this.listProductNewQnt) {

			totalPrice = Double.parseDouble(items.getPrice());

			int qnt = Integer.parseInt(items.getQnt());

			totalPrice = totalPrice * qnt;

			priceList.add(totalPrice);

		}

		for (double i : priceList) {
			soma += i;
		}

		priceListTotal.add(soma);

		model.addAttribute("itemQnt", this.listProductNewQnt.size());

		model.addAttribute("itemTotalPrice", priceListTotal);

	}

	@GetMapping("/vender")
	public String vender(Model model) {

		List<Item> listProducts = repoService.getAllItems();

		for (Item i : this.listProductNewQnt) {

			String code = i.getCode();
			int qnt = Integer.parseInt(i.getQnt());

			for (Item itemList : listProducts) {

				if (itemList.getCode().equals(code)) {

					int itemListQnt = Integer.parseInt(itemList.getQnt());

					itemListQnt = itemListQnt - qnt;

					if (itemListQnt < 0) {
						model.addAttribute("qntyhight", itemListQnt);
						return "venda";
					}

					String newQnt = Integer.toString(itemListQnt);

					itemList.setQnt(newQnt);
					repoService.saveItem(itemList);
					break;

				}

			}

		}

		this.listProductOrig.clear();
		this.listProductNewQnt.clear();

		model.addAttribute("listaFinal", this.listProductNewQnt.size());
		model.addAttribute("soldSucc", true);

		return "menu";
	}

	private List<Item> itemLoopVenda(List<Item> listProducts, String qnt) {
		this.listProduct = new ArrayList<Item>();

		for (Item items : listProducts) {
			Item itemString = items;

			if (itemString.getCode().toLowerCase().contains(this.globalSearchField)) {

				if (qnt != null) {
					itemString.setQnt(qnt);
				}

				this.listProduct.add(itemString);
			}
		}
		return this.listProduct;
	}

		
	private List<Item> itemLoopVendaOrig(List<Item> listProducts, String qnt) {
		this.listProductOrig = new ArrayList<Item>();

		for (Item items : listProducts) {
			Item itemString = items;

			if (itemString.getCode().toLowerCase().contains(this.globalSearchField)) {

				if (qnt != null) {
					itemString.setQnt(qnt);
				}

				this.listProductOrig.add(itemString);
			}
		}
		return this.listProductOrig;
	}

	private List<Item> itemLoop(String searchField, List<Item> listProducts) {
		List<Item> listProduct = new ArrayList<Item>();

		for (Item items : listProducts) {
			Item itemString = items;
			String itemStrings = items.toString();

			if (itemStrings.toLowerCase().contains(searchField)) {
				listProduct.add(itemString);
			}

		}

		return listProduct;
	}

	private void executeCarrinho(Model model) {
		if (this.listProductOrig != null) {
			if (this.listProductNewQnt.size() > 0 && this.listProductNewQnt != null)
				model.addAttribute("numberOrders", this.listProductNewQnt.size());
		}
	}
}