package com.codegen.relaxng.parse.sax;

import com.codegen.relaxng.parse.BuildException;
import com.codegen.relaxng.parse.IllegalSchemaException;
import com.codegen.relaxng.parse.Parseable;
import com.codegen.relaxng.parse.ParsedPattern;
import com.codegen.relaxng.parse.SchemaBuilder;
import com.codegen.relaxng.parse.Scope;
import com.codegen.xml.sax.XMLReaderCreator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;

public class SAXParseable extends SAXSubParser implements Parseable {
  private final InputSource in;

  public SAXParseable(XMLReaderCreator xrc, InputSource in, ErrorHandler eh) {
    super(xrc, eh);
    this.in = in;
  }

  public ParsedPattern parse(SchemaBuilder schemaBuilder, Scope scope) throws BuildException, IllegalSchemaException {
    try {
      XMLReader xr = xrc.createXMLReader();
      SchemaParser sp = new SchemaParser(xr, eh, schemaBuilder, null, scope);
      xr.parse(in);
      return sp.getParsedPattern();
    }
    catch (SAXException e) {
      throw toBuildException(e);
    }
    catch (IOException e) {
      throw new BuildException(e);
    }
  }

}
