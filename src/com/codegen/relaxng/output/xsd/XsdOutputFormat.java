package com.codegen.relaxng.output.xsd;

import com.codegen.relaxng.edit.SchemaCollection;
import com.codegen.relaxng.output.OutputDirectory;
import com.codegen.relaxng.output.OutputDirectoryParamProcessor;
import com.codegen.relaxng.output.OutputFailedException;
import com.codegen.relaxng.output.OutputFormat;
import com.codegen.relaxng.output.common.ErrorReporter;
import com.codegen.relaxng.output.xsd.basic.Schema;
import com.codegen.relaxng.translate.util.AbstractParam;
import com.codegen.relaxng.translate.util.EnumParam;
import com.codegen.relaxng.translate.util.InvalidParamsException;
import com.codegen.relaxng.translate.util.ParamProcessor;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;

public class XsdOutputFormat implements OutputFormat {
  static private final boolean DEFAULT_ENABLE_ABSTRACT_ELEMENT = true;
  static private final String[] processContents = { "skip", "lax", "strict" };
  public void output(SchemaCollection sc, OutputDirectory od, String[] params, String inputFormat, ErrorHandler eh)
          throws SAXException, IOException, OutputFailedException, InvalidParamsException {
    final Guide guide = new Guide(DEFAULT_ENABLE_ABSTRACT_ELEMENT);
    final BasicOutput.Options outputOptions = new BasicOutput.Options();
    if ("dtd".equals(inputFormat))
      outputOptions.anyProcessContents = "strict";
    ParamProcessor pp = new OutputDirectoryParamProcessor(od);
    pp.declare("disable-abstract-elements",
               new AbstractParam() {
                 public void set(boolean value) {
                   guide.setDefaultGroupEnableAbstractElements(!value);
                 }
               });
    pp.declare("any-process-contents",
               new EnumParam(processContents) {
                 protected void setEnum(int i) {
                   outputOptions.anyProcessContents = getValues()[i];
                 }
               });
    pp.declare("any-attribute-process-contents",
               new EnumParam(processContents) {
                 protected void setEnum(int i) {
                   outputOptions.anyAttributeProcessContents = getValues()[i];
                 }
               });
    pp.process(params, eh);
    try {
      ErrorReporter er = new ErrorReporter(eh, XsdOutputFormat.class);
      SchemaInfo si = new SchemaInfo(sc, er);
      if (!er.getHadError()) {
        RefChecker.check(si, er);
        if (!er.getHadError()) {
          RestrictionsChecker.check(si, er);
          if (!er.getHadError()) {
            Schema schema = BasicBuilder.buildBasicSchema(si, guide, er);
            if (!er.getHadError()) {
              new Transformer(schema, er).transform();
              if (!er.getHadError())
                BasicOutput.output(schema, guide, new PrefixManager(si), od, outputOptions, er);
            }
          }
        }
      }
      if (er.getHadError())
        throw new OutputFailedException();
    }
    catch (ErrorReporter.WrappedSAXException e) {
      throw e.getException();
    }
  }
}
