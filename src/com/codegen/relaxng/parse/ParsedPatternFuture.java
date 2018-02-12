package com.codegen.relaxng.parse;

public interface ParsedPatternFuture {
  ParsedPattern getParsedPattern() throws IllegalSchemaException;
}
