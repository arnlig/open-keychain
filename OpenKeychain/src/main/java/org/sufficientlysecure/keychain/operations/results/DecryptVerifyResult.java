/*
 * Copyright (C) 2014-2015 Dominik Schürmann <dominik@dominikschuermann.de>
 * Copyright (C) 2014 Vincent Breitmoser <v.breitmoser@mugenguild.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sufficientlysecure.keychain.operations.results;

import android.os.Parcel;

import org.openintents.openpgp.OpenPgpDecryptionResult;
import org.openintents.openpgp.OpenPgpMetadata;
import org.openintents.openpgp.OpenPgpSignatureResult;
import org.sufficientlysecure.keychain.service.input.CryptoInputParcel;
import org.sufficientlysecure.keychain.service.input.RequiredInputParcel;

public class DecryptVerifyResult extends InputPendingResult {

    public static final int RESULT_NO_DATA = RESULT_ERROR + 16;
    public static final int RESULT_KEY_DISALLOWED = RESULT_ERROR + 32;

    OpenPgpSignatureResult mSignatureResult;
    OpenPgpDecryptionResult mDecryptionResult;
    OpenPgpMetadata mDecryptionMetadata;
    // This holds the charset which was specified in the ascii armor, if specified
    // https://tools.ietf.org/html/rfc4880#page56
    String mCharset;

    CryptoInputParcel mCachedCryptoInputParcel;

    byte[] mOutputBytes;

    public DecryptVerifyResult(int result, OperationLog log) {
        super(result, log);
    }

    public DecryptVerifyResult(OperationLog log, RequiredInputParcel requiredInput,
                               CryptoInputParcel cryptoInputParcel) {
        super(log, requiredInput, cryptoInputParcel);
    }

    public DecryptVerifyResult(Parcel source) {
        super(source);
        mSignatureResult = source.readParcelable(OpenPgpSignatureResult.class.getClassLoader());
        mDecryptionResult = source.readParcelable(OpenPgpDecryptionResult.class.getClassLoader());
        mDecryptionMetadata = source.readParcelable(OpenPgpMetadata.class.getClassLoader());
        mCachedCryptoInputParcel = source.readParcelable(CryptoInputParcel.class.getClassLoader());
    }


    public boolean isKeysDisallowed () {
        return (mResult & RESULT_KEY_DISALLOWED) == RESULT_KEY_DISALLOWED;
    }

    public OpenPgpSignatureResult getSignatureResult() {
        return mSignatureResult;
    }

    public void setSignatureResult(OpenPgpSignatureResult signatureResult) {
        mSignatureResult = signatureResult;
    }

    public OpenPgpDecryptionResult getDecryptionResult() {
        return mDecryptionResult;
    }

    public void setDecryptionResult(OpenPgpDecryptionResult decryptionResult) {
        mDecryptionResult = decryptionResult;
    }

    public CryptoInputParcel getCachedCryptoInputParcel() {
        return mCachedCryptoInputParcel;
    }

    public void setCachedCryptoInputParcel(CryptoInputParcel cachedCryptoInputParcel) {
        mCachedCryptoInputParcel = cachedCryptoInputParcel;
    }

    public OpenPgpMetadata getDecryptionMetadata() {
        return mDecryptionMetadata;
    }

    public void setDecryptionMetadata(OpenPgpMetadata decryptMetadata) {
        mDecryptionMetadata = decryptMetadata;
    }

    public String getCharset () {
        return mCharset;
    }

    public void setCharset(String charset) {
        mCharset = charset;
    }

    public void setOutputBytes(byte[] outputBytes) {
        mOutputBytes = outputBytes;
    }

    public byte[] getOutputBytes() {
        return mOutputBytes;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(mSignatureResult, flags);
        dest.writeParcelable(mDecryptionResult, flags);
        dest.writeParcelable(mDecryptionMetadata, flags);
        dest.writeParcelable(mCachedCryptoInputParcel, flags);
    }

    public static final Creator<DecryptVerifyResult> CREATOR = new Creator<DecryptVerifyResult>() {
        public DecryptVerifyResult createFromParcel(final Parcel source) {
            return new DecryptVerifyResult(source);
        }

        public DecryptVerifyResult[] newArray(final int size) {
            return new DecryptVerifyResult[size];
        }
    };

}
