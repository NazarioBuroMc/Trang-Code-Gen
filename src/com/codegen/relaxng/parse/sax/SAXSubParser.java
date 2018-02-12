package com.codegen.relaxng.parse.sax;

import com.codegen.relaxng.parse.BuildException;
import com.codegen.relaxng.parse.IllegalSchemaException;
import com.codegen.relaxng.parse.IncludedGrammar;
import com.codegen.relaxng.parse.ParsedPattern;
import com.codegen.relaxng.parse.SchemaBuilder;
import com.codegen.relaxng.parse.Scope;
import com.codegen.relaxng.parse.SubParser;
import com.codegen.xml.sax.XMLReaderCreator;

import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;

import java.io.IOException;

public class SAXSubParser implements SubParser {
  final XMLReaderCreator xrc;
  final ErrorHandler eh;

  SAXSubParser(XMLReaderCreator xrc, ErrorHandler eh) {
    this.xrc = xrc;
    this.eh = eh;
  }

  public ParsedPattern parseInclude(String uri, SchemaBuilder schemaBuilder, IncludedGrammar g)
          throws BuildException, IllegalSchemaException {
    try {
      XMLReader xr = xrc.createXMLReader();
      SchemaParser sp = new SchemaParser(xr, eh, schemaBuilder, g, g);
      xr.parse(makeInputSource(xr, uri));
      return sp.getParsedPattern();
    }
    catch (SAXException e) {
     throw SAXParseable.toBuildException(e);
    }
    catch (IOException e) {
     throw new BuildException(e);
    }
  }

  public ParsedPattern parseExternal(String uri, SchemaBuilder schemaBuilder, Scope s)
          throws BuildException, IllegalSchemaException {
    try {
      XMLReader xr = xrc.createXMLReader();
      SchemaParser sp = new SchemaParser(xr, eh, schemaBuilder, null, s);
      xr.parse(makeInputSource(xr, uri));
      return sp.getParsedPattern();
    }
    catch (SAXException e) {
      throw SAXParseable.toBuildException(e);
    }
    catch (IOException e) {
      throw new BuildException(e);
    }
  }

  private static InputSource makeInputSource(XMLReader xr, String systemId) throws IOException, SAXException {
    EntityResolver er = xr.getEntityResolver();
    if (er != null) {
      InputSource inputSource = er.resolveEntity(null, systemId);
      if (inputSource != null)
	return inputSource;
    }
    return new InputSource(systemId);
  }

  static BuildException toBuildException(SAXException e) {
    Exception inner = e.getException();
    if (inner instanceof BuildException)
      throw (BuildException)inner;
    throw new BuildException(e);
  }
}
