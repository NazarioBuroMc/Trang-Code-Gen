package com.codegen.relaxng.parse;

public interface IncludedGrammar extends GrammarSection, Scope {
  ParsedPattern endIncludedGrammar(Location loc, Annotations anno) throws BuildException;
}
