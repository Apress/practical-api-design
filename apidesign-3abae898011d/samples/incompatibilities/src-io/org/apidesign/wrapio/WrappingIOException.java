package org.apidesign.wrapio;

import java.io.IOException;

// BEGIN: wrapping.WrappingIOException
public class WrappingIOException extends IOException {
  private IOException cause;
  public WrappingIOException(IOException cause) {
    this.cause = cause;
  }
  public IOException getCause() {
    return cause;
  }
}
// END: wrapping.WrappingIOException
