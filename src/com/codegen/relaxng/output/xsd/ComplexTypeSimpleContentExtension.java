package com.codegen.relaxng.output.xsd;

import com.codegen.relaxng.output.xsd.basic.AttributeUse;
import com.codegen.relaxng.output.xsd.basic.ComplexTypeSimpleContent;
import com.codegen.relaxng.output.xsd.basic.SimpleType;

class ComplexTypeSimpleContentExtension extends ComplexTypeSimpleContent {
  private final String base;

  ComplexTypeSimpleContentExtension(AttributeUse attributeUses, SimpleType simpleType, String base) {
    super(attributeUses, simpleType);
    this.base = base;
  }

  ComplexTypeSimpleContentExtension(ComplexTypeSimpleContent ct) {
    super(ct.getAttributeUses(), ct.getSimpleType());
    this.base = null;
  }

  String getBase() {
    return base;
  }
}
