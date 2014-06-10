package nl.loxia.raildocs.raildocs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity implements LoaderCallbacks<Cursor>{
    @ViewById(R.id.email)
    protected AutoCompleteTextView emailView;
    @ViewById(R.id.password)
    protected EditText passwordView;
    @ViewById(R.id.login_progress)
    protected View progressView;
    @ViewById(R.id.email_login_form)
    protected View loginFormView;
    @ViewById(R.id.email_sign_in_button)
    protected Button emailSignInButton;

    @RestService
    IRailCloud railCloud;

    private boolean loginInProgress;

    @AfterViews
    protected void afterViews() {
        // Set up the login form.
        populateAutoComplete();

        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Click(R.id.email_sign_in_button)
    public void attemptLogin() {
        if (loginInProgress) {
            return;
        }

        emailView.setError(null);
        passwordView.setError(null);

        String username = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean ok = validateFields(username, password);

        if (ok) {
            showProgress(true);
            loginInProgress = true;
            doLogin(username, password);
        }
    }

    private boolean validateFields(String username, String password) {
        boolean ok = true;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            ok = false;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            ok = false;
        } else if (!isEmailValid(username)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            ok = false;
        }

        if (!ok) {
            focusView.requestFocus();
        }
        return ok;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        loginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        emailView.setAdapter(adapter);
    }

    @Background
    protected void doLogin(String username, String password) {
        railCloud.setHttpBasicAuth(username, password);
        try {
            railCloud.getNamespaces();
        } catch (RestClientException e) {
            loginFailed();
        }
        loginSucceeded();
    }

    @UiThread
    protected void loginSucceeded() {
        showProgress(false);
        loginInProgress = false;
    }

    @UiThread
    protected void loginFailed() {
        showProgress(false);
        loginInProgress = false;
        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT).show();
    }
}



