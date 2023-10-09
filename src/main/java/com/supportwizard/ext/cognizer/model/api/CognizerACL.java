package com.supportwizard.ext.cognizer.model.api;

public class CognizerACL {
  static public final String OPERATION_ADDED = "ADDED";
  static public final String OPERATION_DELETED = "DELETED";

  private final String id;
  private final String operation;// [ADDED], [DELETED] --- for ACLCHANGE


  public CognizerACL(String id, String operation) {
    this.id = id;
    this.operation = operation;
  }

  public String getId() {
    return id;
  }

  public String getOperation() {
    return operation;
  }

  @Override
  public String toString() {
    return "CognizerACL{" +
      "id='" + id + '\'' +
      ", operation='" + operation + '\'' +
      '}';
  }
}
