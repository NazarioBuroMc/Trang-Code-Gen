package com.codegen.datatype.xsd;

import org.codegen.datatype.ValidationContext;

import com.codegen.util.Uri;

class AnyUriDatatype extends TokenDatatype {
  public boolean lexicallyAllows(String str) {
    return Uri.isValid(str);
  }

  public boolean alwaysValid() {
    return false;
  }
}
