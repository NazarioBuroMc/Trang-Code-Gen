package com.codegen.relaxng.input.parse.sax;

import com.codegen.relaxng.input.parse.ParseInputFormat;
import com.codegen.relaxng.parse.Parseable;
import com.codegen.relaxng.parse.sax.SAXParseable;
import com.codegen.xml.sax.Jaxp11XMLReaderCreator;
import com.codegen.xml.sax.XMLReaderCreator;

import org.xml.sax.InputSource;
import org.xml.sax.ErrorHandler;

public class SAXParseInputFormat extends ParseInputFormat {
  private final XMLReaderCreator xrc = new Jaxp11XMLReaderCreator();

  public SAXParseInputFormat() {
    super(true);
  }

  public Parseable makeParseable(InputSource in, ErrorHandler eh) {
    return new SAXParseable(xrc, in, eh);
  }
}
