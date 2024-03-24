package org.rate.XmlElement;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import org.rate.parser.ParserType;


@Getter
@Setter
@XmlRootElement(name = "ParserTypeHeader")
public class ParserTypeHeader {
    private ParserType type;
}