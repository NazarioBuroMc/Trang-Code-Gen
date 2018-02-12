package com.codegen.datatype.xsd;

import org.codegen.datatype.ValidationContext;

class EntityDatatype extends NCNameDatatype {
  boolean allowsValue(String str, ValidationContext vc) {
    return vc.isUnparsedEntity(str);
  }

  Object getValue(String str, ValidationContext vc) {
    if (!allowsValue(str, vc))
      return null;
    return super.getValue(str, vc);
  }
}
