package com.wjjiang.materialdesigns.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.wjjiang.materialdesigns.R;

import java.util.Calendar;

import butterknife.BindView;

public class AlarmActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.btn_set)
    Button setBtn;
    @BindView(R.id.btn_cancel)
    Button cancenBtn;
    @BindView(R.id.alarm_toolbar)
    Toolbar toolbar;

    private AlarmManager alarmManager;
    private PendingIntent pi;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_alarm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBtn.setOnClickListener(this);
        cancenBtn.setOnClickListener(this);

        setSupportActionBar(toolbar);
        setTitle("Alarm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        pi = PendingIntent.getActivity(this, 0, intent, 0);

//        Daemon.run(this, AlarmActivity.class, Daemon.INTERVAL_ONE_MINUTE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set:
                Calendar currentTime = Calendar.getInstance();
                new TimePickerDialog(AlarmActivity.this, 0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // set current time
                                Calendar c = Calendar.getInstance();
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                                } else {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                                }

                                Snackbar.make(view, "Alarm set!", Snackbar.LENGTH_SHORT).show();
                            }
                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false).show();
                cancenBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_cancel:
                alarmManager.cancel(pi);
                cancenBtn.setVisibility(View.GONE);
                Snackbar.make(view, "Alarm cancel!", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }
}
