package com.codegen.xml.infer;

public class EmptyParticle extends Particle {
  public Object accept(ParticleVisitor visitor) {
    return visitor.visitEmpty(this);
  }
}
