package nucleo;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class testes {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		   try {

	            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	            Document doc = docBuilder.parse (new File("Melhorias_v2/nova_entrada_af.txt"));

	            // normalize text representation
	            doc.getDocumentElement ().normalize ();
	            System.out.println ("A raiz do documento : " + 
	                 doc.getDocumentElement().getNodeName());

	            NodeList automato = doc.getElementsByTagName("AF");
	            Element maquinas = (Element)automato.item(0);

	            //-------Procura as maquinas
	            NodeList listaMaquinas = maquinas.getElementsByTagName("maquina");
	            int totalMaq = listaMaquinas.getLength();
	            System.out.println("Total de maquinas : " + totalMaq);

	            //-------Inicia iteracao sobre as maquinas
	            for(int s=0; s<listaMaquinas.getLength() ; s++){

	            	//-------Pega maquina s
	                Node maquina = listaMaquinas.item(s);
	                if(maquina.getNodeType() == Node.ELEMENT_NODE){


	                    Element elementoMaquina = (Element)maquina;

	                    //-------Atributo de nome da maquina
	                    NodeList nomeMaq = elementoMaquina.getElementsByTagName("name");
	                    Element elementoNomeMaq = (Element)nomeMaq.item(0);

	                    System.out.println("Maquina : " +
	                    		elementoNomeMaq.getTextContent());

	                    //------Estados
	                    NodeList estados = elementoMaquina.getElementsByTagName("estado");
	                    
	                    for(int i=0; i<estados.getLength() ; i++){
		                    Element estado = (Element)estados.item(i);

		                    NodeList nome = estado.getElementsByTagName("name");
		                    NodeList end = estado.getElementsByTagName("final");
		                    NodeList start = estado.getElementsByTagName("inicial");
		                    NodeList retornaMoore = estado.getElementsByTagName("moore");

		                    
		                    System.out.print("Estado: ");
		                    System.out.print(nome.item(0).getTextContent()); 
		                    if (end.item(0) != null)
		                    	System.out.print(" final");
		                    if (start.item(0) != null)
		                    	System.out.print(" inicial");
		                    if (retornaMoore.item(0) != null)
		                    	System.out.print(" " + retornaMoore.item(0).getTextContent());
		                    System.out.println();
	                    }
	                    
	                    //------Simbolos
	                    NodeList simbolos = elementoMaquina.getElementsByTagName("simbolo");
	                    
	                    for(int i=0; i<simbolos.getLength() ; i++){
		                    Element simbolo = (Element)simbolos.item(i);
		                    System.out.println("Simbolo: " + simbolo.getTextContent());
		                    
	                    }
	                    
	                    //------Regras
	                    NodeList regras = elementoMaquina.getElementsByTagName("regra");
	                    
	                    for(int i=0; i<regras.getLength() ; i++){
		                    Element regra = (Element)regras.item(i);
		                    
		                    NodeList estadoInicial = regra.getElementsByTagName("de");
		                    NodeList acao = regra.getElementsByTagName("acao");
		                    NodeList estadoFinal = regra.getElementsByTagName("para");
		                    NodeList retornaMealey = regra.getElementsByTagName("mealey");

		                    
		                    System.out.println("Regra: ");
		                    System.out.println("\tde : " + estadoInicial.item(0).getTextContent() + " ");
		                    System.out.println("\tpara : " + estadoFinal.item(0).getTextContent() + " ");
		                    
		                    for(int j=0; j<acao.getLength() ; j++){
		                    	Element acaoAux = (Element)acao.item(j);
		                    	NodeList simboloConsumido = acaoAux.getElementsByTagName("consome");
		                    	NodeList maquinaChamada = acaoAux.getElementsByTagName("chama");
		                    	if (simboloConsumido.item(0) != null)
		                    		System.out.println("\tconsome : " + simboloConsumido.item(0).getTextContent() + " "); 
		                    	if (maquinaChamada.item(0) != null)
		                    		System.out.println("\tchama : " + maquinaChamada.item(0).getTextContent() + " "); 

		                    }
		                    
		                    if (retornaMealey.item(0) != null)
		                    	System.out.println("\tretorna : " + retornaMealey.item(0).getTextContent() + " ");
		                    
	                    }

	                }//end of if clause


	            }//end of for loop with s var


	        }catch (SAXParseException err) {
	        System.out.println ("** Parsing error" + ", line " 
	             + err.getLineNumber () + ", uri " + err.getSystemId ());
	        System.out.println(" " + err.getMessage ());

	        }catch (SAXException e) {
	        Exception x = e.getException ();
	        ((x == null) ? e : x).printStackTrace ();

	        }catch (Throwable t) {
	        t.printStackTrace ();
	        }


	}

}
