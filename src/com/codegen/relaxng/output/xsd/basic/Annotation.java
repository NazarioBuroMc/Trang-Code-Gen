package com.codegen.relaxng.output.xsd.basic;

import com.codegen.util.Equal;

public class Annotation {
  private final String documentation;

  public Annotation(String documentation) {
    this.documentation = documentation;
  }

  public String getDocumentation() {
    return documentation;
  }

  public boolean equals(Object obj) {
    return obj instanceof Annotation && Equal.equal(documentation, ((Annotation)obj).documentation);
  }

  public int hashCode() {
    if (documentation != null)
      return documentation.hashCode();
    return Annotation.class.hashCode();
  }
}
