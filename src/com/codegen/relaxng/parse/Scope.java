package com.codegen.relaxng.parse;

public interface Scope {
  ParsedPattern makeParentRef(String name, Location loc, Annotations anno) throws BuildException;
  ParsedPattern makeRef(String name, Location loc, Annotations anno) throws BuildException;
}
