package com.tomclaw.appsend.main.local;

import static com.tomclaw.appsend.util.TimeHelper.timeHelper;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tomclaw.appsend.R;
import com.tomclaw.appsend.core.GlideApp;
import com.tomclaw.appsend.main.adapter.files.FileViewHolder;
import com.tomclaw.appsend.main.adapter.files.FilesListener;
import com.tomclaw.appsend.main.download.DownloadActivity;
import com.tomclaw.appsend.main.item.AppItem;
import com.tomclaw.appsend.util.FileHelper;

import java.util.concurrent.TimeUnit;

public class AppItemViewHolder extends FileViewHolder<AppItem> {

    private View itemView;
    private ImageView appIcon;
    private TextView appName;
    private TextView appVersion;
    private TextView appUpdateTime;
    private TextView appSize;
    private View badgeNew;
    private View updateButton;

    public AppItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        appIcon = itemView.findViewById(R.id.app_icon);
        appName = itemView.findViewById(R.id.app_name);
        appVersion = itemView.findViewById(R.id.app_version);
        appUpdateTime = itemView.findViewById(R.id.app_update_time);
        appSize = itemView.findViewById(R.id.app_size);
        badgeNew = itemView.findViewById(R.id.badge_new);
        updateButton = itemView.findViewById(R.id.button_update);
    }

    @Override
    public void bind(final AppItem item, boolean isLast, final FilesListener<AppItem> listener) {
        Context context = itemView.getContext();
        if (listener != null) {
            itemView.setOnClickListener(v -> listener.onClick(item));
        }

        try {
            GlideApp.with(context)
                    .load(item.getPackageInfo())
                    .into(appIcon);
        } catch (Throwable ignored) {
        }

        appName.setText(item.getLabel());
        appVersion.setText(item.getVersion());
        if (item.getLastUpdateTime() > 0) {
            appUpdateTime.setVisibility(View.VISIBLE);
            appUpdateTime.setText(timeHelper().getFormattedDate(item.getLastUpdateTime()));
        } else {
            appUpdateTime.setVisibility(View.GONE);
        }
        appSize.setText(FileHelper.formatBytes(context.getResources(), item.getSize()));

        long appInstallDelay = System.currentTimeMillis() - item.getFirstInstallTime();
        boolean isNewApp = appInstallDelay > 0 && appInstallDelay < TimeUnit.DAYS.toMillis(1);
        badgeNew.setVisibility(isNewApp ? View.VISIBLE : View.GONE);

        updateButton.setVisibility(item.getUpdate() != null ? View.VISIBLE : View.GONE);
        updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, DownloadActivity.class)
                    .putExtra(DownloadActivity.STORE_APP_ID, item.getUpdate().getAppId())
                    .putExtra(DownloadActivity.STORE_APP_LABEL, item.getUpdate().getLabel())
                    .putExtra(DownloadActivity.STORE_FINISH_ONLY, true);
            context.startActivity(intent);
        });
    }

}
