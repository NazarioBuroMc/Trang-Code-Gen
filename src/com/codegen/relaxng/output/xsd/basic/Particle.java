package com.codegen.relaxng.output.xsd.basic;

import com.codegen.relaxng.edit.SourceLocation;

public abstract class Particle extends Annotated {
  public Particle(SourceLocation location, Annotation annotation) {
    super(location, annotation);
  }

  public abstract Object accept(ParticleVisitor visitor);
}
