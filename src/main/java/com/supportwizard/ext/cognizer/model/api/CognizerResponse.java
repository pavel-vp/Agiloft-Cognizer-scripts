package com.supportwizard.ext.cognizer.model.api;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public abstract class CognizerResponse implements Serializable {
  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }
}
