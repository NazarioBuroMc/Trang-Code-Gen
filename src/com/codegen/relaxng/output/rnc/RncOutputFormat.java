package com.codegen.relaxng.output.rnc;

import com.codegen.relaxng.edit.Pattern;
import com.codegen.relaxng.edit.SchemaCollection;
import com.codegen.relaxng.edit.SchemaDocument;
import com.codegen.relaxng.output.OutputDirectory;
import com.codegen.relaxng.output.OutputDirectoryParamProcessor;
import com.codegen.relaxng.output.OutputFailedException;
import com.codegen.relaxng.output.OutputFormat;
import com.codegen.relaxng.output.common.ErrorReporter;
import com.codegen.relaxng.translate.util.InvalidParamsException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class RncOutputFormat implements OutputFormat {
  public void output(SchemaCollection sc, OutputDirectory od, String[] params, String inputFormat, ErrorHandler eh)
          throws SAXException, IOException, OutputFailedException, InvalidParamsException {
    new OutputDirectoryParamProcessor(od).process(params, eh);
    try {
      ErrorReporter er = new ErrorReporter(eh, RncOutputFormat.class);
      for (Iterator iter = sc.getSchemaDocumentMap().entrySet().iterator(); iter.hasNext();) {
        Map.Entry entry = (Map.Entry)iter.next();
        outputPattern((SchemaDocument)entry.getValue(), (String)entry.getKey(), od, er);
      }
    }
    catch (ErrorReporter.WrappedSAXException e) {
      throw e.getException();
    }
  }

  private static void outputPattern(SchemaDocument sd, String sourceUri, OutputDirectory od, ErrorReporter er) throws IOException {
    Output.output(sd.getPattern(),
                  sd.getEncoding(),
                  sourceUri,
                  od,
                  er);
  }

}
