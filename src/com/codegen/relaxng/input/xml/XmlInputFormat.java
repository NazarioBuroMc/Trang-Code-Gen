package com.codegen.relaxng.input.xml;

import com.codegen.relaxng.edit.SchemaCollection;
import com.codegen.relaxng.input.AbstractMultiInputFormat;
import com.codegen.relaxng.input.InputFailedException;
import com.codegen.relaxng.translate.util.EncodingParam;
import com.codegen.relaxng.translate.util.InvalidParamsException;
import com.codegen.relaxng.translate.util.ParamProcessor;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;

public class XmlInputFormat extends AbstractMultiInputFormat {
  public SchemaCollection load(String[] uris, String[] params, String outputFormat, ErrorHandler eh)
          throws InputFailedException, InvalidParamsException, IOException, SAXException {
    ParamProcessor pp = new ParamProcessor();
    final Inferrer.Options options = new Inferrer.Options();
    pp.declare("encoding",
               new EncodingParam() {
                 protected void setEncoding(String encoding) {
                   options.encoding = encoding;
                 }
               });
    pp.process(params, eh);
    return Inferrer.infer(uris, options, eh);
  }
}
