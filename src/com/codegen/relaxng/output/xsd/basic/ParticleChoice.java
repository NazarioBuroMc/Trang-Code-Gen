package com.codegen.relaxng.output.xsd.basic;

import java.util.List;

import com.codegen.relaxng.edit.SourceLocation;

public class ParticleChoice extends ParticleGroup {
  public ParticleChoice(SourceLocation location, Annotation annotation, List children) {
    super(location, annotation, children);
  }

  public Object accept(ParticleVisitor visitor) {
    return visitor.visitChoice(this);
  }
}
