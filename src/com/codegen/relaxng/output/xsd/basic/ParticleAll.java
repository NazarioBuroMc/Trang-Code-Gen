package com.codegen.relaxng.output.xsd.basic;

import java.util.List;

import com.codegen.relaxng.edit.SourceLocation;

public class ParticleAll extends ParticleGroup {
  public ParticleAll(SourceLocation location, Annotation annotation, List children) {
    super(location, annotation, children);
  }

  public Object accept(ParticleVisitor visitor) {
    return visitor.visitAll(this);
  }
}
