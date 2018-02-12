package com.codegen.relaxng.parse;

public interface Parseable extends SubParser {
  ParsedPattern parse(SchemaBuilder f, Scope scope) throws BuildException, IllegalSchemaException;
}
