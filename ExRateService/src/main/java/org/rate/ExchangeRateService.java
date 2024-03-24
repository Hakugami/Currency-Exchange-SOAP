package org.rate;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;
import org.rate.XmlElement.ParserTypeHeader;
import org.rate.exceptions.InvalidParserTypeException;

@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@WebService(name = "ExchangeRateService",
        targetNamespace = "http://rate.org/",
        serviceName = "ExchangeRateService"
        , portName = "ExchangeRateServicePort")
public interface ExchangeRateService {
    @WebMethod(operationName = "getExchangeRate"
            , action = "http://rate.org/getExchangeRate")
    double getExchangeRate(String fromCurrency, String toCurrency, @WebParam(header = true , name ="ParserType" )  ParserTypeHeader type) throws InvalidParserTypeException;

    @WebMethod
    double getExchangeRateDOM(String fromCurrency, String toCurrency);

    @WebMethod
    double getExchangeRateXPath(String fromCurrency, String toCurrency);
}
