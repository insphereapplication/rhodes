/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android.camera;

import android.graphics.Point;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.rhomobile.rhodes.Logger;
import android.graphics.YuvImage;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;


final class PreviewCallback implements Camera.PreviewCallback {

  private static final String TAG = PreviewCallback.class.getSimpleName();

  private final CameraConfigurationManager configManager;
  private final boolean useOneShotPreviewCallback;
  private Handler previewHandler;
  private int previewMessage;

  PreviewCallback(CameraConfigurationManager configManager, boolean useOneShotPreviewCallback) {
    this.configManager = configManager;
    this.useOneShotPreviewCallback = useOneShotPreviewCallback;
  }

  void setHandler(Handler previewHandler, int previewMessage) {
    this.previewHandler = previewHandler;
    this.previewMessage = previewMessage;
  }

  public void onPreviewFrame(byte[] data, Camera camera) {
    Logger.I(TAG, "Byte massive length " + data.length);
    Point cameraResolution = configManager.getCameraResolution();
    if (!useOneShotPreviewCallback) {
      camera.setPreviewCallback(null);
    }
    if (previewHandler != null) {
      int x = cameraResolution.x;
      int y = cameraResolution.y;

      /*
      YuvImage yuv = new YuvImage(data, parameters.getPreviewFormat(), x, y, null);

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      yuv.compressToJpeg(new Rect(0, 0, x, y), 100, out);

      byte[] bytes = out.toByteArray();
      double dimensionKoeff = ((double)y)/((double)x);

      final Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length), 1000, 
        (int)(dimensionKoeff*1000.), true);
      */

      Message message = previewHandler.obtainMessage(previewMessage, x, y, data);
      message.sendToTarget();
      previewHandler = null;
    } else {
      Log.d(TAG, "Got preview callback, but no handler for it");
    }
  }

}
