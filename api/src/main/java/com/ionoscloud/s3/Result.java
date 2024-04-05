

package com.ionoscloud.s3;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ionoscloud.s3.errors.ErrorResponseException;
import com.ionoscloud.s3.errors.InsufficientDataException;
import com.ionoscloud.s3.errors.InternalException;
import com.ionoscloud.s3.errors.InvalidResponseException;
import com.ionoscloud.s3.errors.ServerException;
import com.ionoscloud.s3.errors.XmlParserException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/** A container class keeps any type or an exception. */
public class Result<T> {
  private final T type;
  private final Exception ex;

  public Result(T type) {
    this.type = type;
    this.ex = null;
  }

  public Result(Exception ex) {
    this.type = null;
    this.ex = ex;
  }

  /** Returns given Type if exception is null, else respective exception is thrown. */
  public T get()
      throws ErrorResponseException, IllegalArgumentException, InsufficientDataException,
          InternalException, InvalidKeyException, InvalidResponseException, IOException,
          JsonMappingException, JsonParseException, NoSuchAlgorithmException, ServerException,
          XmlParserException {
    if (ex == null) {
      return type;
    }

    if (ex instanceof ErrorResponseException) {
      throw (ErrorResponseException) ex;
    }

    if (ex instanceof IllegalArgumentException) {
      throw (IllegalArgumentException) ex;
    }

    if (ex instanceof InsufficientDataException) {
      throw (InsufficientDataException) ex;
    }

    if (ex instanceof InternalException) {
      throw (InternalException) ex;
    }

    if (ex instanceof InvalidKeyException) {
      throw (InvalidKeyException) ex;
    }

    if (ex instanceof InvalidResponseException) {
      throw (InvalidResponseException) ex;
    }

    if (ex instanceof IOException) {
      throw (IOException) ex;
    }

    if (ex instanceof JsonMappingException) {
      throw (JsonMappingException) ex;
    }

    if (ex instanceof JsonParseException) {
      throw (JsonParseException) ex;
    }

    if (ex instanceof NoSuchAlgorithmException) {
      throw (NoSuchAlgorithmException) ex;
    }

    if (ex instanceof ServerException) {
      throw (ServerException) ex;
    }

    if (ex instanceof XmlParserException) {
      throw (XmlParserException) ex;
    }

    throw new RuntimeException("Exception not handled", ex);
  }
}
