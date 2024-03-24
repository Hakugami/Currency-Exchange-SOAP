package org.exchange;

import jakarta.xml.ws.Endpoint;

public class ExchangeCalculationServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8383/exchangeCalculationService", new ExchangeCalculationServiceImpl());
        System.out.println("Service is published! " + "http://localhost:8383/exchangeCalculationService?wsdl");
    }
}
