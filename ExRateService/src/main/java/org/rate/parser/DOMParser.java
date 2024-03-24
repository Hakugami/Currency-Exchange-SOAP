package org.rate.parser;

import org.rate.exceptions.ServiceFault;
import org.rate.exceptions.ServiceFaultException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;

public class DOMParser {
    public static double getExchangeRate(FileInputStream xmlStream, String fromCurrency, String toCurrency) {
        System.out.println("Parsing XML file with DOM parser");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlStream);
            NodeList currencies = doc.getElementsByTagName("currency");

            double sourceRate = 0;
            double targetRate = 0;

            for (int i = 0; i < currencies.getLength(); i++) {
                Node currency = currencies.item(i);
                if (currency.getNodeType() == Node.ELEMENT_NODE) {
                    Element currencyElement = (Element) currency;
                    String code = currencyElement.getElementsByTagName("code").item(0).getTextContent();
                    double rate = Double.parseDouble(currencyElement.getElementsByTagName("rate").item(0).getTextContent());

                    if (code.equals(fromCurrency)) {
                        sourceRate = rate;
                        System.out.println("Found source currency: " + fromCurrency + " with rate: " + sourceRate);
                    } else if (code.equals(toCurrency)) {
                        targetRate = rate;
                        System.out.println("Found target currency: " + toCurrency + " with rate: " + targetRate);
                    }
                }
            }

            if (sourceRate == 0) {
                ServiceFault fault = new ServiceFault("Source currency not found in XML file", "Source currency not found in XML file");
                throw new ServiceFaultException("Source currency not found in XML file", fault);
            }

            if (targetRate == 0) {
                ServiceFault fault = new ServiceFault("Target currency not found in XML file", "Target currency not found in XML file");
                throw new ServiceFaultException("Target currency not found in XML file", fault);
            }

            // Always convert from the source currency to USD first
            double usdFromSource = 1 / sourceRate;

            // Then convert from USD to the target currency

            return usdFromSource * targetRate;

        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Error reading or parsing XML file: " + e.getMessage());
            e.printStackTrace();
        }
        ServiceFault fault = new ServiceFault("Error reading or parsing XML file", "Error reading or parsing XML file");
        throw new ServiceFaultException("Error reading or parsing XML file", fault);
    }
}
