package org.exchange;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.MessageContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebService(endpointInterface = "org.exchange.ExchangeCalculationService")
public class ExchangeCalculationServiceImpl implements ExchangeCalculationService {
    @Override
    @WebMethod
    public double calculateExchange(ExchangeRequest request, @WebParam(header = true, name = "ParserType") ParserTypeHeader type) throws InvalidParserTypeException_Exception {
        if (type.getType() == null || (!type.getType().equals(ParserType.DOM) && !type.getType().equals(ParserType.X_PATH))) {
            InvalidParserTypeException fault = new InvalidParserTypeException();
            ServiceFault serviceFault = new ServiceFault();
            serviceFault.setFaultCode("Err-01");
            serviceFault.setFaultString("Invalid parser type");
            fault.setFault(serviceFault);
            throw new InvalidParserTypeException_Exception("Invalid parser type", fault);
        }
        try {
            ExchangeRateServiceImplService service = new ExchangeRateServiceImplService();
            ExchangeRateService port = service.getExchangeRateServiceImplPort();
            System.out.println("Requesting exchange rate from " + request.getFromCurrency() + " to " + request.getToCurrency() + "...");
            Map<String, List<String>> headers = new HashMap<>();
            headers.put("ParserType", Collections.singletonList(type.getType().toString()));
            BindingProvider bindingProvider = (BindingProvider) port;
            bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            GetExchangeRate getExchangeRate = new GetExchangeRate();
            getExchangeRate.setArg0(request.getFromCurrency());
            getExchangeRate.setArg1(request.getToCurrency());
            GetExchangeRateResponse response = port.getExchangeRate(getExchangeRate, type);
            return response.getReturn() * request.getAmount();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}