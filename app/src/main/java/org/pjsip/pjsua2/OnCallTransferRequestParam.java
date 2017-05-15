/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.10
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class OnCallTransferRequestParam {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected OnCallTransferRequestParam(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(OnCallTransferRequestParam obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_OnCallTransferRequestParam(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setDstUri(String value) {
    pjsua2JNI.OnCallTransferRequestParam_dstUri_set(swigCPtr, this, value);
  }

  public String getDstUri() {
    return pjsua2JNI.OnCallTransferRequestParam_dstUri_get(swigCPtr, this);
  }

  public void setStatusCode(pjsip_status_code value) {
    pjsua2JNI.OnCallTransferRequestParam_statusCode_set(swigCPtr, this, value.swigValue());
  }

  public pjsip_status_code getStatusCode() {
    return pjsip_status_code.swigToEnum(pjsua2JNI.OnCallTransferRequestParam_statusCode_get(swigCPtr, this));
  }

  public void setOpt(CallSetting value) {
    pjsua2JNI.OnCallTransferRequestParam_opt_set(swigCPtr, this, CallSetting.getCPtr(value), value);
  }

  public CallSetting getOpt() {
    long cPtr = pjsua2JNI.OnCallTransferRequestParam_opt_get(swigCPtr, this);
    return (cPtr == 0) ? null : new CallSetting(cPtr, false);
  }

  public OnCallTransferRequestParam() {
    this(pjsua2JNI.new_OnCallTransferRequestParam(), true);
  }

}
