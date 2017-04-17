/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.pjsip.pjsua2;

public class MediaFmtChangedEvent {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected MediaFmtChangedEvent(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(MediaFmtChangedEvent obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        pjsua2JNI.delete_MediaFmtChangedEvent(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setNewWidth(long value) {
    pjsua2JNI.MediaFmtChangedEvent_newWidth_set(swigCPtr, this, value);
  }

  public long getNewWidth() {
    return pjsua2JNI.MediaFmtChangedEvent_newWidth_get(swigCPtr, this);
  }

  public void setNewHeight(long value) {
    pjsua2JNI.MediaFmtChangedEvent_newHeight_set(swigCPtr, this, value);
  }

  public long getNewHeight() {
    return pjsua2JNI.MediaFmtChangedEvent_newHeight_get(swigCPtr, this);
  }

  public MediaFmtChangedEvent() {
    this(pjsua2JNI.new_MediaFmtChangedEvent(), true);
  }

}
