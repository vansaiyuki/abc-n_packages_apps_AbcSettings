/*
 * Copyright (C) 2016 The ABC rom
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
package com.abc.settings.fragments;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import android.support.v14.preference.SwitchPreference;

import com.android.internal.logging.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class VolumeRockerSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String SWAP_VOLUME_BUTTONS = "swap_volume_buttons";
    private static final String VOLUME_ROCKER_WAKE = "volume_rocker_wake";
    private static final String VOLUME_ROCKER_MUSIC_CONTROLS = "volume_rocker_music_controls";

    private SwitchPreference mSwapVolumeButtons;
    private SwitchPreference mVolumeRockerWake;
    private SwitchPreference mVolumeRockerMusicControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.abc_volumerocker_settings);

        // volume rocker reorient
        mSwapVolumeButtons = (SwitchPreference) findPreference(SWAP_VOLUME_BUTTONS);
        mSwapVolumeButtons.setOnPreferenceChangeListener(this);
        int swapVolumeButtons = Settings.System.getIntForUser(getContentResolver(),
                SWAP_VOLUME_BUTTONS, 0, UserHandle.USER_CURRENT);
        mSwapVolumeButtons.setChecked(swapVolumeButtons != 0);

        // volume rocker wake
        mVolumeRockerWake = (SwitchPreference) findPreference(VOLUME_ROCKER_WAKE);
        mVolumeRockerWake.setOnPreferenceChangeListener(this);
        int volumeRockerWake = Settings.System.getIntForUser(getContentResolver(),
                VOLUME_ROCKER_WAKE, 0, UserHandle.USER_CURRENT);
        mVolumeRockerWake.setChecked(volumeRockerWake != 0);

        // volume rocker music control
        mVolumeRockerMusicControl = (SwitchPreference) findPreference(VOLUME_ROCKER_MUSIC_CONTROLS);
        mVolumeRockerMusicControl.setOnPreferenceChangeListener(this);
        int volumeRockerMusicControl = Settings.System.getIntForUser(getContentResolver(),
                VOLUME_ROCKER_MUSIC_CONTROLS, 0, UserHandle.USER_CURRENT);
        mVolumeRockerMusicControl.setChecked(volumeRockerMusicControl != 0);
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.ABC;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mSwapVolumeButtons) {
            boolean value = (Boolean) newValue;
            Settings.System.putIntForUser(getContentResolver(), SWAP_VOLUME_BUTTONS,
                    value ? 1 : 0, UserHandle.USER_CURRENT);
            return true;
        } else if (preference == mVolumeRockerWake) {
            boolean value = (Boolean) newValue;
            Settings.System.putIntForUser(getContentResolver(), VOLUME_ROCKER_WAKE,
                    value ? 1 : 0, UserHandle.USER_CURRENT);
            return true;
        } else if (preference == mVolumeRockerMusicControl) {
            boolean value = (Boolean) newValue;
            Settings.System.putIntForUser(getContentResolver(), VOLUME_ROCKER_MUSIC_CONTROLS,
                    value ? 1 : 0, UserHandle.USER_CURRENT);
            return true;
        }
        return false;
    }
}
