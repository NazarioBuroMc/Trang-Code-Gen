package com.codegen.relaxng.parse.compact;

import com.codegen.relaxng.parse.BuildException;
import com.codegen.relaxng.parse.IllegalSchemaException;
import com.codegen.relaxng.parse.IncludedGrammar;
import com.codegen.relaxng.parse.Parseable;
import com.codegen.relaxng.parse.ParsedPattern;
import com.codegen.relaxng.parse.SchemaBuilder;
import com.codegen.relaxng.parse.Scope;
import com.codegen.xml.util.EncodingMap;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.net.URL;

public class CompactParseable implements Parseable {
  private final InputSource in;
  private final ErrorHandler eh;

  public CompactParseable(InputSource in, ErrorHandler eh) {
    this.in = in;
    this.eh = eh;
  }

  public ParsedPattern parse(SchemaBuilder sb, Scope scope) throws BuildException, IllegalSchemaException {
    return new CompactSyntax(makeReader(in), in.getSystemId(), sb, eh).parse(scope);
  }

  public ParsedPattern parseInclude(String uri, SchemaBuilder sb, IncludedGrammar g)
          throws BuildException, IllegalSchemaException {
    InputSource tem = new InputSource(uri);
    tem.setEncoding(in.getEncoding());
    return new CompactSyntax(makeReader(tem), uri, sb, eh).parseInclude(g);
  }

  public ParsedPattern parseExternal(String uri, SchemaBuilder sb, Scope scope)
          throws BuildException, IllegalSchemaException {
    InputSource tem = new InputSource(uri);
    tem.setEncoding(in.getEncoding());
    return new CompactSyntax(makeReader(tem), uri, sb, eh).parse(scope);
  }

  private static final String UTF8 = EncodingMap.getJavaName("UTF-8");
  private static final String UTF16 = EncodingMap.getJavaName("UTF-16");

  private static Reader makeReader(InputSource is) throws BuildException {
    try {
      Reader r = is.getCharacterStream();
      if (r == null) {
        InputStream in = is.getByteStream();
        if (in == null) {
          String systemId = is.getSystemId();
          in = new URL(systemId).openStream();
        }
        String encoding = is.getEncoding();
        if (encoding == null) {
          PushbackInputStream pb = new PushbackInputStream(in, 2);
          encoding = detectEncoding(pb);
          in = pb;
        }
        r = new InputStreamReader(in, encoding);
      }
      return r;
    }
    catch (IOException e) {
      throw new BuildException(e);
    }
  }

  static private String detectEncoding(PushbackInputStream in) throws IOException {
    String encoding = UTF8;
    int b1 = in.read();
    if (b1 != -1) {
      int b2 = in.read();
      if (b2 != -1) {
        in.unread(b2);
        if ((b1 == 0xFF && b2 == 0xFE) || (b1 == 0xFE && b2 == 0xFF))
          encoding = UTF16;
      }
      in.unread(b1);
    }
    return encoding;
  }
}
