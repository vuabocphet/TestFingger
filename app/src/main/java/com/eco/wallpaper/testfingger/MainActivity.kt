package com.eco.wallpaper.testfingger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import co.infinum.goldfinger.Goldfinger
import co.infinum.goldfinger.Goldfinger.PromptParams
import co.infinum.goldfinger.rx.RxGoldfinger
import io.reactivex.observers.DisposableObserver





class MainActivity : AppCompatActivity() {

    private lateinit var params: PromptParams
    private lateinit var build: RxGoldfinger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.build = RxGoldfinger.Builder(this).build()
        this.params= PromptParams.Builder(this)
                .title("Title")
                .negativeButtonText("Cancel")
                .description("Description")
                .subtitle("Subtitle")
                .build()
    }

    override fun onStart() {
        super.onStart()
        if (this.build.canAuthenticate().not()) {
            Log.i("TinhNv", "canAuthenticate: ")
            return
        }
        build.authenticate(params).subscribe(object : DisposableObserver<Goldfinger.Result?>() {
            override fun onComplete() {
                Log.i("TinhNv", "onComplete: ")
            }

            override fun onError(e: Throwable) {
                Log.i("TinhNv", "onError: "+e.toString())
            }

            override fun onNext(result: Goldfinger.Result) {
                Log.i("TinhNv", "onNext: "+result.type())
            }
        })
    }

    override fun onPause() {
        super.onPause()
        this.build.cancel()
    }
}