package com.CSE3311.parrot.utils

import android.content.Context
import com.CSE3311.parrot.AppVirgil
import com.virgilsecurity.android.common.model.EThreeParams
import com.virgilsecurity.android.ethree.interaction.EThree
import com.virgilsecurity.common.callback.OnCompleteListener
import com.virgilsecurity.common.callback.OnResultListener
import com.virgilsecurity.sdk.cards.Card
import com.virgilsecurity.sdk.crypto.exceptions.KeyEntryNotFoundException
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 *  code used based on demo code provided by virgil security under apache 2.0 license
 * RxEthree
 */
class RxEthree(val context: Context) {

    private val preferences = Preferences.instance(context)
    //ethree initializer
    fun initEthree(identity: String, verifyPrivateKey: Boolean = false): Single<EThree> = Single.create { e ->
        val params = EThreeParams(identity, {preferences.virgilToken()!!}, context)
        val ethree = EThree(params)
        if (verifyPrivateKey) {
            if (ethree.hasLocalPrivateKey()) {
                e.onSuccess(ethree)
            } else {
                e.onError(KeyEntryNotFoundException())
            }
        } else {
            e.onSuccess(ethree)
        }
    }
    //ethree registration function
    fun registerEthree(): Completable = Completable.create { e ->
        AppVirgil.eThree.register().addCallback(object : OnCompleteListener {
            override fun onError(throwable: Throwable) {
                e.onError(throwable)
            }

            override fun onSuccess() {
                e.onComplete()
            }

        })
    }
    //this requests cards from virgil the string is a username
    //in order to use this you need to establish a card at the start of the function youre using it it
    //so
    //import com.virgilsecurity.sdk.cards.Card
    //private lateinit var userCard: Card
    //in the getter or setter function
    //val encryptedText = eThree.authEncrypt(text, userCard)
    //or val decryptedText = eThree.authDecrypt(text,userCard) for decryption
    //this is a function to request the card:
    /*fun requestCard(identity: String,
                    onSuccess: (Card) -> Unit,
                    onError: (Throwable) -> Unit) {

        val disposable = rxEthree.findCard(identity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        userCard = it
                        onSuccess(it)
                    },
                    onError = {
                        onError(it)
                    }
                )

        compositeDisposable += disposable
    }*/
    //the above is in kotlin but translating it to java should be something like
    //Single<Card> getCard = rxEThree.findCard(username);
    //final Card userCard = getCard.blockingGet();
    //string encryptedText = eThree.authEncrypt(text,userCard);
    //for one of those you may need to use a blocking get or asynch through subscribe
    //all the above code and relevant code is from here: https://github.com/VirgilSecurity/chat-back4app-android
    //i recommend loading it up in another window to go back and forth and over over functions to see the return types and whats actually going on
    //the relevant pages in there for us are loginpresenter, chatthreadpresenter and chatthreadrvadapter they show how its delcared when being used
    fun findCard(identity: String): Single<Card> = Single.create { e ->
        AppVirgil.eThree.findUser(identity).addCallback(object : OnResultListener<Card> {
            override fun onError(throwable: Throwable) {
                e.onError(throwable)
            }

            override fun onSuccess(result: Card) {
                e.onSuccess(result)
            }

        })
    }


}