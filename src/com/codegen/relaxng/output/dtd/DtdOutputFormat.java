package com.codegen.relaxng.output.dtd;

import com.codegen.relaxng.edit.SchemaCollection;
import com.codegen.relaxng.output.OutputDirectory;
import com.codegen.relaxng.output.OutputDirectoryParamProcessor;
import com.codegen.relaxng.output.OutputFailedException;
import com.codegen.relaxng.output.OutputFormat;
import com.codegen.relaxng.output.common.ErrorReporter;
import com.codegen.relaxng.translate.util.InvalidParamsException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.File;

public class DtdOutputFormat implements OutputFormat {
  public void output(SchemaCollection sc, final OutputDirectory od, String[] params, String inputFormat, ErrorHandler eh)
          throws SAXException, IOException, OutputFailedException, InvalidParamsException {
    new OutputDirectoryParamProcessor(od).process(params, eh);
    Simplifier.simplify(sc);
    try {
      ErrorReporter er = new ErrorReporter(eh, DtdOutputFormat.class);
      Analysis analysis = new Analysis(sc, er);
      if (!er.getHadError())
        DtdOutput.output(!inputFormat.equals("xml"), analysis, od, er);
      if (er.getHadError())
        throw new OutputFailedException();
    }
    catch (ErrorReporter.WrappedSAXException e) {
      throw e.getException();
    }
  }
}
