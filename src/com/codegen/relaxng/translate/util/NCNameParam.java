package com.codegen.relaxng.translate.util;

import com.codegen.xml.util.Naming;

public abstract class NCNameParam extends AbstractParam {
  public void set(String value) throws InvalidParamValueException {
    if (!Naming.isNcname(value))
      throw new ParamProcessor.LocalizedInvalidValueException("invalid_ncname");
    setNCName(value);
  }

  protected abstract void setNCName(String value) throws InvalidParamValueException;
}
