package com.projeto.estoque.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projeto.estoque.dto.Pessoa;
import com.projeto.estoque.interfaces.IPessoaService;

@Controller
public class PessoaController {

	private Pessoa ps = new Pessoa();

	@Autowired
	private IPessoaService repoService;

	@GetMapping("/pessoa")
	public String showpessoa(Model model) {
		Pessoa pessoa = new Pessoa();
		model.addAttribute("pessoa", pessoa);
		return "pessoa-register";
	}

	@PostMapping("/registerPessoa")
	public String registerItem(@ModelAttribute("pessoa") Pessoa pessoa, Model model, RedirectAttributes attr,
			@RequestParam(value = "userAction") Boolean accept) throws Exception {

		if (accept) {
			repoService.savePessoa(pessoa);
			model.addAttribute("suc", ps);
			attr.addFlashAttribute("message", "Cliente cadastrado com sucesso...");
			return "redirect:/pessoa";
		}

		getDataCep(pessoa, model);

		ps.setPhone(pessoa.getPhone());
		ps.setName(pessoa.getName());
		ps.setZipCode(pessoa.getZipCode());

		model.addAttribute("pessoa", ps);

		return "pessoa-register";
	}

	@GetMapping("/registerPessoa/{id}")
	public String registerPessoaid(@PathVariable(value = "id") String phone, Model model, Pessoa pessoa) {

		pessoa.setPhone(phone);

		model.addAttribute("phone", pessoa);
		return "pessoa-register";
	}

	private String getDataCep(Pessoa pessoa, Model model) throws Exception {
		String searchField = pessoa.getZipCode().toLowerCase();
		String url = "https://viacep.com.br/ws/" + searchField + "/json/";

		URI uri = URI.create(url);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(uri).header("Content-Type", "application/json").GET()
				.build();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.body().toString().contains("400")) {
			model.addAttribute("err", pessoa);
			return "error";
		}

		String street;
		String city;
		String state;

		JSONObject objJson = new JSONObject(response.body().toString());

		street = objJson.getString("logradouro");
		city = objJson.getString("localidade");
		state = objJson.getString("uf");

		ps.setStreet(street);
		ps.setCity(city);
		ps.setState(state);

		return "pessoa-register";

	}
}
