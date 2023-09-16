package com.example.hackzurich.base

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.ViewBindingProperty
import by.kirich1409.viewbindingdelegate.viewBinding
import okhttp3.OkHttpClient
import java.security.cert.CertificateException
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

inline fun <reified T : ViewBinding> Fragment.viewBinding(): ViewBindingProperty<Fragment, T> {
    return viewBinding()
}

enum class ActionType(val type: String) {
    SAVED("SAVED"), POPULAR("POPULAR")
}

fun getRealPathFromURIPath(contentURI: Uri, context: Context): String {
    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

    context.contentResolver.query(contentURI, filePathColumn, null, null, null).use {
        return if (it == null) {
            contentURI.path.orEmpty()
        } else {
            try {
                it.moveToFirst()
                val idx = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                it.getString(idx)
            } catch (ex: Throwable) {
                ex.message?.let { it1 -> Log.e("TAG", it1) }
                ""
            }
        }
    }
}
@Suppress("DEPRECATION")
fun OkHttpClient.Builder.trustAll(): OkHttpClient.Builder {
    hostnameVerifier { _, _ -> true }
    try {
        // Create a trust manager that does not validate certificate chains
        val naiveTrustManager = object : X509TrustManager {

            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
//                Timber.i("checkClientTrusted")
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
//                Timber.i("checkServerTrusted")
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        }

        val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

        sslSocketFactory(sslSocketFactory, naiveTrustManager)
    } catch (e: Exception) {
//        Timber.e(e)
    }
    return this
}