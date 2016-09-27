package lxz.dbutil.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class StructuredGlobbingResourceLoader extends ClasspathResourceLoader {

	public static class VTLIndentationGlobber extends FilterInputStream {
		protected String buffer = "";
		protected int bufpos = 0;

		protected enum State {
			defstate, hash, comment, directive, schmoo, eol, eof
		}

		protected State state = State.defstate;

		public VTLIndentationGlobber(InputStream is) {
			super(is);
		}

		// TODO - multiline comments #* ... *# not taken into account for now in
		// all cases
		public int read() throws IOException {
			while (true) {
				switch (state) {
					case defstate: {
						int ch = in.read();
						switch (ch) {
						case (int) '#':
							state = State.hash;
							buffer = "";
							bufpos = 0;
							return ch;
						case (int) ' ':
							buffer += (char) ' ';
							break;
						case (int) '\t':
							buffer += (char) '\t';
							break;
						case -1:
							state = State.eof;
							break;
						default:
							buffer += (char) ch;
							state = State.schmoo;
							break;
						}
						break;
					}
					case eol:
						if (bufpos < buffer.length())
							return (int) buffer.charAt(bufpos++);
						else {
							state = State.defstate;
							buffer = "";
							bufpos = 0;
							return '\n';
						}
					case eof:
						if (bufpos < buffer.length())
							return (int) buffer.charAt(bufpos++);
						else
							return -1;
					case hash: {
						int ch = (int) in.read();
						switch (ch) {
						case (int) '#':
							state = State.directive;
							return ch;
						case -1:
							state = State.eof;
							return -1;
						default:
							state = State.directive;
							buffer = "##";
							return ch;
						}
					}
					case directive: {
						int ch = (int) in.read();
						if (ch == (int) '\n') {
							state = State.eol;
							break;
						} else if (ch == -1) {
							state = State.eof;
							break;
						} else
							return ch;
					}
					case schmoo: {
						int ch = (int) in.read();
						if (ch == (int) '\n') {
							state = State.eol;
							break;
						} else if (ch == -1) {
							state = State.eof;
							break;
						} else {
							buffer += (char) ch;
							return (int) buffer.charAt(bufpos++);
						}
					}
				}
			}
		}

		public int read(byte[] b, int off, int len) throws IOException {
			int i;
			int ok = 0;
			while (len-- > 0) {
				i = read();
				if (i == -1)
					return (ok == 0) ? -1 : ok;
				b[off++] = (byte) i;
				ok++;
			}
			return ok;
		}

		public int read(byte[] b) throws IOException {
			return read(b, 0, b.length);
		}

		public boolean markSupported() {
			return false;
		}
	}

	public synchronized InputStream getResourceStream(String name) {
		return new VTLIndentationGlobber(super.getResourceStream(name));
	}
}