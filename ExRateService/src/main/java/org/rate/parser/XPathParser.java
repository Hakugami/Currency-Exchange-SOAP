package org.rate.parser;

import org.rate.exceptions.ServiceFault;
import org.rate.exceptions.ServiceFaultException;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

public class XPathParser {
    public static double getExchangeRate(InputStream xmlStream, String fromCurrency, String toCurrency) {
        System.out.println("Parsing XML file with XPath parser");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlStream);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            XPathExpression expr = xpath.compile("//currency[code='" + fromCurrency + "']/rate");
            double sourceRate = Double.parseDouble((String) expr.evaluate(doc, XPathConstants.STRING));
            double usdFromSource = 1 / sourceRate;

            expr = xpath.compile("//currency[code='" + toCurrency + "']/rate");
            double targetRate = Double.parseDouble((String) expr.evaluate(doc, XPathConstants.STRING));

            return usdFromSource * targetRate;

        } catch (Exception e) {
            e.printStackTrace();
        }
        ServiceFault fault = new ServiceFault("Error reading or parsing XML file", "Error reading or parsing XML file");
        throw new ServiceFaultException("Error reading or parsing XML file", fault);
    }
}
