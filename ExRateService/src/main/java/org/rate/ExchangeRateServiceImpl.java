package org.rate;


import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.rate.XmlElement.ParserTypeHeader;
import org.rate.exceptions.InvalidParserTypeException;
import org.rate.exceptions.ServiceFault;
import org.rate.exceptions.ServiceFaultException;
import org.rate.parser.DOMParser;
import org.rate.parser.ParserType;
import org.rate.parser.XPathParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

@WebService(endpointInterface = "org.rate.ExchangeRateService")
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private static final String EXCHANGE_RATE = "/exchange_rates.xml";

    @Override
    public double getExchangeRate(String fromCurrency, String toCurrency, @WebParam(header = true , name ="ParserType" ) ParserTypeHeader typeHeader) {
        ParserType type = typeHeader.getType();
        try {
            return switch (type) {
                case DOM -> getExchangeRateDOM(fromCurrency, toCurrency);
                case XPath -> getExchangeRateXPath(fromCurrency, toCurrency);
                default -> throw new InvalidParserTypeException("Invalid parser type", new ServiceFault("INVALID PARSER TYPE", "Parser type must be DOM or X_PATH"));
            };
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            ServiceFault fault = new ServiceFault("SOMETHING WENT WRONG", e.getMessage());
            throw new ServiceFaultException(e.getMessage(), fault, e);
        }
    }


    @Override
    public double getExchangeRateDOM(String fromCurrency, String toCurrency) {
        try {
            FileInputStream xmlStream = new FileInputStream(new File(getClass().getResource(EXCHANGE_RATE).toURI()));
            return DOMParser.getExchangeRate(xmlStream, fromCurrency, toCurrency);
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public double getExchangeRateXPath(String fromCurrency, String toCurrency) {
        try {
            FileInputStream xmlStream = new FileInputStream(new File(getClass().getResource(EXCHANGE_RATE).toURI()));
            return XPathParser.getExchangeRate(xmlStream, fromCurrency, toCurrency);
        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
