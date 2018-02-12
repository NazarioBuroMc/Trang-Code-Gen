package com.codegen.relaxng.parse.sax;

import com.codegen.relaxng.parse.ParseReceiver;
import com.codegen.relaxng.parse.ParsedPatternFuture;
import com.codegen.relaxng.parse.SchemaBuilder;
import com.codegen.relaxng.parse.Scope;
import com.codegen.xml.sax.XMLReaderCreator;

import org.xml.sax.XMLReader;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

public class SAXParseReceiver extends SAXSubParser implements ParseReceiver {
  public SAXParseReceiver(XMLReaderCreator xrc, ErrorHandler eh) {
    super(xrc, eh);
  }

  public ParsedPatternFuture installHandlers(XMLReader xr, SchemaBuilder schemaBuilder, Scope scope)
          throws SAXException {
    return new SchemaParser(xr, eh, schemaBuilder, null, scope);
  }
}
