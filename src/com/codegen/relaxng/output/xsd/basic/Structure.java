package com.codegen.relaxng.output.xsd.basic;

import com.codegen.relaxng.output.common.Name;

public interface Structure {
  Name getName();
  Object accept(StructureVisitor visitor);
}
