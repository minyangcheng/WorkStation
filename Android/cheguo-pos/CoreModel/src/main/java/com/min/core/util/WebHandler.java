package com.min.core.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.min.core.view.WebActivity;

import me.drakeet.floo.TargetNotFoundHandler;
import me.drakeet.floo.Urls;

public class WebHandler implements TargetNotFoundHandler {

    @Override
    public boolean onTargetNotFound(
            @NonNull Context context,
            @NonNull Uri sourceUri,
            @NonNull Bundle extras,
            @Nullable Integer intentFlags) {

        if (Urls.isWebScheme(sourceUri)) {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("url", sourceUri.toString());
            if (intentFlags != null) {
                intent.setFlags(intentFlags);
            }
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
