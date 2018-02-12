package com.codegen.relaxng.output;

import com.codegen.relaxng.translate.util.EncodingParam;
import com.codegen.relaxng.translate.util.IntegerParam;
import com.codegen.relaxng.translate.util.Param;
import com.codegen.relaxng.translate.util.ParamProcessor;

public class OutputDirectoryParamProcessor extends ParamProcessor {
  private final OutputDirectory od;
  private static final int MAX_INDENT = 16;

  public OutputDirectoryParamProcessor(OutputDirectory od) {
    this.od = od;
    super.declare("encoding",
                  new EncodingParam() {
                    protected void setEncoding(String encoding) {
                      OutputDirectoryParamProcessor.this.od.setEncoding(encoding);
                    }
                  });
    super.declare("indent",
                  new IntegerParam(0, MAX_INDENT) {
                    protected void setInteger(int value) {
                      OutputDirectoryParamProcessor.this.od.setIndent(value);
                    }
                  });
  }
}
