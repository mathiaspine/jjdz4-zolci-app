package pl.isa.parserXML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class Parser {
    static int czesciSamochodoweId = 620;

    public static void main(String[] args) {
        AllegroItem allegroItem;
        ArrayList<AllegroItem> allegroList = new ArrayList<AllegroItem>();

        int czesciSamochodowePosition = 0;
        try {
            File inputFile = new File("src/Allegro_cathegories_2016-02-13.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("ns1:item");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
//                System.out.println("\nCurrent Element :" + nNode.getNodeName());
//                System.out.println("\nIteam");
                allegroItem = new AllegroItem();

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    allegroItem.setId(Integer.parseInt(eElement
                            .getElementsByTagName("ns1:catId")
                            .item(0)
                            .getTextContent()));

                    allegroItem.setName(eElement
                            .getElementsByTagName("ns1:catName")
                            .item(0)
                            .getTextContent());

                    allegroItem.setParent(Integer.parseInt(eElement
                            .getElementsByTagName("ns1:catParent")
                            .item(0)
                            .getTextContent()));

                    allegroItem.setPosition(Integer.parseInt(eElement
                            .getElementsByTagName("ns1:catPosition")
                            .item(0)
                            .getTextContent()));
                    allegroList.add(allegroItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < allegroList.size(); i++) { //find collection position of category "Części Samochodowe"
            if (allegroList.get(i).getId() == czesciSamochodoweId) {
                czesciSamochodowePosition = i;
                break;
            }
        }


        TreeOperations treeOperations = new TreeOperations();

        allegroList.get(czesciSamochodowePosition).setChildren(allegroList); //set every item's children
        treeOperations.printWholeTree(0, allegroList.get(czesciSamochodowePosition));

        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwę kategorii której dzieci i rodzice cię interesują: ");
        //String searchPhase = scanner.nextLine();*/
        String searchPhase = "Układ hamulcowy";

        /*treeOperations.setSearchedCategory(searchPhase);
        treeOperations.printRelations(allegroList);*/
        //TODO poruszac sie tylko po dzieciach Czesci SAmochodowych a nie po calej liscie
    }
}
