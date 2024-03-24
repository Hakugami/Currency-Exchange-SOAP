package org.exchange;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@WebService(name = "ExchangeCalculationService",
        targetNamespace = "http://exchange.org/",
        serviceName = "ExchangeCalculationService"
        , portName = "ExchangeCalculationServicePort")
public interface ExchangeCalculationService {

    @WebMethod(operationName = "calculateExchange"
            , action = "http://exchange.org/calculateExchange")
    double calculateExchange(ExchangeRequest request, @WebParam(header = true, name = "ParserType") ParserTypeHeader type) throws InvalidParserTypeException_Exception;
}
