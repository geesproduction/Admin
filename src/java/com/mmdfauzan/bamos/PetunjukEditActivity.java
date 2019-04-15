/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  java.lang.CharSequence
 */
package com.mmdfauzan.bamos;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.mmdfauzan.bamos.PetunjukEditActivity;

public class PetunjukEditActivity
extends AppCompatActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427393);
        this.getSupportActionBar().setTitle((CharSequence)"Petunjuk Edit Aplikasi");
        this.getSupportActionBar().hide();
        ((Button)this.findViewById(2131296391)).setOnClickListener(new View.OnClickListener((PetunjukEditActivity)this){
            final /* synthetic */ PetunjukEditActivity this$0;
            {
                this.this$0 = petunjukEditActivity;
            }

            public void onClick(View view) {
                this.this$0.finish();
            }
        });
    }
}

