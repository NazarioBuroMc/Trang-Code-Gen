package com.codegen.relaxng.output;

import com.codegen.relaxng.edit.SchemaCollection;
import com.codegen.relaxng.translate.util.InvalidParamsException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;

public interface OutputFormat {
  void output(SchemaCollection sc, OutputDirectory od, String[] params, String inputFormat, ErrorHandler eh)
          throws SAXException, IOException, OutputFailedException, InvalidParamsException;
}
