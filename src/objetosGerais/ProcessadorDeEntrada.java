package objetosGerais;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import automatoFinitoEstruturado.AutomatoFinitoEstruturado_v2;

public class ProcessadorDeEntrada {

	public static AutomatoFinitoEstruturado_v2 constroiAFv2(String arquivo) {
		AutomatoFinitoEstruturado_v2 temp = new AutomatoFinitoEstruturado_v2();
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(arquivo));

			// normalize text representation
			doc.getDocumentElement().normalize();
			
			NodeList automato = doc.getElementsByTagName("AF");
			Element maquinas = (Element) automato.item(0);

			// -------Procura as maquinas
			NodeList listaMaquinas = maquinas.getElementsByTagName("maquina");
			int totalMaq = listaMaquinas.getLength();

			// -------Inicia iteracao sobre as maquinas
			for (int s = 0; s < listaMaquinas.getLength(); s++) {


				// -------Pega maquina s
				Node maquina = listaMaquinas.item(s);
				if (maquina.getNodeType() == Node.ELEMENT_NODE) {
					Element elementoMaquina = (Element) maquina;

					// -------Atributo de nome da maquina
					NodeList nomeMaq = elementoMaquina
					.getElementsByTagName("name");
					Element elementoNomeMaq = (Element) nomeMaq.item(0);
					
					if (s > 0)
						temp.submaquinas.novaMaquinaAFE(elementoNomeMaq.getTextContent());
					else
						temp.setNome(elementoNomeMaq.getTextContent());

					// ------Estados
					NodeList estados = elementoMaquina
					.getElementsByTagName("estado");

					for (int i = 0; i < estados.getLength(); i++) {
						Element estado = (Element) estados.item(i);

						NodeList nome = estado.getElementsByTagName("name");
						NodeList end = estado.getElementsByTagName("final");
						NodeList start = estado.getElementsByTagName("inicial");
						NodeList retornaMoore = estado
						.getElementsByTagName("moore");
						
						int tipo = 0;
						if (end.item(0) != null)
							tipo = 1;
						if (start.item(0) != null)
							tipo = 2;
						if (retornaMoore.item(0) != null)
							tipo = 0;
						
						if (s > 0) {
							if (temp.submaquinas.getMaquina(elementoNomeMaq.getTextContent())!=null) {
								if (retornaMoore.item(0)!= null)
									temp.submaquinas.getMaquina(elementoNomeMaq.getTextContent())
									.estados.add(new Estadov2(
										nome.item(0).getTextContent(),
										tipo, 
										retornaMoore.item(0).getTextContent()));
								else
									temp.submaquinas.getMaquina(elementoNomeMaq.getTextContent())
									.estados.add(new Estadov2(
										nome.item(0).getTextContent(),
										tipo, 
										null));
							}
						} else {
							if (retornaMoore.item(0)!= null)
								temp.estados.add(new Estadov2(
									nome.item(0).getTextContent(),
									tipo, 
									retornaMoore.item(0).getTextContent()));
							else
								temp.estados.add(new Estadov2(
									nome.item(0).getTextContent(),
									tipo, 
									null));
						}
						
					}

					// ------Simbolos
					NodeList simbolos = elementoMaquina
					.getElementsByTagName("simbolo");

					for (int i = 0; i < simbolos.getLength(); i++) {
						Element simbolo = (Element) simbolos.item(i);
						if (s > 0) {
							if (temp.submaquinas.getMaquina(elementoNomeMaq.getTextContent())!=null)
								temp.submaquinas.getMaquina(elementoNomeMaq.getTextContent())
								.simbolos.add(new Simbolo(simbolo.getTextContent()));
						}
						else {
							temp.simbolos.add(new Simbolo(simbolo.getTextContent()));
						}

					}

					// ------Regras
					NodeList regras = elementoMaquina
					.getElementsByTagName("regra");

					for (int i = 0; i < regras.getLength(); i++) {
						Element regra = (Element) regras.item(i);

						NodeList estadoInicial = regra
						.getElementsByTagName("de");
						NodeList acao = regra.getElementsByTagName("acao");
						NodeList estadoFinal = regra
						.getElementsByTagName("para");
						NodeList retornaMealey = regra
						.getElementsByTagName("mealey");

						

						for (int j = 0; j < acao.getLength(); j++) {
							Element acaoAux = (Element) acao.item(j);
							NodeList simboloConsumido = acaoAux
							.getElementsByTagName("consome");
							NodeList maquinaChamada = acaoAux
							.getElementsByTagName("chama");
							if (simboloConsumido.item(0) != null) {
								if (s > 0) {
									if (retornaMealey.item(0)!=null)
										temp.submaquinas.getMaquina(elementoNomeMaq.getTextContent())
										.regras.add(new Regra(
											temp.estados.getEstado(estadoInicial.item(0).getTextContent()),
											temp.estados.getEstado(estadoFinal.item(0).getTextContent()),
											temp.simbolos.getSimbolo(simboloConsumido.item(0).getTextContent()),
											retornaMealey.item(0).getTextContent()));
									else
										temp.submaquinas.getMaquina(elementoNomeMaq.getTextContent())
										.regras.add(new Regra(
											temp.estados.getEstado(estadoInicial.item(0).getTextContent()),
											temp.estados.getEstado(estadoFinal.item(0).getTextContent()),
											temp.simbolos.getSimbolo(simboloConsumido.item(0).getTextContent()),
											null));
								} else {
									if (retornaMealey.item(0)!=null)
										temp.regras.add(new Regra(
											temp.estados.getEstado(estadoInicial.item(0).getTextContent()),
											temp.estados.getEstado(estadoFinal.item(0).getTextContent()),
											temp.simbolos.getSimbolo(simboloConsumido.item(0).getTextContent()),
											retornaMealey.item(0).getTextContent()));
									else
										temp.regras.add(new Regra(
											temp.estados.getEstado(estadoInicial.item(0).getTextContent()),
											temp.estados.getEstado(estadoFinal.item(0).getTextContent()),
											temp.simbolos.getSimbolo(simboloConsumido.item(0).getTextContent()),
											null));
								}
							}
							if (maquinaChamada.item(0) != null) {
								if (s > 0) {
									temp.submaquinas.getMaquina(elementoNomeMaq.getTextContent())
									.regras.add(new Regra(
											temp.estados.getEstado(estadoInicial.item(0).getTextContent()),
											temp.estados.getEstado(estadoFinal.item(0).getTextContent()),
											maquinaChamada.item(0).getTextContent(),
											null));
								} else {
									temp.regras.add(new Regra(
											temp.estados.getEstado(estadoInicial.item(0).getTextContent()),
											temp.estados.getEstado(estadoFinal.item(0).getTextContent()),
											maquinaChamada.item(0).getTextContent(),
											null));
								}
							}

						}

					}

				}

			}

		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());

		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();

		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return temp;
	}
}
