package com.codegen.relaxng.input.parse;

import com.codegen.relaxng.edit.SchemaCollection;
import com.codegen.relaxng.input.InputFailedException;
import com.codegen.relaxng.input.InputFormat;
import com.codegen.relaxng.parse.IllegalSchemaException;
import com.codegen.relaxng.parse.Parseable;
import com.codegen.relaxng.translate.util.EncodingParam;
import com.codegen.relaxng.translate.util.InvalidParamsException;
import com.codegen.relaxng.translate.util.ParamProcessor;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.relaxng.datatype.helpers.DatatypeLibraryLoader;

import java.io.IOException;

public abstract class ParseInputFormat implements InputFormat {
  private final boolean commentsNeedTrimming;
  protected ParseInputFormat(boolean commentsNeedTrimming) {
    this.commentsNeedTrimming = commentsNeedTrimming;
  }

  public SchemaCollection load(String uri, String[] params, String outputFormat, ErrorHandler eh)
          throws InputFailedException, InvalidParamsException, IOException, SAXException {
    final InputSource in = new InputSource(uri);
    ParamProcessor pp = new ParamProcessor();
    pp.declare("encoding",
               new EncodingParam() {
                 protected void setEncoding(String encoding) {
                   in.setEncoding(encoding);
                 }
               });
    pp.process(params, eh);
    Parseable parseable = makeParseable(in, eh);
    try {
      return SchemaBuilderImpl.parse(parseable,
                                     uri,
                                     eh,
                                     new DatatypeLibraryLoader(),
                                     commentsNeedTrimming);
    }
    catch (IllegalSchemaException e) {
      throw new InputFailedException();
    }
  }

  protected abstract Parseable makeParseable(InputSource in, ErrorHandler eh);
}
