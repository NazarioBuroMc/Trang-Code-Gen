package com.codegen.relaxng.translate.util;

import java.io.UnsupportedEncodingException;

import com.codegen.xml.util.EncodingMap;

public abstract class EncodingParam extends AbstractParam {
  public void set(String value) throws InvalidParamValueException {
    try {
      "x".getBytes(EncodingMap.getJavaName(value));
    }
    catch (UnsupportedEncodingException e) {
      throw new ParamProcessor.LocalizedInvalidValueException("unsupported_encoding");
    }
    setEncoding(value);
  }

  protected abstract void setEncoding(String encoding) throws InvalidParamValueException;
}
