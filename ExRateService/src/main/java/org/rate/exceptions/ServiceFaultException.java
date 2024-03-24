package org.rate.exceptions;

import jakarta.xml.ws.WebFault;

@WebFault(name = "ServiceFault")
public class ServiceFaultException extends RuntimeException {

    private final ServiceFault fault;

    public ServiceFaultException(String message, ServiceFault fault) {
        super(message);
        this.fault = fault;
    }

    public ServiceFaultException(String message, ServiceFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
    }

    public ServiceFault getFaultInfo() {
        return fault;
    }
}