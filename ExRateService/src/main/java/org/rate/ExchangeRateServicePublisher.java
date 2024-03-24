package org.rate;

import jakarta.xml.ws.Binding;
import jakarta.xml.ws.Endpoint;
import jakarta.xml.ws.handler.Handler;
import org.rate.handlers.LoggingSOAPHandler;

import java.util.List;

public class ExchangeRateServicePublisher {
    public static void main(String[] args) {
        String url = "http://localhost:8282/exchangeRateService";
        ExchangeRateServiceImpl exchangeRateService = new ExchangeRateServiceImpl();
        Endpoint endpoint = Endpoint.create(exchangeRateService);
        Binding binding = endpoint.getBinding();
        List<Handler> handlerChain = binding.getHandlerChain();
        handlerChain.add(new LoggingSOAPHandler());
        binding.setHandlerChain(handlerChain);
        endpoint.publish(url);

        System.out.println("Service is published! " + url + "?wsdl");
    }
}
