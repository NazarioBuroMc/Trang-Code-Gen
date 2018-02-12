package com.codegen.relaxng.output.xsd.basic;

import com.codegen.relaxng.edit.SourceLocation;

public abstract class Located {
  private final SourceLocation location;

  public Located(SourceLocation location) {
    this.location = location;
  }

  public SourceLocation getLocation() {
    return location;
  }
}
