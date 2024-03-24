package org.rate.exceptions;

import jakarta.xml.ws.WebFault;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@WebFault(name = "InvalidParserTypeException")
public class InvalidParserTypeException extends Exception{
    protected ServiceFault fault;

    public InvalidParserTypeException(String message , ServiceFault fault) {
        super(message);
        this.fault = fault;
    }



}
