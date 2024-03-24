package org.rate.handlers;

import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.ws.handler.MessageContext;


import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.Set;

public class LoggingSOAPHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public Set<QName> getHeaders() {
        return null; // no headers to process
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        logSOAPMessage(context);
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logSOAPMessage(context);
        return true;
    }

    @Override
    public void close(MessageContext context) {
        // nothing to close
    }

    private void logSOAPMessage(SOAPMessageContext context) {
        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty) {
            System.out.println("\nOutbound message:");
        } else {
            System.out.println("\nInbound message:");
        }

        SOAPMessage message = context.getMessage();
        try {
            SOAPBody body = message.getSOAPBody();
            System.out.println("SOAP Body: " + body.getTextContent().trim());
        } catch (SOAPException e) {
            System.err.println("Error logging SOAP message: " + e.getMessage());
        }
    }


}