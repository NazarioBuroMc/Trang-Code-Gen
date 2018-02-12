package com.codegen.relaxng.input;

import com.codegen.relaxng.edit.SchemaCollection;
import com.codegen.relaxng.translate.util.InvalidParamsException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;

public interface InputFormat {
  SchemaCollection load(String uri, String[] params, String outputFormat, ErrorHandler eh)
          throws InputFailedException, InvalidParamsException, IOException, SAXException;
}
