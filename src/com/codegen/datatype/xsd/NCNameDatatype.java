package com.codegen.datatype.xsd;

import com.codegen.xml.util.Naming;

class NCNameDatatype extends NameDatatype {
  public boolean lexicallyAllows(String str) {
    return Naming.isNcname(str);
  }
}
