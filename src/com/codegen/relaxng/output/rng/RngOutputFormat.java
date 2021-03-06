package com.codegen.relaxng.output.rng;

import com.codegen.relaxng.edit.Pattern;
import com.codegen.relaxng.edit.SchemaCollection;
import com.codegen.relaxng.edit.SchemaDocument;
import com.codegen.relaxng.output.OutputDirectory;
import com.codegen.relaxng.output.OutputDirectoryParamProcessor;
import com.codegen.relaxng.output.OutputFormat;
import com.codegen.relaxng.translate.util.InvalidParamsException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

public class RngOutputFormat implements OutputFormat {
  public void output(SchemaCollection sc, OutputDirectory od, String[] params, String inputFormat, ErrorHandler eh)
          throws IOException, InvalidParamsException, SAXException {
    new OutputDirectoryParamProcessor(od).process(params, eh);
    for (Iterator iter = sc.getSchemaDocumentMap().entrySet().iterator(); iter.hasNext();) {
      Map.Entry entry = (Map.Entry)iter.next();
      outputPattern((SchemaDocument)entry.getValue(), (String)entry.getKey(), od);
    }
  }

  private static void outputPattern(SchemaDocument sd, String sourceUri, OutputDirectory od) throws IOException {
    Analyzer analyzer = new Analyzer();
    sd.getPattern().accept(analyzer);
    Output.output(sd.getPattern(),
                  sd.getEncoding(),
                  sourceUri,
                  od,
                  analyzer.getDatatypeLibrary(),
                  analyzer.getPrefixMap());
  }
}
